package com.lolboxen.xmpp.packet.parsing

import com.lolboxen.test.UnitSpec
import com.lolboxen.xmpp.packet.{SaslChallenge, SaslSuccess}

import scala.xml.Elem

/**
 * Created by Trent Ahrens on 12/6/14.
 */
class SaslChallengeParserTest extends UnitSpec {

  val elem: Elem = <challenge xmlns="urn:ietf:params:xml:ns:xmpp-sasl">data</challenge>
  val parser: SaslChallengeParser = new SaslChallengeParser

  it should "recognize when it can parse" in {
    parser.canParse(elem) shouldBe true
  }

  it should "parse packet" in {
    parser.parse(elem) shouldBe new SaslChallenge("data")
  }
}

class SaslSuccessParserTest extends UnitSpec {

  val elem: Elem = <success xmlns="urn:ietf:params:xml:ns:xmpp-sasl"/>
  val parser: SaslSuccessParser = new SaslSuccessParser

  it should "recognize when it can parse" in {
    parser.canParse(elem) shouldBe true
  }

  it should "parse packet" in {
    parser.parse(elem) shouldBe SaslSuccess
  }
}