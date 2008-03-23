package net.sf.anathema.charm.cards

import net.sf.anathema.character.generic.magic.ICharm;

class CharmReaderTest extends GroovyTestCase {
    def file = new File('B:/Workspaces/Anathema_Outcaste/Charm_Cards/test/net/sf/anathema/charm/cards/Three_Sample_Charms.xml')
    
	void testReturnsEmptyList() {
		assertEquals(0, new CharmReader().read(null).size);
	}
	
	void testFillsListFromSampleFile() {
		assertEquals(3, new CharmReader().read(file).size);
	}
	
	void testReturnsInstancesOfCharmFromSampleFile() {
	  new CharmReader().read(file).each {charm -> assertTrue(charm instanceof ICharm)};	  
	}
}