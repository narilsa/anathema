package net.sf.anathema.charm.cards

import net.sf.anathema.character.generic.magic.charms.type.*
import static net.sf.anathema.charm.cards.DummyCharmFactory.*
import org.custommonkey.xmlunit.*

class CardsWriter_StatsTest extends GroovyTestCase {

	static def CARD_WITH_DUMMYCOST = '''
    <cards>
      <card>
        <stats>
			<cost>Cost</cost>
		</stats>
      </card>
    </cards>
  '''

	static def CARD_WITH_DURATION = '''
    <cards>
      <card>
        <stats>
			<duration>Instant</duration>
		</stats>
      </card>
    </cards>
  '''

	static def CARD_WITH_PERMANENT_DURATION = '''
    <cards>
      <card>
        <stats>
			<duration>Permanent</duration>
		</stats>
      </card>
    </cards>
  '''

	static def CARD_WITH_EXTRAACTION = '''
    <cards>
      <card>
        <stats>
			<type>ExtraAction</type>
		</stats>
      </card>
    </cards>
  '''

	static def CARD_WITH_SUPPLEMENTAL = '''
    <cards>
      <card>
        <stats>
			<type>Supplemental</type>
		</stats>
      </card>
    </cards>
  '''

	static def CARD_WITH_FULLSIMPLE = '''
    <cards>
      <card>
        <stats>
			<type>Simple (Speed 5, DV -2)</type>
		</stats>
      </card>
    </cards>
  '''

	static def CARD_WITH_SPEEDSIMPLE = '''
    <cards>
      <card>
        <stats>
			<type>Simple (Speed 3)</type>
		</stats>
      </card>
    </cards>
  '''
	static def CARD_WITH_DVSIMPLE = '''
    <cards>
      <card>
        <stats>
			<type>Simple (DV -3)</type>
		</stats>
      </card>
    </cards>
  '''

	static def CARD_WITH_FULLREFLEXIVE = '''
    <cards>
      <card>
        <stats>
			<type>Reflexive (Step 2 or 3)</type>
		</stats>
      </card>
    </cards>
  '''

	static def CARD_WITH_HALFREFLEXIVE = '''
    <cards>
      <card>
        <stats>
			<type>Reflexive (Step 4)</type>
		</stats>
      </card>
    </cards>
  '''

	def writer
	def cardsWriter
	def pages = [:]

	void setUp(){
		XMLUnit.ignoreWhitespace = true
		writer = new StringWriter()
		cardsWriter = new CardsWriter(writer: writer)
	}

	void testWritesStatsElementWithCharmTypeId(){
		cardsWriter.write(createCharm([id: "testId"]))
		LenientDifferenceListener.assertDifferenceLeniently(CARD_WITH_EXTRAACTION, writer.toString())
	}

	void testWritesStatsElementWithSupplementalCharmTypeId(){
		cardsWriter.write(createCharm([charmType: CharmType.Supplemental]))
		LenientDifferenceListener.assertDifferenceLeniently(CARD_WITH_SUPPLEMENTAL, writer.toString())
	}

	void testWritesSimpleTypeWithDVandSpeed(){
		cardsWriter.write(createCharm([charmType: CharmType.Simple, dv: -2, speed: 5]))
		LenientDifferenceListener.assertDifferenceLeniently(CARD_WITH_FULLSIMPLE, writer.toString())
	}

	void testWritesSimpleTypeWithDV(){
		cardsWriter.write(createCharm([charmType: CharmType.Simple, dv: -3, speed: 6]))
		LenientDifferenceListener.assertDifferenceLeniently(CARD_WITH_DVSIMPLE, writer.toString())
	}

	void testWritesSimpleTypeWithSpeed(){
		cardsWriter.write(createCharm([charmType: CharmType.Simple, dv: -1, speed: 3]))
		LenientDifferenceListener.assertDifferenceLeniently(CARD_WITH_SPEEDSIMPLE, writer.toString())
	}

	void testWritesReflexiveWithSteps(){
		cardsWriter.write(createCharm([charmType: CharmType.Reflexive, step1: 2, step2: 3]))
		LenientDifferenceListener.assertDifferenceLeniently(CARD_WITH_FULLREFLEXIVE, writer.toString())
	}

	void testOmitsNullStep(){
		cardsWriter.write(createCharm([charmType: CharmType.Reflexive, step1: 4]))
		LenientDifferenceListener.assertDifferenceLeniently(CARD_WITH_HALFREFLEXIVE, writer.toString())
	}

	void testWritesDummyCost(){
		cardsWriter.write(createCharm([:]))
		LenientDifferenceListener.assertDifferenceLeniently(CARD_WITH_DUMMYCOST, writer.toString())
	}

	void testWritesDurationElement(){
		cardsWriter.write(createCharm([duration: "Instant"]))
		LenientDifferenceListener.assertDifferenceLeniently(CARD_WITH_DURATION, writer.toString())
	}

	void testWritesDurationText(){
		cardsWriter.write(createCharm([duration: "Permanent"]))
		LenientDifferenceListener.assertDifferenceLeniently(CARD_WITH_PERMANENT_DURATION, writer.toString())
	}
}