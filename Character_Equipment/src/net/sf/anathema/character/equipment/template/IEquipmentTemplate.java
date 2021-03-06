package net.sf.anathema.character.equipment.template;

import net.sf.anathema.character.equipment.MagicalMaterial;
import net.sf.anathema.character.equipment.MaterialComposition;
import net.sf.anathema.character.generic.equipment.weapon.IEquipmentStats;
import net.sf.anathema.character.generic.rules.IExaltedRuleSet;

public interface IEquipmentTemplate {

  String getName();

  String getDescription();

  IEquipmentStats[] getStats(IExaltedRuleSet ruleSet);

  MaterialComposition getComposition();

  MagicalMaterial getMaterial();
}