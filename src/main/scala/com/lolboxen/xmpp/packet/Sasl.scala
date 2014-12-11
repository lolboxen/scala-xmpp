package com.lolboxen.xmpp.packet

/**
 * Created by Trent Ahrens on 12/6/14.
 */
case class SaslChallenge(payload: String) extends Packet

case object SaslSuccess extends Packet