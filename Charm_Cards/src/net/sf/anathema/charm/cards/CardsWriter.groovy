package net.sf.anathema.charm.cards

import groovy.xml.MarkupBuilderclass CardsWriter {
	
  def write(def charms){
    def writer = new StringWriter()
    def xml = new MarkupBuilder(writer)
    xml.cards(){
      charms.each{
        card()
      }
    }
    writer.toString()    
  }
}