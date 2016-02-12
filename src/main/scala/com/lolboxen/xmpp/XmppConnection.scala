package com.lolboxen.xmpp

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorRef}
import akka.io.{Tcp, IO}
import akka.io.Tcp.{Received => TcpReceived, CommandFailed, ConfirmedClose, ConnectionClosed, Register}

/**
 * Created by Trent Ahrens on 12/5/14.
 */

/**
 *
 */
class XmppConnection(client: ActorRef, processor: ActorRef, packetReader: ActorRef, packetWriter: ActorRef) extends Actor {

  import context.system

  var tcpActor: ActorRef = _

  override def receive: Receive = disconnected

  def disconnected: Receive = {
    case Connect(host, port, _) => IO(Tcp) ! Tcp.Connect(new InetSocketAddress(host, port))
    case CommandFailed(_:Connect) =>
      client ! ConnectFailed
    case c @ Tcp.Connected(_,_) =>
      tcpActor = sender()
      tcpActor ! Register(self)
      packetWriter ! RegisterTcpActor(tcpActor)
      processor ! Connected
      context.become(connected)
  }

  def connected: Receive = {
    case Disconnect =>
      tcpActor ! ConfirmedClose
      context.become(disconnected)
    case s: Send => packetWriter ! s
    case TcpReceived(b) => packetReader ! ReceivedBytes(b)
    case e: ConnectionClosed =>
      client ! Disconnected
      packetWriter ! UnregisterTcpActor
      context.become(disconnected)
  }
}
