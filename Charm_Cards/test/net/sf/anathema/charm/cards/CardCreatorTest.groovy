package net.sf.anathema.charm.cards

import org.custommonkey.xmlunit.*

class CardCreatorTest extends GroovyTestCase {def resourceDir = "B:/Workspaces/Anathema_Outcaste/Charm_Cards/testresources/"
	def input = resourceDir + '''Three_Sample_Charms.xml'''
	def inputFolder = resourceDir + 'folder/'
	def output = resourceDir + '''Three_Sample_Cards.xml'''

	def creator

	void setUp(){
		creator = new CardCreator(propertiesDirectory: resourceDir)
	}


	void testDoesNotThrowExceptionIfMissingSingleFileIsProvided(){
		creator.run(["Missing"])
	}

	void testProducesExpectedOutput(){
		creator.run([input, output])
		XMLUnit.ignoreWhitespace = true
		println new File(output)
		println new File(output).getText()
		Diff diff = new Diff(Three_Sample_Cards.CARDS, new File(output).getText());
		assert diff.similar;
	}

	void testCreatesCardFileForEveryXmlInSubfolders(){
		creator.run([inputFolder])
		assert new File(inputFolder, "Cards_One_Sample_Charm.xml").exists()
		assert new File(inputFolder, "Cards_Two_Sample_Charms.xml").exists()
	}

	void testIgnoresSvnFolder(){
		creator.run([inputFolder])
		assert !(new File(inputFolder, "Cards_.svn").exists())
	}

	void tearDown(){
		new File(output).delete();
		//new File(inputFolder, "Cards_One_Sample_Charm.xml").delete()
		//new File(inputFolder, "Cards_Two_Sample_Charms.xml").delete()
	}
}