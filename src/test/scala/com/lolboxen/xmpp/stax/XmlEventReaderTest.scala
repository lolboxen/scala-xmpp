package com.lolboxen.xmpp.stax

import akka.util.ByteString
import org.scalatest.{FlatSpec, Matchers}

import scala.xml.{NamespaceBinding, Null, Attribute}
import scala.xml.pull.{EvText, EvElemEnd, EvElemStart}

/**
 * Created by Trent Ahrens on 12/10/14.
 */
class XmlEventReaderTest extends FlatSpec with Matchers {

  val xml = """<a:z xmlns:a="foobars" k="v"><a:y/><x>&amp;test</x><w xmlns="localns" /></a:z>"""

  "reader" should "output correct event sequence" in {
    val xmlReader = new XmlEventReader()
    xmlReader.feed(ByteString(xml))
    xmlReader.next() shouldBe EvElemStart("a", "z", Attribute(null, "k", "v", Null), NamespaceBinding("a", "foobars", null))
    xmlReader.next() shouldBe EvElemStart("a", "y", Null, NamespaceBinding("a", "foobars", null))
    xmlReader.next() shouldBe EvElemEnd("a", "y")
    xmlReader.next() shouldBe EvElemStart(null, "x", Null, null)
    xmlReader.next() shouldBe EvText("&amp;test")
    xmlReader.next() shouldBe EvElemEnd(null, "x")
    xmlReader.next() shouldBe EvElemStart(null, "w", Null, NamespaceBinding(null, "localns", null))
    xmlReader.next() shouldBe EvElemEnd(null, "w")
    xmlReader.next() shouldBe EvElemEnd("a", "z")
  }

}
