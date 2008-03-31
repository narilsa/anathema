package net.sf.anathema.charm.cards

import net.sf.anathema.character.generic.impl.magic.charm.type.*
import net.sf.anathema.character.generic.magic.*
import net.sf.anathema.character.generic.magic.charms.type.*
import net.sf.anathema.character.generic.rules.*
import net.sf.anathema.character.generic.traits.types.*
import net.sf.anathema.character.generic.type.*

class DummyCharmFactory {static ICharm createCharm(Map parameters){
	[getId: {parameters.id ?: "id"},
			getCharacterType: {parameters.character ?: CharacterType.LUNAR},
			getPrimaryTraitType: {parameters.ability ?: AbilityType.Archery},
			getCharmTypeModel: {createTypeModel(parameters)},
			getSource: {createSource(parameters)}] as ICharm
}

	static ICharmTypeModel createTypeModel(parameters){
		CharmTypeModel model = new CharmTypeModel()
		model.charmType = parameters.charmType ?: CharmType.ExtraAction
		if (parameters.charmType == CharmType.Simple){
			model.specialModel = new SimpleSpecialsModel(parameters.speed, TurnType.Tick, parameters.dv)
		}
		if (parameters.charmType == CharmType.Reflexive){
			model.specialModel = new ReflexiveSpecialsModel(parameters.step1, parameters.step2)
		}
		model
	}

	static IExaltedSourceBook createSource(parameters){
		[getId: {parameters.book ?: "book"}] as IExaltedSourceBook
	}
}