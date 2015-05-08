package com.lolboxen.xmpp

import com.lolboxen.test.UnitSpec

/**
 * Created by Trent Ahrens on 12/5/14.
 */
class JidTest extends UnitSpec {
  "example@example.com/home" should "parse as full jid" in {
    val jid = Jid("example@example.com/home")
    jid.user shouldBe Some("example")
    jid.domain shouldBe "example.com"
    jid.resource shouldBe Some("home")
  }

  "example@example.com" should "parse as bare jid" in {
    val jid = Jid("example@example.com")
    jid.user shouldBe Some("example")
    jid.domain shouldBe "example.com"
    jid.resource shouldBe None
  }

  "example.com" should "parse as domain" in {
    val jid = Jid("e-xample.com")
    jid.user shouldBe None
    jid.domain shouldBe "e-xample.com"
    jid.resource shouldBe None
  }

  "example@example.com/home" should "have correct asString" in {
    val jid = new Jid(Some("example"), "example.com", Some("home")).asString shouldBe "example@example.com/home"
  }

  "lolboxen@lolboxen.com" should "have correct asString" in {
    val jid = new Jid(Some("lolboxen"), "lolboxen.com", None).asString shouldBe "lolboxen@lolboxen.com"
  }

  "lolboxen.com" should "have correct asString" in {
    val jid = new Jid(None, "lolboxen.com", None).asString shouldBe "lolboxen.com"
  }
}
