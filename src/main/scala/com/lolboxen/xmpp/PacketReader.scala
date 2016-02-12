package com.lolboxen.xmpp

import akka.actor.{Actor, ActorRef}
import akka.event.Logging
import com.lolboxen.xmpp.packet.parsing.{PacketParser, PacketReader => PacketReaderSync}

import scala.annotation.tailrec

/**
 * Created by Trent Ahrens on 12/5/14.
 */
class PacketReader(listener: ActorRef, val parsers: List[PacketParser]) extends Actor {

  val log = Logging(context.system, this)

  val packetReader = new PacketReaderSync(parsers)

  override def receive: Receive = {
    case ReceivedBytes(data) =>
      packetReader.feed(data)
      exhaustReader()
  }

  @tailrec
  final def exhaustReader(): Unit = {
    packetReader.nextPacket() match {
      case Some(p) =>
        log.info("Received packet: {}", p)
        listener ! Received(p)
        exhaustReader()
      case _ => ()
    }
  }
}
