package com.lolboxen.xmpp.packet

import com.lolboxen.test.UnitSpec
import com.lolboxen.xmpp.Jid

/**
 * Created by Trent Ahrens on 12/15/14.
 */
class StreamBeganTest extends UnitSpec {
  it should "generate correct stream starting tag" in {
    StreamBegan(None, "1.1", None, Some(Jid("example.com"))).byteString.decodeString("UTF-8") shouldBe """<stream:stream xmlns="jabber:client" xmlns:stream="http://etherx.jabber.org/streams" xml:lang="en" version="1.1" to="example.com">"""
  }
}
