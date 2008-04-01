package net.sf.anathema.charm.cards

import static net.sf.anathema.charm.cards.DummyCharmFactory.*
import org.custommonkey.xmlunit.*
import org.junit.*

class CardsWriter_KeywordTest {

	static def CARD_WITH_KEYWORD = '''
    <cards>
      <card>
			<keywords>
        <keyword>word</keyword>
			</keywords>
      </card>
    </cards>
  '''

	static def CARD_WITH_OTHER_KEYWORD = '''
    <cards>
      <card>
				<keywords>
        <keyword>wort</keyword>
				</keywords>
      </card>
    </cards>
  '''

	static def CARD_WITH_TWO_WORDS = '''
    <cards>
      <card>
				<keywords>
        <keyword>wort</keyword>
				<keyword>word</keyword>
				</keywords>
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
	void writesKeywordElement(){
		cardsWriter.write createCharm([keywords: ["word"]])
		LenientDifferenceListener.assertDifferenceLeniently(CARD_WITH_KEYWORD, writer.toString())
	}

	@Test
	void writesKeywordWithContent(){
		cardsWriter.write createCharm([keywords: ["wort"]])
		LenientDifferenceListener.assertDifferenceLeniently(CARD_WITH_OTHER_KEYWORD, writer.toString())
	}

	@Test
	void writesAllKeywords(){
		cardsWriter.write createCharm([keywords: ["wort", "word"]])
		LenientDifferenceListener.assertDifferenceLeniently(CARD_WITH_TWO_WORDS, writer.toString())
	}
}