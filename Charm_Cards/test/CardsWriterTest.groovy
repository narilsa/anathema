package net.sf.anathema.charm

import net.sf.anathema.character.generic.magic.*
import static net.sf.anathema.charm.DummyCharmFactory.*
import net.sf.anathema.charm.cards.*
import org.custommonkey.xmlunit.*






class CardsWriterTest extends GroovyTestCase {static def EMPTYLIST = """
    <cards/>
  """

	static def SINGLECARD = '''
    <cards>
      <card />
    </cards>
  '''

	static def MULTICARD = '''
    <cards>
      <card />
      <card />
    </cards>
  '''

	void setUp(){
		XMLUnit.ignoreWhitespace = true
	}

	void testWritesEmptyCardsList(){
		def xmlDiff = new Diff(new CardsWriter().write(null), EMPTYLIST)
		assert xmlDiff.similar()
	}

	void testWritesSingleCardForSingleCharm(){
		String charmXml = new CardsWriter().write(createCharm("testId"))
		Diff diff = new Diff(charmXml, SINGLECARD)
		DifferenceListener listener = new LayerDifferenceListener()
		diff.overrideDifferenceListener(listener)
		assert diff.similar()
	}

	void testMultipleCardsForMultipleObjects(){
		ICharm[] charms = [createCharm("testId"), createCharm("toastId")]
		String charmXml = new CardsWriter().write(charms)
		Diff diff = new Diff(charmXml, MULTICARD)
		DifferenceListener listener = new LayerDifferenceListener()
		diff.overrideDifferenceListener(listener)
		assert diff.similar()
	}
}