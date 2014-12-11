package com.lolboxen.xmpp.packet.parsing

import com.lolboxen.xmpp.packet.{Packet, SaslChallenge, SaslSuccess}

import scala.xml.Elem

/**
 * Created by Trent Ahrens on 12/6/14.
 */
class SaslChallengeParser extends ElemParser {
  override def canParse(e: Elem): Boolean = e.label == "challenge" && e.scope.uri == "urn:ietf:params:xml:ns:xmpp-sasl"

  override def parse(e: Elem): Packet = new SaslChallenge(e.text)

  override def isDefault: Boolean = false
}

class SaslSuccessParser extends ElemParser {
  override def canParse(e: Elem): Boolean = e.label == "success" && e.scope.uri == "urn:ietf:params:xml:ns:xmpp-sasl"

  override def parse(e: Elem): Packet = SaslSuccess

  override def isDefault: Boolean = false
}
