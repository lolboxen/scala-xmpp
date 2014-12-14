package com.lolboxen.xmpp

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorRef}
import akka.io.{Tcp, IO}
import akka.io.Tcp.{Received => TcpReceived, ConfirmedClose, ConnectionClosed, Register}

/**
 * Created by Trent Ahrens on 12/5/14.
 */

/**
 *
 */
class XmppConnection(client: ActorRef, processor: ActorRef, packetReader: ActorRef, packerWriter: ActorRef) extends Actor {

  import context.system

  var tcpActor: ActorRef = _
  var packetWriter: ActorRef = _

  override def receive: Receive = disconnected

  def disconnected: Receive = {
    case Connect(host, port, _) => IO(Tcp) ! Tcp.Connect(new InetSocketAddress(host, port))
    case c @ Tcp.Connected(_,_) =>
      tcpActor = sender()
      tcpActor ! Register(self)
      packerWriter ! RegisterTcpActor(tcpActor)
      processor ! c
      context.become(connected)
  }

  def connected: Receive = {
    case Disconnect =>
      tcpActor ! ConfirmedClose
      context.become(disconnected)
    case s: Send => packetWriter ! s
    case r: TcpReceived => packetReader ! r
    case e: ConnectionClosed =>
      client ! e
      packerWriter ! UnregisterTcpActor
      context.become(disconnected)
  }
}
