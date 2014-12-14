package com.lolboxen.xmpp

import org.scalatest._

/**
 * Created by Trent Ahrens on 12/5/14.
 */
class JidTest extends FlatSpec with Matchers {
  "example@example.com/home" should "parse as full jid" in {
    val jid = Jid("example@example.com/home")
    jid.user should be (Some("example"))
    jid.domain should be ("example.com")
    jid.resource should be (Some("home"))
  }

  "example@example.com" should "parse as bare jid" in {
    val jid = Jid("example@example.com")
    jid.user should be (Some("example"))
    jid.domain should be ("example.com")
    jid.resource should be (None)
  }

  "example.com" should "parse as domain" in {
    val jid = Jid("e-xample.com")
    jid.user should be (None)
    jid.domain should be ("e-xample.com")
    jid.resource should be (None)
  }

  "example@example.com/home" should "have correct asString" in {
    val jid = new Jid(Some("example"), "example.com", Some("home")).asString should be ("example@example.com/home")
  }

  "lolboxen@lolboxen.com" should "have correct asString" in {
    val jid = new Jid(Some("lolboxen"), "lolboxen.com", None).asString should be ("lolboxen@lolboxen.com")
  }

  "lolboxen.com" should "have correct asString" in {
    val jid = new Jid(None, "lolboxen.com", None).asString should be ("lolboxen.com")
  }
}
