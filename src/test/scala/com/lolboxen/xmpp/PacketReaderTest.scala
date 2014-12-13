package com.lolboxen.xmpp

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import akka.util.ByteString
import com.lolboxen.xmpp.packet.{StreamEnded, StreamBegan}
import com.lolboxen.xmpp.packet.parsing.StreamParser
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

/**
 * Created by Trent Ahrens on 12/9/14.
 */
class PacketReaderTest(_system: ActorSystem) extends TestKit(_system) with ImplicitSender with WordSpecLike with Matchers with BeforeAndAfterAll {
  def this() = this(ActorSystem("MySpec"))

  val packetReader = system.actorOf(Props(classOf[PacketReader], testActor, List(new StreamParser())))

  override def afterAll() {
    TestKit.shutdownActorSystem(system)
  }

  it must {
    "correct decode packets" in {
      packetReader ! akka.io.Tcp.Received(ByteString("""<stream:stream xmlns="jabber:client" xmlns:stream="http://etherx.jabber.org/streams" id="1" version="1.0" from="domain.com"></stream:stream>"""))
      expectMsgAllOf(
        Received(StreamBegan(Some("1"), "1.0", Some(Jid("domain.com")), None)),
        Received(StreamEnded))
    }
  }
}