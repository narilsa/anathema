package net.sf.anathema.charm

import net.sf.anathema.character.generic.traits.types.*
import static net.sf.anathema.charm.DummyCharmFactory.*
import net.sf.anathema.charm.cards.*
import org.custommonkey.xmlunit.*






class CardsWriter_AbilityTest extends GroovyTestCase {static def CARD_WITH_ABILITYTYPE = '''
    <cards>
      <card>
        <ability>Archery</ability>
      </card>
    </cards>
  '''

	static def CARD_WITH_OTHER_ABILITYTYPE = '''
    <cards>
      <card>
        <ability>Athletics</ability>
      </card>
    </cards>
  '''

	void setUp(){
		XMLUnit.ignoreWhitespace = true
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