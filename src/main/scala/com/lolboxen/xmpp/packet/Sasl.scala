package com.lolboxen.xmpp.packet

import akka.util.ByteString

/**
 * Created by Trent Ahrens on 12/6/14.
 */
case class SaslChallenge(payload: String) extends Packet {
  override def byteString: ByteString = ByteString(<challenge xmlns="urn:ietf:params:xml:ns:xmpp-sasl">{payload}</challenge>.toString())
}

case class SaslResponse(payload: String) extends Packet {
  override def byteString: ByteString = ByteString(<response xmlns="urn:ietf:params:xml:ns:xmpp-sasl">{payload}</response>.toString())
}

case object SaslSuccess extends Packet {
  override def byteString: ByteString = ByteString(<success xmlns="urn:ietf:params:xml:ns:xmpp-sasl" />.toString())
}

sealed trait SaslFailureReason
case object NotAuthorized extends SaslFailureReason
case object InvalidAuthzid extends SaslFailureReason
case object UnknownReason extends SaslFailureReason

case class SaslFailure(reason: SaslFailureReason) extends Packet {
  override def byteString: ByteString = {
    val xmlReason = reason match {
      case NotAuthorized => <not-authorized/>
      case InvalidAuthzid => <invalid-authzid/>
      case UnknownReason => throw new Exception("cannot make sasl failure packet of unknown reason")
    }
    ByteString(<failure xmlns="urn:ietf:params:xml:ns:xmpp-sasl">{xmlReason}</failure>.toString())
  }
}