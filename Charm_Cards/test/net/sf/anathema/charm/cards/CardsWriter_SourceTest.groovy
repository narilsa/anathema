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
					<page>4512</page>
				</source>
      </card>
    </cards>
  '''

	def writer
	def cardsWriter
	def page
	def book = "book"
	def pages = [getString: {key, arguments -> if (key == book + ".id.Page") return page}]

	@Before
	void setUp(){
		XMLUnit.ignoreWhitespace = true
		writer = new StringWriter()
		cardsWriter = new CardsWriter(writer: writer, provider: pages)
	}

	@Test
	void writesSourceElement(){
		page = 4211
		cardsWriter.write createCharm([:])
		LenientDifferenceListener.assertDifferenceLeniently(CARD_WITH_SOURCE, writer.toString())
	}

	@Test
	void writesPage(){
		page = 4411
		cardsWriter.write createCharm([page: page])
		LenientDifferenceListener.assertDifferenceLeniently(CARD_WITH_OTHER_PAGE, writer.toString())
	}

	@Test
	void writesBookAsId(){
		page = 4512
		book = "Ex3"
		cardsWriter.write createCharm([book: book, page: page])
		LenientDifferenceListener.assertDifferenceLeniently(CARD_WITH_OTHER_BOOK, writer.toString())
	}
}