package net.sf.anathema.charm

import org.custommonkey.xmlunit.*
import org.w3c.dom.*






class LayerDifferenceListener implements DifferenceListener {

	int differenceFound(Difference difference){
		Node node = difference.testNodeDetail.node
		if (node.parentNode.parentNode != null){
			return RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR
		}
		RETURN_ACCEPT_DIFFERENCE
	}

	void skippedComparison(Node control, Node test){
		//nothing to do
	}
}