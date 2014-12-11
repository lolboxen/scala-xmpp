package com.lolboxen.xmpp

import com.lolboxen.xmpp.packet.Packet

/**
 * Created by Trent Ahrens on 12/5/14.
 */
case class Connect(host: String, port: Int, domain: String)

case object Connected

case object Disconnected

case class Send(m: Packet)

case class Received(p: Packet)