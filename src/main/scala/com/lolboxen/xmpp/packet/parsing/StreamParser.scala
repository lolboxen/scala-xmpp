package com.lolboxen.xmpp.packet.parsing

import com.lolboxen.xmpp.packet.{StreamBegan, StreamEnded, Packet}
import com.lolboxen.xmpp.helper.RichMetaData._

import scala.xml.pull.{EvElemStart, XMLEvent}

/**
 * Created by Trent Ahrens on 12/9/14.
 */
class StreamParser extends EventParser {
  override def canParse(s: Seq[XMLEvent]): Boolean = s.headOption.exists {
    case EvElemStart(prefix, label, _, _) => s"$prefix:$label" == "stream:stream"
    case _ => false
  }

  override def parseEnd(s: Seq[XMLEvent]): Packet = StreamEnded

  override def parseStart(s: Seq[XMLEvent]): Packet = s match {
    case EvElemStart(_, _, attr, _)::t => new StreamBegan(attr.id, attr.version, attr.from)
  }
}
