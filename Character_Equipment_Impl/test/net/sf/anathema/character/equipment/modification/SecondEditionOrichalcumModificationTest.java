package net.sf.anathema.character.equipment.modification;

import net.sf.anathema.character.equipment.MagicalMaterial;
import net.sf.anathema.character.equipment.impl.character.model.stats.modification.WeaponStatsType;
import net.sf.anathema.character.generic.health.HealthType;
import org.junit.Test;

public class SecondEditionOrichalcumModificationTest extends AbstractEquipmentModificationTest {

  @Test
  public void addsTwoToMeleeAccuracy() throws Exception {
    assertAccuracyModification(3, 1, WeaponStatsType.Melee);
  }

  @Test
  public void addsOneToRangedAccuracy() throws Exception {
    assertAccuracyModification(2, 1, WeaponStatsType.Bow);
    assertAccuracyModification(2, 1, WeaponStatsType.Thrown);
    assertAccuracyModification(2, 1, WeaponStatsType.Thrown_BowBonuses);
    assertAccuracyModification(2, 1, WeaponStatsType.Flame);
  }

  @Test
  public void addsOneToDefense() throws Exception {
    assertDefenseModification(2, 1, WeaponStatsType.Melee);
  }

  @Test
  public void rateIncreasedForMeleeBy1() throws Exception {
    assertRateModification(2, 1, WeaponStatsType.Melee);
    assertRateModification(1, 1, WeaponStatsType.Bow);
    assertRateModification(1, 1, WeaponStatsType.Thrown);
    assertRateModification(1, 1, WeaponStatsType.Thrown_BowBonuses);
    assertRateModification(1, 1, WeaponStatsType.Flame);
  }

  @Test
  public void speedUnmodified() {
    assertSpeedUnmodified();
  }

  @Test
  public void damageIncreasedForRangedWeaponsBy1() throws Exception {
    assertDamageModification(1, 1, WeaponStatsType.Melee);
    assertDamageModification(2, 1, WeaponStatsType.Bow);
    assertDamageModification(2, 1, WeaponStatsType.Thrown);
    assertDamageModification(2, 1, WeaponStatsType.Thrown_BowBonuses);
    assertDamageModification(2, 1, WeaponStatsType.Flame);
  }

  @Test
  public void rangeIncreasedForBowAndThrown() throws Exception {
    assertRangeModification(51, 1, WeaponStatsType.Bow);
    assertRangeModification(11, 1, WeaponStatsType.Thrown);
    assertRangeModification(51, 1, WeaponStatsType.Thrown_BowBonuses);
    assertRangeModification(1, 1, WeaponStatsType.Flame);
  }

  @Test
  public void soakIncreasedBy2() throws Exception {
    assertSoakModification(3, 1, HealthType.Lethal);
    assertSoakModification(3, 1, HealthType.Bashing);
    assertSoakModification(3, 1, HealthType.Aggravated);
  }

  @Test
  public void hardnessIncreasedBy1() throws Exception {
    assertHardnessModification(2, 1);
  }

  @Test
  public void mobilityUnmodified() {
    assertMobilityPenaltyUnmodified();
  }

  @Test
  public void fatigueUnmodified() {
    assertFatigueUnmodified();
  }

  @Override
  protected MagicalMaterial getMagicMaterial() {
    return MagicalMaterial.Orichalcum;
  }
}