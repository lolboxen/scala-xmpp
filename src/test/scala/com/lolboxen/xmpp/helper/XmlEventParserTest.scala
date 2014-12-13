package com.lolboxen.xmpp.helper

import org.scalatest.{FlatSpec, Matchers}

import scala.xml.pull.{EvElemEnd, EvElemStart, EvText, XMLEvent}
import scala.xml.{Attribute, NamespaceBinding, Null}

/**
 * Created by Trent Ahrens on 12/9/14.
 */
class XmlEventParserTest extends FlatSpec with Matchers {

  it should "correctly parse sequence of xml events" in {
    val events: Seq[XMLEvent] = List(
      EvElemStart("prefix", "label", Attribute(null, "key", "val", Attribute("prefix", "key", "val", Null)), new NamespaceBinding("prefix", "urn:ietf:params:xml:ns:xmpp-sasl", null)),
      EvElemStart(null, "l2", Null, new NamespaceBinding(null, null, null)),
      EvText("sample text"),
      EvElemEnd(null, "l2"),
      EvElemEnd("prefix", "label"))
    XmlEventParser.parseEventsAsString(events) should be ("""<prefix:label xmlns:prefix="urn:ietf:params:xml:ns:xmpp-sasl" key="val" prefix:key="val"><l2 xmlns="">sample text</l2></prefix:label>""")
  }

}
