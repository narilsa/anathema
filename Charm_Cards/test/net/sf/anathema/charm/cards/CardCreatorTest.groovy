package net.sf.anathema.charm.cards

import org.custommonkey.xmlunit.*


class CardCreatorTest extends GroovyTestCase {def input = 'B:/Workspaces/Anathema_Outcaste/Charm_Cards/test/net/sf/anathema/charm/cards/Three_Sample_Charms.xml'
	def inputFolder = 'B:/Workspaces/Anathema_Outcaste/Charm_Cards/test/net/sf/anathema/charm/cards/folder/'
	def output = 'B:/Workspaces/Anathema_Outcaste/Charm_Cards/test/net/sf/anathema/charm/cards/Three_Sample_Cards.xml'

	void testDoesNotRunWithoutParameters(){
		CardCreator.main()
	}

	void testDoesNotTryToOpenInputIfOnlyOneParameterIsProvided(){
		CardCreator.main "Missing"
	}

	void testProducesExpectedOutput(){
		CardCreator.main input, output
		File outputFile = new File(output)
		StringBuilder builder = new StringBuilder()
		outputFile.eachLine() {
			line -> builder.append(line)
		}
		XMLUnit.ignoreWhitespace = true
		Diff diff = new Diff(builder.toString(), Three_Sample_Cards.CARDS);
		assert diff.similar;
	}

	void testCreatesCardFileForEveryXmlInSubfolders(){
		CardCreator.main inputFolder
		assert new File(inputFolder, "Cards_One_Sample_Charm.xml").exists()
		assert new File(inputFolder, "Cards_Two_Sample_Charms.xml").exists()
	}

	void tearDown(){
		new File(output).delete();
		new File(inputFolder, "Cards_One_Sample_Charm.xml").delete()
		new File(inputFolder, "Cards_Two_Sample_Charms.xml").delete()
	}
}