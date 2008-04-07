package net.sf.anathema.charm.cards

import org.junit.*

class ResourceGathererTest {
	def resourceDir = "B:/Workspaces/Anathema_Outcaste/Charm_Cards/testresources/"

	def gatherer

	@Before
	void createGatherer(){
		gatherer = new ResourceGatherer(directory: resourceDir)
		gatherer.gather()
	}

	@Test
	void containsKeyForPropertyFile(){
		assert gatherer.supportsKey("key")
	}

	@Test
	void doesNotContainMissingKey(){
		assert !gatherer.supportsKey("cat")
	}

	@Test
	void containsAllProperties(){
		assert gatherer.supportsKey("cow")
	}

	@Test
	void returnsValuesForKeys(){
		assert 'moo' == gatherer.getString("cow")
	}
}