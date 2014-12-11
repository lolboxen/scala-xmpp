package com.lolboxen.xmpp.packet.parsing

import com.lolboxen.xmpp.packet.Packet

import scala.xml.Elem
import scala.xml.pull.XMLEvent

/**
 * Created by Trent Ahrens on 12/5/14.
 */
trait PacketParser

trait EventParser extends PacketParser {
  def canParse(s: Seq[XMLEvent]): Boolean
  def parseStart(s: Seq[XMLEvent]): Packet
  def parseEnd(s: Seq[XMLEvent]): Packet
}

trait ElemParser extends PacketParser {
  def canParse(e: Elem): Boolean
  def parse(e: Elem): Packet
  def isDefault: Boolean
}