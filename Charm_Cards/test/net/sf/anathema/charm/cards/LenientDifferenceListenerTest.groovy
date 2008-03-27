package net.sf.anathema.charm.cards

import org.custommonkey.xmlunit.Diffclass LenientDifferenceListenerTest extends GroovyTestCase {
	void testAcceptsDifferentNumberOfChildred() {
	  String testXml = "<parent><child/><child/></parent>"
	  String controlXml = "<parent><child/></parent>"
	  Diff diff =  new Diff(controlXml, testXml)
	  diff.overrideDifferenceListener(new LenientDifferenceListener())
	  assert diff.similar()
	}
	
	void testAcceptsMissingChildrenInTestXml() {
	  String testXml = "<parent><child/><schuld/></parent>"
	  String controlXml = "<parent><child/></parent>"
	  Diff diff =  new Diff(controlXml, testXml)
	  diff.overrideDifferenceListener(new LenientDifferenceListener())
	  assert diff.similar()
	}
	
	void testRejectsMissingControlNode() {
	  String testXml = "<parent><schuld/></parent>"
	  String controlXml = "<parent><child/></parent>"
	  Diff diff =  new Diff(controlXml, testXml)
	  diff.overrideDifferenceListener(new LenientDifferenceListener())
	  assert !diff.similar()
	}
}