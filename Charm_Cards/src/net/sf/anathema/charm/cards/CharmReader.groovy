package net.sf.anathema.charm.cards

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import net.sf.anathema.character.generic.impl.magic.persistence.*;

class CharmReader  {
  List read(File file){
    if (file == null) return [];
    Document document = new SAXReader().read(file);
    new CharmSetBuilder().buildCharms(document);
  }
}