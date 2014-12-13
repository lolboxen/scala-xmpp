package com.lolboxen.xmpp

/**
 * Created by Trent Ahrens on 12/5/14.
 */
object Jid {

  val full = """([\w\d]+)@([\w\d.]+)/([\w\d]+)""".r
  val base = """([\w\d]+)@([\w\d.]+)""".r
  val domain = """([\w\d.]+)""".r

  def apply(jid: String): Jid = jid match {
    case full(u, d, r) => new Jid(Some(u), d, Some(r))
    case base(u, d) => new Jid(Some(u), d, None)
    case domain(d) => new Jid(None, d, None)
    }
}

case class Jid(user: Option[String], domain: String, resource: Option[String]) {
  def asString: String = String.format("%s%s%s", user.map(u => u + "@").getOrElse(""), domain, resource.map(r => "/" + r).getOrElse(""))
}
