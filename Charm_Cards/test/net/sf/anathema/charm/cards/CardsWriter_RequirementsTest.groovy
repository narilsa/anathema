package net.sf.anathema.charm.cards

import static net.sf.anathema.charm.cards.DummyCharmFactory.*
import org.custommonkey.xmlunit.*
import org.junit.*

class CardsWriter_RequirementsTest {

	static def CARD_WITH_REQUIREMENT = '''
    <cards>
      <card>
			<requirements>
			<trait>2</trait>
			<essence>3</essence>
			</requirements>
      </card>
    </cards>
  '''

	static def CARD_WITH_ESSENCE_REQUIREMENT = '''
    <cards>
      <card>
				<requirements>
					<essence>4</essence>
			</requirements>
      </card>
    </cards>
  '''

	static def CARD_WITH_TRAIT_REQUIREMENT = '''
    <cards>
      <card>
				<requirements>
			<trait>3</trait>
			</requirements>
      </card>
    </cards>
  '''

	def writer
	def cardsWriter
	def pages = [:]

	@Before
	void setUp(){
		XMLUnit.ignoreWhitespace = true
		writer = new StringWriter()
		cardsWriter = new CardsWriter(writer: writer)
	}

	@Test
	void writesRequirementsElement(){
		cardsWriter.write createCharm([essence: 3, trait: 2])
		LenientDifferenceListener.assertDifferenceLeniently(CARD_WITH_REQUIREMENT, writer.toString())
	}

	@Test
	void writesEssenceRequirement(){
		cardsWriter.write createCharm([essence: 4, trait: 2])
		LenientDifferenceListener.assertDifferenceLeniently(CARD_WITH_ESSENCE_REQUIREMENT, writer.toString())
	}

	@Test
	void writesTraitRequirement(){
		cardsWriter.write createCharm([essence: 3, trait: 3])
		LenientDifferenceListener.assertDifferenceLeniently(CARD_WITH_TRAIT_REQUIREMENT, writer.toString())
	}
}