package com.lolboxen.xmpp.packet.parsing

import com.lolboxen.xmpp.packet.{Unknown, Packet}

import scala.xml.Elem

/**
 * Created by Trent Ahrens on 12/9/14.
 */
class UnknownPacketParser extends ElemParser {
  override def canParse(s: Elem): Boolean = false

  override def parse(e: Elem): Packet = Unknown(e)

  override def isDefault: Boolean = true
}
