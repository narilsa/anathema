package net.sf.anathema.charm

import org.custommonkey.xmlunit.*
import org.w3c.dom.*






class LenientDifferenceListener implements DifferenceListener {

	int differenceFound(Difference difference){
		if (difference.id == DifferenceConstants.CHILD_NODELIST_LENGTH_ID){
			return RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR
		}
		if (difference.id == DifferenceConstants.CHILD_NODE_NOT_FOUND_ID){
			return RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR
		}
		RETURN_ACCEPT_DIFFERENCE
	}

	void skippedComparison(Node control, Node test){
		//nothing to do
	}
}