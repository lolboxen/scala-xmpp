package com.lolboxen.xmpp

import akka.actor.Props
import akka.testkit.TestKit
import akka.util.ByteString
import com.lolboxen.test.AkkaUnitSpec
import com.lolboxen.xmpp.packet.parsing.StreamParser
import com.lolboxen.xmpp.packet.{StreamBegan, StreamEnded}

/**
 * Created by Trent Ahrens on 12/9/14.
 */
class PacketReaderTest extends AkkaUnitSpec {

  val packetReader = system.actorOf(Props(classOf[PacketReader], testActor, List(new StreamParser())))

  override def afterAll() {
    TestKit.shutdownActorSystem(system)
  }

  it should "correct decode packets" in {
    packetReader ! akka.io.Tcp.Received(ByteString("""<stream:stream xmlns="jabber:client" xmlns:stream="http://etherx.jabber.org/streams" id="1" version="1.0" from="domain.com"></stream:stream>"""))
    expectMsgAllOf(
      Received(StreamBegan(Some("1"), "1.0", Some(Jid("domain.com")), None)),
      Received(StreamEnded))
  }
}