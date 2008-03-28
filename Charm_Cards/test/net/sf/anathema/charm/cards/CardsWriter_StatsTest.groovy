package net.sf.anathema.charm.cards

import net.sf.anathema.character.generic.magic.charms.type.CharmType
import net.sf.anathema.charm.cards.CardsWriter
import static net.sf.anathema.charm.cards.DummyCharmFactory.createCharm
import org.custommonkey.xmlunit.Diff
import org.custommonkey.xmlunit.XMLUnit

class CardsWriter_StatsTest extends GroovyTestCase {
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

    void setUp() {
        XMLUnit.setIgnoreWhitespace(true)
    }

    void testWritesStatsElementWithCharmTypeId() {
        String testXml = new CardsWriter().write(createCharm([id: "testId"]))
        assertDifferenceLeniently(CARD_WITH_EXTRAACTION, testXml)
    }

    void testWritesStatsElementWithSupplementalCharmTypeId() {
        String testXml = new CardsWriter().write(createCharm([charmType: CharmType.Supplemental]))
        assertDifferenceLeniently(CARD_WITH_SUPPLEMENTAL, testXml)
    }

    void testWritesSimpleTypeWithDVandSpeed() {
        String testXml = new CardsWriter().write(createCharm([charmType: CharmType.Simple, dv: -2, speed: 5]))
        assertDifferenceLeniently(CARD_WITH_FULLSIMPLE, testXml)
    }

    void testWritesSimpleTypeWithDV() {
        String testXml = new CardsWriter().write(createCharm([charmType: CharmType.Simple, dv: -3, speed: 6]))
        assertDifferenceLeniently(CARD_WITH_DVSIMPLE, testXml)
    }

    void testWritesSimpleTypeWithSpeed() {
        String testXml = new CardsWriter().write(createCharm([charmType: CharmType.Simple, dv: -1, speed: 3]))
        assertDifferenceLeniently(CARD_WITH_SPEEDSIMPLE, testXml)
    }

    void testWritesReflexiveWithSteps() {
        String testXml = new CardsWriter().write(createCharm([charmType: CharmType.Reflexive, step1: 2, step2: 3]))
        assertDifferenceLeniently(CARD_WITH_FULLREFLEXIVE, testXml)
    }

    void testOmitsNullStep() {
        String testXml = new CardsWriter().write(createCharm([charmType: CharmType.Reflexive, step1: 4]))
        assertDifferenceLeniently(CARD_WITH_HALFREFLEXIVE, testXml)
    }

    void assertDifferenceLeniently(controlXml, testXml) {
        Diff diff = new Diff(controlXml, testXml)
        diff.overrideDifferenceListener(new LenientDifferenceListener())
        println diff.toString()
        assert diff.similar()
    }
}