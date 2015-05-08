package com.lolboxen.xmpp.packet.parsing

import akka.util.ByteString
import com.lolboxen.test.UnitSpec
import com.lolboxen.xmpp.Jid
import com.lolboxen.xmpp.packet._

/**
 * Created by Trent Ahrens on 12/11/14.
 */
class PacketReaderTest extends UnitSpec {

  val data = ByteString("""<stream:stream xmlns="jabber:client" xmlns:stream="http://etherx.jabber.org/streams" id="1" version="1.0" from="example.com"><challenge xmlns='urn:ietf:params:xml:ns:xmpp-sasl'>data</challenge><success xmlns="urn:ietf:params:xml:ns:xmpp-sasl"></success></stream:stream>""")

  it should "correctly parse all packets" in {
    val reader = new PacketReader(List(new StreamParser, new SaslChallengeParser, new SaslSuccessParser))
    reader.feed(data)
    reader.nextPacket() shouldBe Some(StreamBegan(Some("1"), "1.0", Some(Jid("example.com")), None))
    reader.nextPacket() shouldBe Some(SaslChallenge("data"))
    reader.nextPacket() shouldBe Some(SaslSuccess)
    reader.nextPacket() shouldBe Some(StreamEnded)
  }

  it should "ignore unknown packets when no default parser is present" in {
    val reader = new PacketReader(List(new StreamParser))
    reader.feed(data)
    reader.nextPacket()
    reader.nextPacket() shouldBe Some(StreamEnded)
  }

  it should "use default parser when encountering unknown packets" in {
    val reader = new PacketReader(List(new StreamParser, new UnknownPacketParser))
    reader.feed(data)
    reader.nextPacket()
    reader.nextPacket() shouldBe Some(Unknown(<challenge xmlns='urn:ietf:params:xml:ns:xmpp-sasl'>data</challenge>))
    reader.nextPacket() shouldBe Some(Unknown(<success xmlns="urn:ietf:params:xml:ns:xmpp-sasl"></success>))
    reader.nextPacket() shouldBe Some(StreamEnded)
  }
}
