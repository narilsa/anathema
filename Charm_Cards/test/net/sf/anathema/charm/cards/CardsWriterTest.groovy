package net.sf.anathema.charm.cards

import net.sf.anathema.character.generic.magic.*
import static net.sf.anathema.charm.cards.DummyCharmFactory.*
import org.custommonkey.xmlunit.*


class CardsWriterTest extends GroovyTestCase {
	static def EMPTYLIST = '''
    <cards/>
  '''

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

	def writer
	def cardsWriter

	void setUp(){
		XMLUnit.ignoreWhitespace = true
		writer = new StringWriter()
		cardsWriter = new CardsWriter(writer: writer)
	}

	void testWritesEmptyCardsList(){
		cardsWriter.write null
		assert new Diff(EMPTYLIST, writer.toString()).similar()
	}

	void testWritesSingleCardForSingleCharm(){
		cardsWriter.write(createCharm([id: "testId"]))
		Diff diff = new Diff(SINGLECARD, writer.toString())
		println writer.toString()
		DifferenceListener listener = new LayerDifferenceListener()
		diff.overrideDifferenceListener(listener)
		assert diff.similar()
	}

	void testMultipleCardsForMultipleObjects(){
		ICharm[] charms = [createCharm([id: "testId"]), createCharm([id: "toastId"])]
		cardsWriter.write(charms)
		Diff diff = new Diff(MULTICARD, writer.toString())
		println writer.toString()
		DifferenceListener listener = new LayerDifferenceListener()
		diff.overrideDifferenceListener(listener)
		assert diff.similar()
	}
}