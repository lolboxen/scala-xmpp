package com.lolboxen.xmpp

import akka.actor.{Actor, ActorRef}
import akka.event.Logging
import com.lolboxen.xmpp.io.IoWrite

/**
 * Created by Trent Ahrens on 12/12/14.
 */
class PacketWriter(tcpWritingActor: ActorRef) extends Actor {

  val log = Logging(context.system, this)

  override def receive: Receive = {
    case m: RegisterTcpActor => tcpWritingActor ! m
    case UnregisterTcpActor => tcpWritingActor ! UnregisterTcpActor
    case Send(p) =>
      log.info("Sending packet: {}", p)
      tcpWritingActor ! IoWrite(p.byteString)
  }
}
