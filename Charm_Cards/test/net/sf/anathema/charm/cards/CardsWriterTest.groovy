package net.sf.anathema.charm.cards;

import net.sf.anathema.charm.cards.CardsWriter;

class CardsWriterTest extends GroovyTestCase {
	void testWritesEmptyCardsList() {
		assertEquals("<cards></cards>",new CardsWriter().write());
	}
}