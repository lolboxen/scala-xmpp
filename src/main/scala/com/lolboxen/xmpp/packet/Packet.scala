package com.lolboxen.xmpp.packet

import akka.util.ByteString
import com.lolboxen.xmpp.Jid

/**
 * Created by Trent Ahrens on 12/6/14.
 */
trait Packet {
  def to: Option[Jid] = None
  def from: Option[Jid] = None
  def byteString: ByteString
}