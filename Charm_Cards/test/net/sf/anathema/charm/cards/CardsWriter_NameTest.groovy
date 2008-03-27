package net.sf.anathema.charm.cards;
import static net.sf.anathema.charm.cards.DummyCharmFactory.*
import net.sf.anathema.character.generic.type.CharacterType
import net.sf.anathema.charm.cards.CardsWriter
import net.sf.anathema.character.generic.magic.ICharm
import org.custommonkey.xmlunit.XMLUnit
import org.custommonkey.xmlunit.Diffimport net.sf.anathema.character.generic.magic.ICharmimport org.custommonkey.xmlunit.DifferenceListener
import net.sf.anathema.character.generic.impl.backgrounds.CharacterTypeBackgroundTemplateimport org.custommonkey.xmlunit.ElementNameQualifier
class CardsWriter_NameTest extends GroovyTestCase {
  
    void setUp() {
	  XMLUnit.setIgnoreWhitespace(true)
    }
  
	void testWritesNameElement(){
	  String charmXml = new CardsWriter().write(createCharm("testId"))
	  assertDifferenceLeniently(CardXmlSamples.CARD_WITH_NAME, charmXml)
	}
	
	void testFillsNameElementWithId(){
	  String charmXml = new CardsWriter().write(createCharm("toastId"))
	  assertDifferenceLeniently(CardXmlSamples.CARD_WITH_OTHER_NAME, charmXml)
	}
	
	void assertDifferenceLeniently(controlXml, testXml){
	  Diff diff = new Diff(controlXml, testXml)
	  diff.overrideDifferenceListener(new LenientDifferenceListener())
	  assert diff.similar()
	}
}