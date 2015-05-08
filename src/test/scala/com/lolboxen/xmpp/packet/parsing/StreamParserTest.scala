package com.lolboxen.xmpp.packet.parsing

import com.lolboxen.test.UnitSpec
import com.lolboxen.xmpp.Jid
import com.lolboxen.xmpp.packet.{StreamBegan, StreamEnded}

import scala.xml.pull.{EvElemEnd, EvElemStart}
import scala.xml.{Attribute, Null}

/**
 * Created by Trent Ahrens on 12/9/14.
 */
class StreamParserTest extends UnitSpec {

  val parsable = List(EvElemStart("stream", "stream", Attribute(null, "id", "1", Attribute(null, "version", "1.0", Attribute(null, "from", "example.com", null))), null))
  val unparsable = List(EvElemStart("junk", "junk", Null, null))
  val parser = new StreamParser

  it should "recognize when it can parse" in {
    parser.canParse(parsable) shouldBe true
  }

  it should "recognize when it should not parse" in {
    parser.canParse(unparsable) shouldBe false
  }

  it should "parse stream open packet" in {
    parser.parseStart(parsable) shouldBe new StreamBegan(Some("1"), "1.0", Some(Jid("example.com")), None)
  }

  it should "parse stream end packet" in {
    parser.parseEnd(List(EvElemEnd("stream", "stream"))) shouldBe StreamEnded
  }
}
