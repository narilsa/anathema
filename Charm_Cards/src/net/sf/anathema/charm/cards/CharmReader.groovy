package net.sf.anathema.charm.cards

import net.sf.anathema.character.generic.impl.magic.persistence.*
import org.dom4j.*
import org.dom4j.io.*

class CharmReader {

	List read(File file){
		if (file == null) return [];
		Document document = new SAXReader().read(file);
		new CharmSetBuilder().buildCharms(document);
	}
}