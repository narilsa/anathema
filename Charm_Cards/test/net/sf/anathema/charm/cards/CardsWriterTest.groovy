package net.sf.anathema.charm.cards;

import net.sf.anathema.charm.cards.CardsWriter;
import org.custommonkey.xmlunit.*;
import net.sf.anathema.character.generic.magic.ICharm
class CardsWriterTest extends GroovyTestCase {
	void testWritesEmptyCardsList() {
	    def xmlDiff = new Diff(new CardsWriter().write(null), CardXmlSamples.EMPTYLIST);
	    assert xmlDiff.similar();
	}
	
	void testWritesOneCardPerObject(){
	  String charmXml = new CardsWriter().write(new Object());
	  def xmlDiff = new Diff(charmXml, CardXmlSamples.SINGLECARD);
	  println charmXml;
	  println CardXmlSamples.SINGLECARD
	  assert xmlDiff.similar();
	}
}