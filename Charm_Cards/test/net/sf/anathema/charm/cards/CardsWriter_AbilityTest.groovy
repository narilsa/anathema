package net.sf.anathema.charm.cards

import net.sf.anathema.character.generic.traits.types.*
import static net.sf.anathema.charm.cards.DummyCharmFactory.*
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

	def writer
	def cardsWriter

	void setUp(){
		XMLUnit.ignoreWhitespace = true
		writer = new StringWriter()
		cardsWriter = new CardsWriter(writer: writer)
	}

	void testWritesAbilityElement(){
		cardsWriter.write(createCharm([:]))
		LenientDifferenceListener.assertDifferenceLeniently(CARD_WITH_ABILITYTYPE, writer.toString())
	}

	void testWritesAbilityElementWithAbility(){
		cardsWriter.write(createCharm([ability: AbilityType.Athletics]))
		LenientDifferenceListener.assertDifferenceLeniently(CARD_WITH_OTHER_ABILITYTYPE, writer.toString())
	}
}