package com.lolboxen.xmpp.helper

import akka.util.ByteString
import com.lolboxen.xmpp.Jid

import scala.language.postfixOps
import scala.xml._

/**
 * Created by Trent Ahrens on 12/12/14.
 */
object XmppPacketBuilder {

  def stream: XmppPacketBuilder = "stream" :: "stream" ns("stream", "http://etherx.jabber.org/streams") ns "jabber:client" nonclosing

  implicit def elemToByteString(e: Elem): ByteString = ByteString(e.toString())

  implicit def packetBuilderToString(pb: XmppPacketBuilder): String = pb.render

  implicit def packetBuilderToByteString(pb: XmppPacketBuilder): ByteString = ByteString(pb.render)

  implicit class StringXmppPacketBuilder(val right: String) {
    def ::(left: String) = new XmppPacketBuilder(left, right)
  }

  implicit class XmppPacketBuilderStream(val pb: XmppPacketBuilder) {
    def id(value: String) = pb.attr("id", value)
    def version(value: String) = pb.attr("version", value)
  }
}

class XmppPacketBuilder(val prefix: String, val name: String) {

  var attributes: List[Attribute] = List.empty
  var namespaces: List[NamespaceBinding] = List.empty
  var closingSlash: String = "/"

  def attr(prefix: String, name: String, value: String): XmppPacketBuilder = {
    attributes = attributes :+ Attribute(prefix, name, value, Null)
    this
  }

  def attr(name: String, value: String): XmppPacketBuilder = attr(null, name, value)

  def ns(prefix: String, uri: String): XmppPacketBuilder = {
    namespaces = namespaces :+ NamespaceBinding(prefix, uri, null)
    this
  }

  def nonclosing: XmppPacketBuilder = {
    closingSlash = ""
    this
  }

  def ns(uri: String): XmppPacketBuilder = ns(null, uri)

  def from(right: Jid): XmppPacketBuilder = attr(null, "from", right.asString)

  def from(right: Option[Jid]): XmppPacketBuilder = right match {
    case Some(jid) => from(jid)
    case None => this
  }

  def to(right: Jid): XmppPacketBuilder = attr(null, "to", right.asString)

  def to(right: Option[Jid]): XmppPacketBuilder = right match {
    case Some(jid) => to(jid)
    case None => this
  }

  def version(right: String): XmppPacketBuilder = attr(null, "version", right)

  override def toString: String = render

  def render: String = s"<${renderName(prefix,name)}$renderNamespaces$renderAttributes$closingSlash>"

  private def renderAttributes: String = attributes.foldRight("") {
      case (PrefixedAttribute(p,n,v,_), s) => s"""$s ${renderName(p,n)}="$v""""
      case (UnprefixedAttribute(n, v, _), s) => s"""$s ${renderName(null,n)}="$v""""
    }

  private def renderNamespaces: String = namespaces.foldRight("") {
    case (NamespaceBinding(p,u,_),s) => s"""$s xmlns${renderNamespace(p)}="$u""""
  }

  private def renderName(p: String, n: String): String = p match {
    case "" => s"$n"
    case null => s"$n"
    case _ => s"$p:$n"
  }

  private def renderNamespace(p: String): String = p match {
    case "" => ""
    case null => ""
    case _ => s":$p"
  }
}
