package net.sf.anathema.charm.cards;
import static net.sf.anathema.charm.cards.DummyCharmFactory.*
import net.sf.anathema.character.generic.traits.types.AbilityType
import net.sf.anathema.charm.cards.CardsWriter
import net.sf.anathema.character.generic.magic.ICharm
import org.custommonkey.xmlunit.XMLUnit
import org.custommonkey.xmlunit.Diffimport net.sf.anathema.character.generic.magic.ICharmimport org.custommonkey.xmlunit.DifferenceListener
import net.sf.anathema.character.generic.impl.backgrounds.CharacterTypeBackgroundTemplateimport org.custommonkey.xmlunit.ElementNameQualifier
class CardsWriter_AbilityTest extends GroovyTestCase {
  static def CARD_WITH_ABILITYTYPE= '''
    <cards>
      <card>
        <ability>Archery</ability>
      </card>
    </cards>
  '''
  
  static def CARD_WITH_OTHER_ABILITYTYPE= '''
    <cards>
      <card>
        <ability>Athletics</ability>
      </card>
    </cards>
  '''
    void setUp() {
	  XMLUnit.setIgnoreWhitespace(true)
    }
	
	void testWritesAbilityElement(){
	  String testXml = new CardsWriter().write(createCharm("testId"))
	  assertDifferenceLeniently(CARD_WITH_ABILITYTYPE, testXml)
	}
	
	void testWritesAbilityElementWithAbility(){
	  String testXml = new CardsWriter().write(createCharm(AbilityType.Athletics))
	  assertDifferenceLeniently(CARD_WITH_OTHER_ABILITYTYPE, testXml)
	}
	
	void assertDifferenceLeniently(controlXml, testXml){
	  Diff diff = new Diff(controlXml, testXml)
	  diff.overrideDifferenceListener(new LenientDifferenceListener())
	  assert diff.similar()
	}
}