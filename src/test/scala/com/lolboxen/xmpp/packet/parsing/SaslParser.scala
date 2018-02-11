package com.lolboxen.xmpp.packet.parsing

import com.lolboxen.test.UnitSpec
import com.lolboxen.xmpp.packet._

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

class SaslFailureParserTest extends UnitSpec {

  val notAuthorized: Elem = <failure xmlns="urn:ietf:params:xml:ns:xmpp-sasl"><not-authorized/></failure>
  val invalidAuthzid: Elem = <failure xmlns="urn:ietf:params:xml:ns:xmpp-sasl"><invalid-authzid/></failure>
  val unknown: Elem = <failure xmlns="urn:ietf:params:xml:ns:xmpp-sasl"><not-in-spec-failure/></failure>
  val parser: SaslFailureParser = new SaslFailureParser

  it should "recognize failure" in {
    parser.canParse(notAuthorized) shouldBe true
  }

  it should "parse not authorized" in {
    parser.parse(notAuthorized) shouldBe SaslFailure(NotAuthorized)
  }

  it should "parse invalid authzid" in {
    parser.parse(invalidAuthzid) shouldBe SaslFailure(InvalidAuthzid)
  }

  it should "parse faults not in spec" in {
    parser.parse(unknown) shouldBe SaslFailure(UnknownReason)
  }
}