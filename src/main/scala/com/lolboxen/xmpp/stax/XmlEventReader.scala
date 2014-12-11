package com.lolboxen.xmpp.stax

import javax.xml.stream.XMLStreamConstants._

import akka.util.ByteString
import com.fasterxml.aalto.AsyncXMLStreamReader
import com.fasterxml.aalto.AsyncXMLStreamReader._
import com.fasterxml.aalto.stax.InputFactoryImpl

import scala.annotation.tailrec
import scala.xml.{NamespaceBinding, MetaData, Attribute, Null}
import scala.xml.pull.{EvElemStart, EvElemEnd, EvText, XMLEvent}

/**
 * Created by Trent Ahrens on 12/10/14.
 */
class XmlEventReader {
  val xmlReader: AsyncXMLStreamReader = new InputFactoryImpl().createAsyncXMLStreamReader()

  var nextEvent: XMLEvent = _

  def feed(d: ByteString) = xmlReader.getInputFeeder.feedInput(d.toArray, 0, d.length)

  def hasNext: Boolean = {
    parse()
    nextEvent != null
  }

  def next(): XMLEvent = {
    parse()
    val r = nextEvent
    nextEvent = null
    r
  }

  @tailrec
  final def parse(): Unit = {
    if (nextEvent == null) {
      xmlReader.next() match {
        case EVENT_INCOMPLETE => ()
        case START_ELEMENT => nextEvent = EvElemStart(emptyAsNull(xmlReader.getPrefix), xmlReader.getLocalName, metaData, namespace)
        case END_ELEMENT => nextEvent = EvElemEnd(emptyAsNull(xmlReader.getPrefix), xmlReader.getLocalName)
        case CDATA => nextEvent = EvText(xmlReader.getText)
        case CHARACTERS => nextEvent = EvText(xmlReader.getText)
        case _ => parse()
      }
    }
  }

  def emptyAsNull(s: String): String = s match {
    case null => null
    case "" => null
    case _ => s
  }

  def metaData: MetaData = {
    List.tabulate(xmlReader.getAttributeCount)(i => (
      emptyAsNull(xmlReader.getAttributePrefix(i)),
      xmlReader.getAttributeLocalName(i),
      xmlReader.getAttributeValue(i)))
    .foldRight(Null.asInstanceOf[MetaData]) {
      case ((prefix, name, value), meta) =>
        Attribute(prefix, name, value, meta)
    }
  }

  def namespace: NamespaceBinding = {
    if (emptyAsNull(xmlReader.getNamespaceURI) == null) null
    else NamespaceBinding(emptyAsNull(xmlReader.getPrefix), xmlReader.getNamespaceURI, null)
  }
}
