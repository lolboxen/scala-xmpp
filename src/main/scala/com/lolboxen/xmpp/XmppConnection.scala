package com.lolboxen.xmpp

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorRef, Props}
import akka.io.Tcp.{ConnectionClosed, Register, Received => TcpReceived}
import akka.io.{IO, Tcp}

/**
 * Created by Trent Ahrens on 12/5/14.
 */
class XmppConnection(client: ActorRef) extends Actor {

  import context.system

  //val packetWriter: ActorRef = context.actorOf(Props[PacketWriter])
  val packetReader: ActorRef = context.actorOf(Props(classOf[PacketReaderActor], self))
  var tcpActor: ActorRef = _

  override def receive: Receive = disconnected

  def disconnected: Receive = {
    case Connect(host, port, _) => IO(Tcp) ! Tcp.Connect(new InetSocketAddress(host, port))
    case c @ Tcp.Connected(_,_) =>
      tcpActor = sender()
      tcpActor ! Register(self)
      context.become(connected)
  }

  def connected: Receive = {
    //case s: Send => packetWriter ! s
    case r: TcpReceived => packetReader ! r
    case _: ConnectionClosed =>
      client ! Disconnected
      context.become(disconnected)
  }
}
