package net.sf.anathema.charm

import net.sf.anathema.character.generic.magic.*
import net.sf.anathema.charm.cards.*



class CharmReaderTest extends GroovyTestCase {def file = new File('B:/Workspaces/Anathema_Outcaste/Charm_Cards/test/net/sf/anathema/charm/cards/Three_Sample_Charms.xml')

	void testReturnsEmptyList(){
		assert new CharmReader().read(null).size == 0
	}

	void testFillsListFromSampleFile(){
		assert new CharmReader().read(file).size == 3
	}

	void testReturnsInstancesOfCharmFromSampleFile(){
		new CharmReader().read(file).each {charm -> assert charm instanceof ICharm};
	}
}