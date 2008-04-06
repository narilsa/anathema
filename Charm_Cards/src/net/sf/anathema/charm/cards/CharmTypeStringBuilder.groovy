package net.sf.anathema.charm.cards

import net.sf.anathema.character.generic.impl.magic.charm.type.*
import net.sf.anathema.character.generic.magic.*
import net.sf.anathema.character.generic.magic.charms.type.*

class CharmTypeStringBuilder {

	String build(ICharm charm){
		def model = charm.charmTypeModel;
		def type = model.charmType
		StringBuilder builder = new StringBuilder(type.toString())
		if (model.hasSpecialsModel()){
			if (type == CharmType.Simple){
				SimpleSpecialsModel specialsModel = model.specialsModel
				builder.append(" (")
				def speed = specialsModel.speed
				def dv = specialsModel.defenseModifier
				if (speed != 6){
					builder.append("Speed ")
					builder.append(specialsModel.speed)
				}
				if (dv != -1){
					if (speed != 6){
						builder.append(", ")
					}
					builder.append("DV ")
					builder.append(dv)
				}
				builder.append(")")
			}
			if (type == CharmType.Reflexive){
				ReflexiveSpecialsModel specialsModel = model.specialsModel
				builder.append(" (Step ")
				builder.append(specialsModel.primaryStep)
				def secondaryStep = specialsModel.secondaryStep
				if (secondaryStep){
					builder.append(" or ")
					builder.append(secondaryStep)
				}
				builder.append(")")
			}
		}
		builder.toString()
	}
}