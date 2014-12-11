package com.lolboxen.xmpp

import akka.actor.{Actor, ActorRef}
import akka.io.Tcp.{Received => TcpReceived}
import com.lolboxen.xmpp.packet.parsing.{PacketParser, PacketReader}

import scala.annotation.tailrec

/**
 * Created by Trent Ahrens on 12/5/14.
 */
class PacketReaderActor(listener: ActorRef, val parsers: List[PacketParser]) extends Actor {

  val packetReader = new PacketReader(parsers)

  override def receive: Receive = {
    case TcpReceived(data) =>
      packetReader.feed(data)
      exhaustReader()
  }

  @tailrec
  final def exhaustReader(): Unit = {
    packetReader.nextPacket() match {
      case Some(p) =>
        listener ! Received(p)
        exhaustReader()
      case _ => ()
    }
  }
}
