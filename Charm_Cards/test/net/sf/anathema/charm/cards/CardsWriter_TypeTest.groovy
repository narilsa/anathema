package net.sf.anathema.charm.cards;
import static net.sf.anathema.charm.cards.DummyCharmFactory.*
import net.sf.anathema.character.generic.type.CharacterType
import net.sf.anathema.charm.cards.CardsWriter
import net.sf.anathema.character.generic.magic.ICharm
import org.custommonkey.xmlunit.XMLUnit
import org.custommonkey.xmlunit.Diffimport net.sf.anathema.character.generic.magic.ICharmimport org.custommonkey.xmlunit.DifferenceListener
import net.sf.anathema.character.generic.impl.backgrounds.CharacterTypeBackgroundTemplateimport org.custommonkey.xmlunit.ElementNameQualifier
class CardsWriter_TypeTest extends GroovyTestCase {
  static def CARD_WITH_CHARACTERTYPE= '''
    <cards>
      <card>
        <exalt>Lunar</exalt>
      </card>
    </cards>
  '''
  
  static def CARD_WITH_OTHER_CHARACTERTYPE= '''
    <cards>
      <card>
        <exalt>Solar</exalt>
      </card>
    </cards>
  '''
    void setUp() {
	  XMLUnit.setIgnoreWhitespace(true)
    }
	
	void testWritesCharacterTypeElement(){
	  String testXml = new CardsWriter().write(createCharm("testId"))
	  assertDifferenceLeniently(CARD_WITH_CHARACTERTYPE, testXml)
	}
	
	void testWritesCharacterTypeElementWithCharacterType(){
	  String testXml = new CardsWriter().write(createCharm(CharacterType.SOLAR))
	  assertDifferenceLeniently(CARD_WITH_OTHER_CHARACTERTYPE, testXml)
	}
	
	void assertDifferenceLeniently(controlXml, testXml){
	  Diff diff = new Diff(controlXml, testXml)
	  diff.overrideDifferenceListener(new LenientDifferenceListener())
	  assert diff.similar()
	}
}