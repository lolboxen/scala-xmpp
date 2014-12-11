package com.lolboxen.xmpp.packet.parsing

import com.lolboxen.xmpp.packet.{SaslChallenge, SaslSuccess}
import org.scalatest.{FlatSpec, Matchers}

import scala.xml.Elem

/**
 * Created by Trent Ahrens on 12/6/14.
 */
class SaslChallengeParserTest extends FlatSpec with Matchers {

  val elem: Elem = <challenge xmlns="urn:ietf:params:xml:ns:xmpp-sasl">data</challenge>
  val parser: SaslChallengeParser = new SaslChallengeParser

  it should "recognize when it can parse" in {
    parser.canParse(elem) should be (true)
  }

  it should "parse packet" in {
    parser.parse(elem) should be (new SaslChallenge("data"))
  }
}

class SaslSuccessParserTest extends FlatSpec with Matchers {

  val elem: Elem = <success xmlns="urn:ietf:params:xml:ns:xmpp-sasl"></success>
  val parser: SaslSuccessParser = new SaslSuccessParser

  it should "recognize when it can parse" in {
    parser.canParse(elem) should be (true)
  }

  it should "parse packet" in {
    parser.parse(elem) should be (SaslSuccess)
  }
}