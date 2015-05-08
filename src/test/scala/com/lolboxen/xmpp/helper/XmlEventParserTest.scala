package com.lolboxen.xmpp.helper

import com.lolboxen.test.UnitSpec

import scala.xml.pull.{EvElemEnd, EvElemStart, EvText, XMLEvent}
import scala.xml.{Attribute, NamespaceBinding, Null}

/**
 * Created by Trent Ahrens on 12/9/14.
 */
class XmlEventParserTest extends UnitSpec {

  it should "correctly parse sequence of xml events" in {
    val events: Seq[XMLEvent] = List(
      EvElemStart("prefix", "label", Attribute(null, "key", "val", Attribute("prefix", "key", "val", Null)), new NamespaceBinding("prefix", "urn:ietf:params:xml:ns:xmpp-sasl", null)),
      EvElemStart(null, "l2", Null, new NamespaceBinding(null, null, null)),
      EvText("sample text"),
      EvElemEnd(null, "l2"),
      EvElemEnd("prefix", "label"))
    XmlEventParser.parseEventsAsString(events) shouldBe """<prefix:label xmlns:prefix="urn:ietf:params:xml:ns:xmpp-sasl" key="val" prefix:key="val"><l2 xmlns="">sample text</l2></prefix:label>"""
  }

}
