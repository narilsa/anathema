package net.sf.anathema.charm.cards
import net.sf.anathema.character.generic.traits.types.AbilityType
import net.sf.anathema.character.generic.type.CharacterType
import net.sf.anathema.character.generic.magic.ICharm

class DummyCharmFactory {
    static ICharm createCharm(String id){
	  [getId:{id}, getCharacterType:{CharacterType.LUNAR}, getPrimaryTraitType:{AbilityType.Archery}] as ICharm
	}
	
    static ICharm createCharm(AbilityType type){
	  [getId:{"id"}, getCharacterType:{CharacterType.LUNAR}, getPrimaryTraitType:{type}] as ICharm
	}
	
    static ICharm createCharm(CharacterType type){
	  [getId:{"id"}, getCharacterType:{type}, getPrimaryTraitType:{AbilityType.Archery}] as ICharm
	}
}