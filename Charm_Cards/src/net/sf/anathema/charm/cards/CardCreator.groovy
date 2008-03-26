package net.sf.anathema.charm.cards

import net.sf.anathema.character.generic.magic.ICharm
class CardCreator {
	
  static void main(args) {
		ICharm[] charms = new CharmReader().read(new File(args[0]))
		Writer writer = args.size() > 1 ? new FileWriter(new File(args[1])) : new StringWriter() 
		new CardsWriter(writer).write(charms)
	}
}