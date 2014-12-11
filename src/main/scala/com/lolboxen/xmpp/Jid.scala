package com.lolboxen.xmpp

/**
 * Created by Trent Ahrens on 12/5/14.
 */
object Jid {

  val full = """([\w\d]+)@([\w\d.]+)/([\w\d]+)""".r
  val base = """([\w\d]+)@([\w\d.]+)""".r
  val domain = """([\w\d.]+)""".r

  def apply(jid: String): Jid = jid match {
      case full(user, domain, resource) => new Jid(Some(user), domain, Some(resource))
      case base(user, domain) => new Jid(Some(user), domain, None)
      case domain(domain) => new Jid(None, domain, None)
    }
}

case class Jid(user: Option[String], domain: String, resource: Option[String]) {
  def asString: String = String.format("%s%s%s", user.map(u => u + "@").getOrElse(""), domain, resource.map(r => "/" + r).getOrElse(""))
}
