package com.lolboxen.xmpp.packet

import akka.util.ByteString

/**
 * Created by Trent Ahrens on 12/6/14.
 */
trait Packet {
  def byteString: ByteString
}