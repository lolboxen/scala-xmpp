package com.lolboxen.xmpp.helper

import scala.xml.{Elem, XML}
import scala.xml.pull.{EvText, EvElemEnd, EvElemStart, XMLEvent}

/**
 * Created by Trent Ahrens on 12/9/14.
 */
object XmlHelper {
  def parseEventsAsString(s: Seq[XMLEvent]): String = s.foldLeft("") {
    case (xmlStr, e: EvElemStart) => s"$xmlStr${strFor(e)}"
    case (xmlStr, e: EvElemEnd) => s"$xmlStr${strFor(e)}"
    case (xmlStr, EvText(text)) => s"$xmlStr$text"
  }

  def parseEventsAsXml(s: Seq[XMLEvent]): Elem = XML.loadString(parseEventsAsString(s))

  def strFor(e: EvElemStart): String = {
    val prefix = Option(e.pre).map(s => s"$s:").getOrElse("")
    val label = e.label
    val sb = new StringBuilder
    e.scope.buildString(sb, null)
    e.attrs.buildString(sb)
    s"<$prefix$label$sb>"
  }

  def strFor(e: EvElemEnd): String = {
    val prefix = Option(e.pre).map(s => s"$s:").getOrElse("")
    val label = e.label
    s"</$prefix$label>"
  }
}
