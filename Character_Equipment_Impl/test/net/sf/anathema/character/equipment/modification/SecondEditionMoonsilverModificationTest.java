package net.sf.anathema.character.equipment.modification;

import net.sf.anathema.character.equipment.MagicalMaterial;
import net.sf.anathema.character.equipment.impl.character.model.stats.modification.WeaponStatsType;
import org.junit.Test;

public class SecondEditionMoonsilverModificationTest extends AbstractEquipmentModificationTest {

  @Test
  public void accuracyIncreasedForMeleeBy2() throws Exception {
    assertAccuracyModification(3, 1, WeaponStatsType.Melee);
  }

  @Test
  public void accuracyIncreasedForRangedWeaponsBy1() throws Exception {
    assertAccuracyModification(2, 1, WeaponStatsType.Bow);
    assertAccuracyModification(2, 1, WeaponStatsType.Thrown);
    assertAccuracyModification(2, 1, WeaponStatsType.Thrown_BowBonuses);
    assertAccuracyModification(2, 1, WeaponStatsType.Flame);
  }

  @Test
  public void defenseIncreasedForMeleeBy2() throws Exception {
    assertDefenseModification(3, 1, WeaponStatsType.Melee);
  }

  @Test
  public void unmodifiedDamage() throws Exception {
    assertDamageUnmodified();
  }

  @Test
  public void unmodifiedSpeed() {
    assertSpeedUnmodified();
  }

  @Test
  public void rateUnmodified() {
    assertRateUnmodified();
  }

  @Test
  public void soakUnmodified() {
    assertLethalSoakUnmodified();
  }

  @Test
  public void hardnessUnmodified() {
    assertHardnessUnmodified();
  }

  @Test
  public void mobilityZero() {
    assertMobilityPenaltyModification(0, 5);
  }

  @Test
  public void fatigueUnmodified() {
    assertFatigueUnmodified();
  }

  @Test
  public void rangeIncreasedForBowAndThrown() {
    assertRangeModification(101, 1, WeaponStatsType.Bow);
    assertRangeModification(21, 1, WeaponStatsType.Thrown);
    assertRangeModification(101, 1, WeaponStatsType.Thrown_BowBonuses);
    assertRangeModification(1, 1, WeaponStatsType.Flame);
  }

  @Override
  protected MagicalMaterial getMagicMaterial() {
    return MagicalMaterial.Moonsilver;
  }
}