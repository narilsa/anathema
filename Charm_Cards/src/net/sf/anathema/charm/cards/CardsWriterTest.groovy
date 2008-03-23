package net.sf.anathema.charm.cards

class CardsWriterTest extends GroovyTestCase {
	void testWritesEmptyCardsList() {
		assertEquals("<cards></cards>",new CardsWriter().write());
	}
}