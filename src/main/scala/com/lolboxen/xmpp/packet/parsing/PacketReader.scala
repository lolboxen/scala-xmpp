package com.lolboxen.xmpp.packet.parsing

import akka.util.ByteString
import com.lolboxen.xmpp.helper.XmlEventParser
import com.lolboxen.xmpp.packet.Packet

import com.lolboxen.xmpp.stax.XmlEventReader

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.xml.Elem
import scala.xml.pull.{EvElemEnd, EvElemStart, XMLEvent}

/**
 * Created by Trent Ahrens on 12/10/14.
 */
class PacketReader(val parsers: List[PacketParser]) {
  val xmlReader: XmlEventReader = new XmlEventReader()

  var openedTags: Int = 0
  var eventParser: Option[EventParser] = None

  val eventBuffer: ListBuffer[XMLEvent] = ListBuffer.empty
  val stack: ListBuffer[(Int, Option[EventParser])] = ListBuffer.empty

  def feed(d: ByteString) = xmlReader.feed(d)

  @tailrec
  final def nextPacket(): Option[Packet] = {
    if (xmlReader.hasNext) {
      buffer(xmlReader.next())
      parse match {
        case Some(x) => Some(x)
        case _ => nextPacket()
      }
    }
    else
      None
  }

  def parse = {
    lazy val defaultElemParser = parsers.find({
      case p: ElemParser => p.isDefault
      case _ => false
    }).map(p => p.asInstanceOf[ElemParser])

    if (openedTags > previousOpenedTags) {
      parsers.find({
        case p: EventParser => p.canParse(eventBuffer.toList)
        case _ => false
      }).map(p => p.asInstanceOf[EventParser]).map(p => {
        eventParser = Some(p)
        push()
        val r = p.parseStart(eventBuffer.toList)
        eventBuffer.clear()
        r
      })
    }
    else if (openedTags < previousOpenedTags) {
      pop()
      eventParser.map(p => p.parseEnd(eventBuffer.toList))
    }
    else {
      val elem: Elem = XmlEventParser.parseEventsAsXml(eventBuffer.toList)
      eventBuffer.clear()
      parsers.find({
        case p: ElemParser => p.canParse(elem)
        case _ => false
      }).map(p =>
        p.asInstanceOf[ElemParser]).orElse(defaultElemParser).map(p => {
        p.parse(elem)
      })
    }
  }

  def previousOpenedTags = stack.headOption.map { case (a, _) => a } getOrElse 0

  def push() = {
    stack prepend (openedTags -> eventParser)
    eventParser = None
  }

  def pop() = {
    val (a,b) = stack remove 0
    eventParser = b
  }

  def buffer(e: XMLEvent) = {
    eventBuffer += e
    openedTags += (e match {
      case _: EvElemStart => 1
      case _: EvElemEnd => -1
      case _ => 0
    })
  }
}
