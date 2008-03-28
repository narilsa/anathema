package net.sf.anathema.charm

import static net.sf.anathema.charm.DummyCharmFactory.*
import net.sf.anathema.charm.cards.*
import org.custommonkey.xmlunit.*






class CardsWriter_NameTest extends GroovyTestCase {static def CARD_WITH_NAME = '''
    <cards>
      <card>
        <name>testId</name>
      </card>
    </cards>
  '''

	static def CARD_WITH_OTHER_NAME = '''
    <cards>
      <card>
        <name>toastId</name>
      </card>
    </cards>
  '''

	void setUp(){
		XMLUnit.ignoreWhitespace = true
	}

	void testWritesNameElement(){
		String charmXml = new CardsWriter().write(createCharm("testId"))
		assertDifferenceLeniently(CARD_WITH_NAME, charmXml)
	}

	void testFillsNameElementWithId(){
		String charmXml = new CardsWriter().write(createCharm("toastId"))
		assertDifferenceLeniently(CARD_WITH_OTHER_NAME, charmXml)
	}

	void assertDifferenceLeniently(controlXml, testXml){
		Diff diff = new Diff(controlXml, testXml)
		diff.overrideDifferenceListener(new LenientDifferenceListener())
		assert diff.similar()
	}
}