package com.lolboxen.xmpp.packet

import akka.util.ByteString

/**
 * Created by Trent Ahrens on 12/6/14.
 */
case class SaslChallenge(payload: String) extends Packet {
  override def byteString: ByteString = ByteString(<challenge xmlns="urn:ietf:params:xml:ns:xmpp-sasl">{payload}</challenge>.toString())
}

case object SaslSuccess extends Packet {
  override def byteString: ByteString = ByteString(<success xmlns="urn:ietf:params:xml:ns:xmpp-sasl" />.toString())
}