package net.sf.anathema.charm.cards

import groovy.xml.*
import net.sf.anathema.character.generic.magic.*

class CardsWriter {

	def provider
	Writer writer
	def builder = new CharmTypeStringBuilder()

	def write(ICharm[] charms){
		def xml = new MarkupBuilder(writer)
		xml.cards() {
			charms.each {charm ->
				card() {
					name(provider.getString(charm.id, []))
					exalt(charm.characterType)
					ability(charm.primaryTraitType)
					stats() {
						cost("Cost")
						type(builder.build(charm))
						duration(charm.duration.getText(provider))
					}
					keywords() {
						charm.attributes.each {
							attribute -> keyword(attribute.id)
						}
					}
					text() {
						para("Charm Text goes here.")
					}
					source() {
						title(charm.source.id)
						page(provider.getString(charm.source.id + "." + charm.id + ".Page", []))
					}
					requirements() {
						trait(charm.prerequisites[0].currentValue)
						essence(charm.essence.currentValue)
					}
				}
			}
		}
	}
}