package net.sf.anathema.charm.cards

import static net.sf.anathema.charm.cards.DummyCharmFactory.*
import org.custommonkey.xmlunit.*
import org.junit.*

class CardsWriter_SourceTest {

	static def CARD_WITH_SOURCE = '''
    <cards>
      <card>
        <source>
					<title>book</title>
					<page>4211</page>	
				</source>
      </card>
    </cards>
  '''

	static def CARD_WITH_OTHER_PAGE = '''
    <cards>
      <card>
        <source>
					<page>4411</page>
				</source>
      </card>
    </cards>
  '''

	static def CARD_WITH_OTHER_BOOK = '''
    <cards>
      <card>
        <source>
					<title>Ex3</title>
				</source>
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
		cardsWriter = new CardsWriter(writer: writer, pages: pages)
	}

	@Test
	void writesSourceElement(){
		pages["book.id.Page"] = 4211
		cardsWriter.write createCharm([:])
		LenientDifferenceListener.assertDifferenceLeniently(CARD_WITH_SOURCE, writer.toString())
	}

	@Test
	void writesPage(){
		pages["book.id.Page"] = 4411
		cardsWriter.write createCharm([page: 4411])
		LenientDifferenceListener.assertDifferenceLeniently(CARD_WITH_OTHER_PAGE, writer.toString())
	}

	@Test
	void writesBookAsId(){
		cardsWriter.write createCharm([book: "Ex3"])
		LenientDifferenceListener.assertDifferenceLeniently(CARD_WITH_OTHER_BOOK, writer.toString())
	}
}