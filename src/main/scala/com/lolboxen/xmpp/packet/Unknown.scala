package com.lolboxen.xmpp.packet

import akka.util.ByteString

import scala.xml.Elem

/**
 * Created by Trent Ahrens on 12/9/14.
 */
case class Unknown(e: Elem) extends Packet {
  override def byteString: ByteString = ByteString(e.toString())
}
