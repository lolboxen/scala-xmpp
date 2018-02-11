package com.lolboxen.xmpp.packet.parsing

import com.lolboxen.xmpp.packet._

import scala.xml.Elem

/**
 * Created by Trent Ahrens on 12/6/14.
 */
class SaslChallengeParser extends ElemParser {
  override def canParse(e: Elem): Boolean = e.label == "challenge" && e.scope.uri == "urn:ietf:params:xml:ns:xmpp-sasl"

  override def parse(e: Elem): Packet = new SaslChallenge(e.text)
}

class SaslSuccessParser extends ElemParser {
  override def canParse(e: Elem): Boolean = e.label == "success" && e.scope.uri == "urn:ietf:params:xml:ns:xmpp-sasl"

  override def parse(e: Elem): Packet = SaslSuccess
}

class SaslFailureParser extends ElemParser {
  override def canParse(e: Elem): Boolean = e.label == "failure" && e.scope.uri == "urn:ietf:params:xml:ns:xmpp-sasl"

  override def parse(e: Elem): Packet = {
    e.child.headOption match {
      case Some(node) if node.label == "not-authorized" => SaslFailure(NotAuthorized)
      case Some(node) if node.label == "invalid-authzid" => SaslFailure(InvalidAuthzid)
      case _ => SaslFailure(UnknownReason)
    }
  }
}