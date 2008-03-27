package net.sf.anathema.charm.cards

import org.custommonkey.xmlunit.DifferenceListenerimport org.custommonkey.xmlunit.Difference
import org.w3c.dom.Node
import org.custommonkey.xmlunit.DifferenceConstantsclass LenientDifferenceListener implements DifferenceListener {
	
  int differenceFound(Difference difference){
    if (difference.getId() == DifferenceConstants.CHILD_NODELIST_LENGTH_ID){
      return RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR
    }
    if (difference.getId() == DifferenceConstants.CHILD_NODE_NOT_FOUND_ID){
      return RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR
    }
    RETURN_ACCEPT_DIFFERENCE
  }
  
  void 	skippedComparison(Node control, Node test) {
    //nothing to do
  }
}