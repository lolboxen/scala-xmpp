package com.lolboxen.xmpp.packet

import akka.util.ByteString
import com.lolboxen.xmpp.Jid
import com.lolboxen.xmpp.helper.XmppPacketBuilder._

/**
 * Created by Trent Ahrens on 12/5/14.
 */
case class StreamBegan(id: Option[String], version: String, override val from: Option[Jid], override val to: Option[Jid]) extends Packet {
  override def byteString: ByteString = stream from from to to version "1.1" attr("xml", "lang", "en")
}

case object StreamEnded extends Packet {
  override def byteString: ByteString = ByteString("""</stream:stream>""")
}