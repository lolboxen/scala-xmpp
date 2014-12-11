package com.lolboxen.xmpp.stax

import akka.util.ByteString
import org.scalatest.{FlatSpec, Matchers}

import scala.xml.{NamespaceBinding, Null, Attribute}
import scala.xml.pull.{EvElemEnd, EvElemStart}

/**
 * Created by Trent Ahrens on 12/10/14.
 */
class XmlEventReaderTest extends FlatSpec with Matchers {

  val xml = """<a:z xmlns:a="foobars" k="v"><a:y/><x></x><w xmlns="localns" /></a:z>"""

  "reader" should "output correct event sequence" in {
    val xmlReader = new XmlEventReader()
    xmlReader.feed(ByteString(xml))
    xmlReader.next() should be (EvElemStart("a", "z", Attribute(null, "k", "v", Null), NamespaceBinding("a", "foobars", null)))
    xmlReader.next() should be (EvElemStart("a", "y", Null, NamespaceBinding("a", "foobars", null)))
    xmlReader.next() should be (EvElemEnd("a", "y"))
    xmlReader.next() should be (EvElemStart(null, "x", Null, null))
    xmlReader.next() should be (EvElemEnd(null, "x"))
    xmlReader.next() should be (EvElemStart(null, "w", Null, NamespaceBinding(null, "localns", null)))
    xmlReader.next() should be (EvElemEnd(null, "w"))
    xmlReader.next() should be (EvElemEnd("a", "z"))
  }

}
