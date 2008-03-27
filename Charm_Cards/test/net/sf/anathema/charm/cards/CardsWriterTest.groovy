package net.sf.anathema.charm.cards;

import static net.sf.anathema.charm.cards.DummyCharmFactory.*
import net.sf.anathema.character.generic.type.CharacterType
import net.sf.anathema.charm.cards.CardsWriter
import net.sf.anathema.character.generic.magic.ICharm
import org.custommonkey.xmlunit.XMLUnit
import org.custommonkey.xmlunit.Diffimport net.sf.anathema.character.generic.magic.ICharmimport org.custommonkey.xmlunit.DifferenceListener
import net.sf.anathema.character.generic.impl.backgrounds.CharacterTypeBackgroundTemplateimport org.custommonkey.xmlunit.ElementNameQualifier
class CardsWriterTest extends GroovyTestCase {
  static def EMPTYLIST = '''
    <cards/>
  '''
  
  static def SINGLECARD= '''
    <cards>
      <card />
    </cards>
  '''
  
  static def MULTICARD= '''
    <cards>
      <card />
      <card />
    </cards>
  '''
    void setUp() {
	  XMLUnit.setIgnoreWhitespace(true)
    }
  
	void testWritesEmptyCardsList() {
	    def xmlDiff = new Diff(new CardsWriter().write(null), EMPTYLIST)
	    assert xmlDiff.similar()
	}
	
	void testWritesSingleCardForSingleCharm(){
	  String charmXml = new CardsWriter().write(createCharm("testId") )
	  Diff diff = new Diff(charmXml, SINGLECARD)
	  DifferenceListener listener = new LayerDifferenceListener() 
	  diff.overrideDifferenceListener(listener)
	  assert diff.similar()
	}
	
	void testMultipleCardsForMultipleObjects(){
	  ICharm[] charms  = [createCharm("testId"), createCharm("toastId")]
	  String charmXml = new CardsWriter().write(charms)
	  Diff diff = new Diff(charmXml, MULTICARD)
	  DifferenceListener listener = new LayerDifferenceListener() 
	  diff.overrideDifferenceListener(listener)
	  assert diff.similar()
	}
}