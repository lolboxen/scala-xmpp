package com.lolboxen.xmpp.io

import akka.actor.{Actor, ActorRef}
import akka.io.Tcp.{Event, Write}
import akka.util.ByteString
import com.lolboxen.xmpp.{UnregisterTcpActor, RegisterTcpActor}

/**
 * Created by Trent Ahrens on 12/12/14.
 */

case class IoWrite(byteString: ByteString)

class TcpWriter() extends Actor {

  case object Ack extends Event

  private var tcpActor: ActorRef = _
  private val buffer = new BufferHelper

  override def receive: Receive = {
    case RegisterTcpActor(a) =>
      tcpActor = a
      context.become(writing)
  }

  def writing: Receive = {
    case IoWrite(data) =>
      tcpActor ! Write(data, Ack)
      context.become(buffering)
    case UnregisterTcpActor =>
      tcpActor = null
      buffer.getAndClearBuffer
      context.become(receive)
  }

  def buffering: Receive = {
    case IoWrite(data) => buffer.append(data)
    case Ack =>
      buffer.empty match {
        case true => context.become(writing)
        case _ => tcpActor ! Write(buffer.getAndClearBuffer)
      }
    case UnregisterTcpActor =>
      tcpActor = null
      buffer.getAndClearBuffer
      context.become(receive)
  }

  private class BufferHelper {
    var buffer: ByteString = ByteString.empty

    def append(d: ByteString): Unit = buffer = buffer ++ d

    def getAndClearBuffer: ByteString = {
      val r = buffer
      buffer = ByteString.empty
      r
    }

    def empty: Boolean = buffer.isEmpty
  }
}
