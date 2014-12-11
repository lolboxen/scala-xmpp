package com.lolboxen.xmpp.packet

import com.lolboxen.xmpp.Jid

/**
 * Created by Trent Ahrens on 12/5/14.
 */
case class StreamBegan(id: String, version: Float, from: Jid) extends Packet

case object StreamEnded extends Packet
