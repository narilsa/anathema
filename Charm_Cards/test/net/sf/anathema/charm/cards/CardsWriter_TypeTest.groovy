package net.sf.anathema.charm.cards

import net.sf.anathema.character.generic.type.*
import static net.sf.anathema.charm.cards.DummyCharmFactory.*
import org.custommonkey.xmlunit.*


class CardsWriter_TypeTest extends GroovyTestCase {
	static def CARD_WITH_CHARACTERTYPE = '''
    <cards>
      <card>
        <exalt>Lunar</exalt>
      </card>
    </cards>
  '''

	static def CARD_WITH_OTHER_CHARACTERTYPE = '''
    <cards>
      <card>
        <exalt>Solar</exalt>
      </card>
    </cards>
  '''

	void setUp(){
		XMLUnit.ignoreWhitespace = true
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