package net.sf.anathema.charm.cards

import net.sf.anathema.character.generic.impl.magic.charm.type.CharmTypeModel
import net.sf.anathema.character.generic.impl.magic.charm.type.ReflexiveSpecialsModel
import net.sf.anathema.character.generic.impl.magic.charm.type.SimpleSpecialsModel
import net.sf.anathema.character.generic.magic.ICharm
import net.sf.anathema.character.generic.magic.charms.type.CharmType
import net.sf.anathema.character.generic.magic.charms.type.ICharmTypeModel
import net.sf.anathema.character.generic.magic.charms.type.TurnType
import net.sf.anathema.character.generic.traits.types.AbilityType
import net.sf.anathema.character.generic.type.CharacterType

class DummyCharmFactory {
    static ICharm createCharm(Map parameters) {
        [getId: {parameters.id ?: "id"},
                getCharacterType: {parameters.character ?: CharacterType.LUNAR},
                getPrimaryTraitType: {parameters.ability ?: AbilityType.Archery},
                getCharmTypeModel: {createTypeModel(parameters)}] as ICharm
    }

    static ICharmTypeModel createTypeModel(parameters) {
        CharmTypeModel model = new CharmTypeModel()
        model.setCharmType(parameters.charmType ?: CharmType.ExtraAction)
        if (parameters.charmType == CharmType.Simple) {
            model.setSpecialModel(new SimpleSpecialsModel(parameters.speed, TurnType.Tick, parameters.dv))
        }
        if (parameters.charmType == CharmType.Reflexive) {
            model.setSpecialModel(new ReflexiveSpecialsModel(parameters.step1, parameters.step2))
        }
        model
    }
}