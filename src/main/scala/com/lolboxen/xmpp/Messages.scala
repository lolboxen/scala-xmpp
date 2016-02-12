package com.lolboxen.xmpp

import akka.actor.ActorRef
import akka.util.ByteString
import com.lolboxen.xmpp.packet.Packet

/**
 * Created by Trent Ahrens on 12/5/14.
 */
case class Connect(host: String, port: Int, domain: String)

case object ConnectFailed

case object Connected

case object Disconnect

case object Disconnected

case class Send(m: Packet)

case class Received(p: Packet)

case class ReceivedBytes(b: ByteString)

case class RegisterTcpActor(connection: ActorRef)

case object UnregisterTcpActor