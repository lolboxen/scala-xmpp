package com.lolboxen.xmpp.packet.parsing

import akka.util.ByteString
import com.lolboxen.xmpp.Jid
import com.lolboxen.xmpp.packet._
import org.scalatest._

/**
 * Created by Trent Ahrens on 12/11/14.
 */
class PacketReaderTest extends FlatSpec with Matchers {

  val data = ByteString("""<stream:stream xmlns="jabber:client" xmlns:stream="http://etherx.jabber.org/streams" id="1" version="1.0" from="example.com"><challenge xmlns='urn:ietf:params:xml:ns:xmpp-sasl'>data</challenge><success xmlns="urn:ietf:params:xml:ns:xmpp-sasl"></success></stream:stream>""")

  it should "correctly parse all packets" in {
    val reader = new PacketReader(List(new StreamParser, new SaslChallengeParser, new SaslSuccessParser))
    reader.feed(data)
    reader.nextPacket() should be (Some(StreamBegan(Some("1"), "1.0", Some(Jid("example.com")), None)))
    reader.nextPacket() should be (Some(SaslChallenge("data")))
    reader.nextPacket() should be (Some(SaslSuccess))
    reader.nextPacket() should be (Some(StreamEnded))
  }

  it should "ignore unknown packets when no default parser is present" in {
    val reader = new PacketReader(List(new StreamParser))
    reader.feed(data)
    reader.nextPacket()
    reader.nextPacket() should be (Some(StreamEnded))
  }

  it should "use default parser when encountering unknown packets" in {
    val reader = new PacketReader(List(new StreamParser, new UnknownPacketParser))
    reader.feed(data)
    reader.nextPacket()
    reader.nextPacket() should be (Some(Unknown(<challenge xmlns='urn:ietf:params:xml:ns:xmpp-sasl'>data</challenge>)))
    reader.nextPacket() should be (Some(Unknown(<success xmlns="urn:ietf:params:xml:ns:xmpp-sasl"></success>)))
    reader.nextPacket() should be (Some(StreamEnded))
  }
}
