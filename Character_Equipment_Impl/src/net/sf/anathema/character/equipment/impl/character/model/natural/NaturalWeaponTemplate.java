package net.sf.anathema.character.equipment.impl.character.model.natural;

import net.sf.anathema.character.equipment.MagicalMaterial;
import net.sf.anathema.character.equipment.MaterialComposition;
import net.sf.anathema.character.equipment.template.IEquipmentTemplate;
import net.sf.anathema.character.generic.equipment.weapon.IEquipmentStats;
import net.sf.anathema.character.generic.rules.IExaltedRuleSet;

public class NaturalWeaponTemplate implements IEquipmentTemplate {

  private static final String NATURAL = "Natural"; //$NON-NLS-1$

  @Override
  public String getDescription() {
    return NATURAL;
  }

  @Override
  public IEquipmentStats[] getStats(IExaltedRuleSet ruleSet) {
    return INaturalWeaponConstants.SECOND_EDITION;
  }

  @Override
  public String getName() {
    return NATURAL;
  }

  @Override
  public MaterialComposition getComposition() {
    return MaterialComposition.None;
  }

  @Override
  public MagicalMaterial getMaterial() {
    return null;
  }
}