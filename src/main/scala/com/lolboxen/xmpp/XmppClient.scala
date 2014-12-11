package com.lolboxen.xmpp

import akka.actor.{Actor, ActorRef, Props}

/**
 * Created by Trent Ahrens on 12/5/14.
 */
class XmppClient(listener: ActorRef) extends Actor {

  val connection: ActorRef = context.actorOf(Props(classOf[XmppConnection], self))

  override def receive: Receive = unconnected

  def unconnected: Receive = {
    case x: Connect => connection ! x
    case Connected =>
      listener ! Connected
      context.become(connected)
  }

  def connected: Receive = {
    case Disconnected =>
      listener ! Disconnected
      context.become(unconnected)
  }
}
