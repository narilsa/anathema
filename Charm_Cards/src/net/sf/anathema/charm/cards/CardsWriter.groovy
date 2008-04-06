package net.sf.anathema.charm.cards

import groovy.xml.*
import net.sf.anathema.character.generic.magic.*

class CardsWriter {

	Writer writer
	Map pages = [:]
	def builder = new CharmTypeStringBuilder()

	def write(ICharm[] charms){
		def xml = new MarkupBuilder(writer)
		xml.cards() {
			charms.each {charm ->
				card() {
					name(charm.id)
					exalt(charm.characterType)
					ability(charm.primaryTraitType)
					stats() {
						type(builder.build(charm))
					}
					keywords() {
						charm.attributes.each {
							attribute -> keyword(attribute.id)
						}
					}
					source() {
						title(charm.source.id)
						page(pages[createPageKey(charm.id, charm.source.id)])
					}
					requirements() {
						trait(charm.prerequisites[0].currentValue)
						essence(charm.essence.currentValue)
					}
				}
			}
		}
	}

	String createPageKey(String charmId, final String bookId){
		return bookId + "." + charmId + ".Page";
	}
}