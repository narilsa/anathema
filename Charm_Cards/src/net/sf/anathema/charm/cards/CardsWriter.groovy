package net.sf.anathema.charm.cards

import groovy.xml.MarkupBuilderimport net.sf.anathema.character.generic.magic.ICharm
class CardsWriter {
	
  def write(ICharm[] charms){
    def writer = new StringWriter()
    def xml = new MarkupBuilder(writer)
    xml.cards(){
      charms.each{charm->
        card(){
          name(charm.getId())
        }
      }
    }
    writer.toString()    
  }
}