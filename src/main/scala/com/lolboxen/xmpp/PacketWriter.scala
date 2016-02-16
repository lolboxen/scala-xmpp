package com.lolboxen.xmpp

import akka.actor.{Actor, ActorRef}
import akka.event.Logging

/**
 * Created by Trent Ahrens on 12/12/14.
 */
class PacketWriter(tcpWritingActor: ActorRef, map: Send => AnyRef) extends Actor {

  val log = Logging(context.system, this)

  override def receive: Receive = {
    case m: RegisterTcpActor => tcpWritingActor ! m
    case UnregisterTcpActor => tcpWritingActor ! UnregisterTcpActor
    case m: Send =>
      log.info("Sending packet: {}", m.packet)
      tcpWritingActor ! map(m)
  }
}
