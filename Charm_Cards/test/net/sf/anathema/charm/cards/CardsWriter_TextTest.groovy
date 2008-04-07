package net.sf.anathema.charm.cards

import static net.sf.anathema.charm.cards.DummyCharmFactory.*
import org.custommonkey.xmlunit.*
import org.junit.*

class CardsWriter_TextTest {

	static def CARD_WITH_TEXT = '''
	   <cards>
	     <card>
			 <text>
				 <para>Charm Text goes here.</para>
		   </text>
	     </card>
	   </cards>
	 '''

	def writer
	def cardsWriter

	@Before
	void setUp(){
		XMLUnit.ignoreWhitespace = true
		writer = new StringWriter()
		cardsWriter = new CardsWriter(writer: writer)
	}

	@Test
	void writesDummyCharmText(){
		cardsWriter.write createCharm([motes: 81])
		LenientDifferenceListener.assertDifferenceLeniently(CARD_WITH_TEXT, writer.toString())
	}
}