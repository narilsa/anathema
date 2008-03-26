package net.sf.anathema.charm.cards

import net.sf.anathema.charm.cards.CardCreatorimport org.custommonkey.xmlunit.XMLUnitimport org.custommonkey.xmlunit.Diff
class CardCreatorTest extends GroovyTestCase {
    def input = 'B:/Workspaces/Anathema_Outcaste/Charm_Cards/test/net/sf/anathema/charm/cards/Three_Sample_Charms.xml'
	def output = 'B:/Workspaces/Anathema_Outcaste/Charm_Cards/test/net/sf/anathema/charm/cards/Three_Sample_Cards.xml'

	void testWorksWithSingleParameter() {
		CardCreator.main(input)
	}
	    
	    
	void testProducesExpectedOutput() {
		CardCreator.main(input)
		File outputFile = new File(output)
		StringBuilder builder = new StringBuilder()
		outputFile.eachLine(){
		  line -> builder.append(line)
		}
		XMLUnit.setIgnoreWhitespace(true)
		Diff diff = new Diff(builder.toString(), Three_Sample_Cards.CARDS);
		assert diff.similar;
	}
}