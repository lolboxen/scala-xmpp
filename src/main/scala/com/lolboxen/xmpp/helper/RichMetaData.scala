package com.lolboxen.xmpp.helper

import com.lolboxen.xmpp.Jid

import scala.xml.{Node, MetaData}

/**
 * Created by Trent Ahrens on 12/6/14.
 */
object RichMetaData {

  def first(key: String, m: MetaData): Node = firstOption(key, m).get

  def firstOption(key: String, m: MetaData): Option[Node] = for {
    s <- m.get(key)
    n <- s.headOption
  } yield n

  implicit class CommonMetaData(val m: MetaData) extends AnyVal {
    def from: Jid = fromOption.get
    def fromOption: Option[Jid] = firstOption("from", m).map(n => Jid(n.text))

    def to: Jid = toOption.get
    def toOption: Option[Jid] = firstOption("to", m).map(n => Jid(n.text))
  }


  implicit class StreamMetaData(val m: MetaData) extends AnyVal {
    def version: String = first("version", m).text

    def id: String = first("id", m).text
  }
}
