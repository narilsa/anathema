package net.sf.anathema.charm.cards

import static net.sf.anathema.charm.cards.DummyCharmFactory.*
import org.custommonkey.xmlunit.*


class CardsWriter_NameTest extends GroovyTestCase {
	static def CARD_WITH_NAME = '''
    <cards>
      <card>
        <name>TESTID</name>
      </card>
    </cards>
  '''

	static def CARD_WITH_OTHER_NAME = '''
    <cards>
      <card>
        <name>TOASTID</name>
      </card>
    </cards>
  '''

	def names
	def writer
	def cardsWriter

	void setUp(){
		XMLUnit.ignoreWhitespace = true
		names = [getString: {key, arguments -> key.toUpperCase()}]
		writer = new StringWriter()
		cardsWriter = new CardsWriter(writer: writer, provider: names)
	}

	void testWritesNameElement(){
		cardsWriter.write(createCharm([id: "testId"]))
		LenientDifferenceListener.assertDifferenceLeniently(CARD_WITH_NAME, writer.toString())
	}

	void testFillsNameElementWithId(){
		cardsWriter.write(createCharm([id: "toastId"]))
		LenientDifferenceListener.assertDifferenceLeniently(CARD_WITH_OTHER_NAME, writer.toString())
	}
}