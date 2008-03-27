package net.sf.anathema.charm.cards

import groovy.xml.MarkupBuilderimport net.sf.anathema.character.generic.magic.ICharm
import net.sf.anathema.charm.cards.CardsWriterclass CardsWriter {
  
  def writer
  
  CardsWriter(){
    this(new StringWriter())
  }
  
  CardsWriter(Writer writer){
    this.writer = writer
  }
  
  def write(ICharm[] charms){
    def xml = new MarkupBuilder(writer)
    xml.cards(){
      charms.each{charm->
        card(){
          name(charm.id)
          exalt(charm.characterType)
          ability(charm.primaryTraitType)
        }
      }
    }
    writer.toString()    
  }
}