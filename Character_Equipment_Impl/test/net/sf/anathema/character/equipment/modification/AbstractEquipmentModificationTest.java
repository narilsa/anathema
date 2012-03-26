package net.sf.anathema.character.equipment.modification;

import net.sf.anathema.character.equipment.MagicalMaterial;
import net.sf.anathema.character.equipment.impl.character.model.stats.modification.AccuracyModification;
import net.sf.anathema.character.equipment.impl.character.model.stats.modification.DamageModification;
import net.sf.anathema.character.equipment.impl.character.model.stats.modification.DefenseModification;
import net.sf.anathema.character.equipment.impl.character.model.stats.modification.FatigueModification;
import net.sf.anathema.character.equipment.impl.character.model.stats.modification.HardnessModification;
import net.sf.anathema.character.equipment.impl.character.model.stats.modification.MobilityPenaltyModification;
import net.sf.anathema.character.equipment.impl.character.model.stats.modification.RangeModification;
import net.sf.anathema.character.equipment.impl.character.model.stats.modification.RateModification;
import net.sf.anathema.character.equipment.impl.character.model.stats.modification.SoakModification;
import net.sf.anathema.character.equipment.impl.character.model.stats.modification.SpeedModification;
import net.sf.anathema.character.equipment.impl.character.model.stats.modification.WeaponStatsType;
import net.sf.anathema.character.generic.health.HealthType;
import org.junit.Assert;

public abstract class AbstractEquipmentModificationTest {

  protected abstract MagicalMaterial getMagicMaterial();

  protected final void assertAccuracyModification(int expected, int original, WeaponStatsType type) {
    AccuracyModification modification = new AccuracyModification(getMagicMaterial());
    Assert.assertEquals(expected, modification.getModifiedValue(original, type));
  }

  protected final void assertRangeModification(int expected, int original, WeaponStatsType type) {
    RangeModification modification = new RangeModification(getMagicMaterial());
    Assert.assertEquals(expected, modification.getModifiedValue(original, type));
  }

  protected final void assertDefenseModification(int expected, int original, WeaponStatsType type) {
    DefenseModification modification = new DefenseModification(getMagicMaterial());
    Assert.assertEquals(expected, modification.getModifiedValue(original, type));
  }

  protected final void assertSpeedModification(int expected, int original, WeaponStatsType type) {
    SpeedModification modification = new SpeedModification(getMagicMaterial());
    Assert.assertEquals(expected, modification.getModifiedValue(original, type));
  }

  protected final void assertDamageModification(int expected, int original, WeaponStatsType type) {
    DamageModification modification = new DamageModification(getMagicMaterial());
    Assert.assertEquals(expected, modification.getModifiedValue(original, type));
  }

  protected final void assertRateModification(int expected, int original, WeaponStatsType type) {
    RateModification modification = new RateModification(getMagicMaterial());
    Assert.assertEquals(expected, modification.getModifiedValue(original, type));
  }

  protected final void assertSpeedUnmodified() {
    assertSpeedModification(1, 1, WeaponStatsType.Bow);
    assertSpeedModification(1, 1, WeaponStatsType.Thrown);
    assertSpeedModification(1, 1, WeaponStatsType.Thrown_BowBonuses);
    assertSpeedModification(1, 1, WeaponStatsType.Melee);
    assertSpeedModification(1, 1, WeaponStatsType.Flame);
  }

  protected final void assertSoakModification(int expected, int original, HealthType type) {
    SoakModification modification = new SoakModification(getMagicMaterial(), type);
    Assert.assertEquals(expected, modification.getModifiedValue(original));
  }

  protected final void assertLethalSoakUnmodified() {
    assertSoakModification(1, 1, HealthType.Lethal);
  }

  protected final void assertHardnessModification(int expected, int original) {
    HardnessModification modification = new HardnessModification(getMagicMaterial());
    Assert.assertEquals(expected, modification.getModifiedValue(original));
  }

  protected final void assertHardnessUnmodified() {
    assertHardnessModification(1, 1);
  }

  protected final void assertMobilityPenaltyUnmodified() {
    assertMobilityPenaltyModification(1, 1);
  }

  protected final void assertFatigueUnmodified() {
    assertFatigueModification(1, 1);
  }

  protected final void assertFatigueModification(int expected, int original) {
    FatigueModification modification = new FatigueModification(getMagicMaterial());
    Assert.assertEquals(expected, modification.getModifiedValue(original));
  }

  protected final void assertMobilityPenaltyModification(int expected, int original) {
    MobilityPenaltyModification modification = new MobilityPenaltyModification(getMagicMaterial());
    Assert.assertEquals(expected, modification.getModifiedValue(original));
  }

  protected final void assertRangeUnmodified() {
    assertRangeModification(1, 1, WeaponStatsType.Bow);
    assertRangeModification(1, 1, WeaponStatsType.Thrown);
    assertRangeModification(1, 1, WeaponStatsType.Thrown_BowBonuses);
    assertRangeModification(1, 1, WeaponStatsType.Flame);
  }

  protected final void assertRateUnmodified() {
    assertRateModification(1, 1, WeaponStatsType.Bow);
    assertRateModification(1, 1, WeaponStatsType.Thrown);
    assertRateModification(1, 1, WeaponStatsType.Thrown_BowBonuses);
    assertRateModification(1, 1, WeaponStatsType.Melee);
    assertRateModification(1, 1, WeaponStatsType.Flame);
  }

  protected final void assertDamageUnmodified() {
    assertDamageModification(1, 1, WeaponStatsType.Bow);
    assertDamageModification(1, 1, WeaponStatsType.Thrown);
    assertDamageModification(1, 1, WeaponStatsType.Thrown_BowBonuses);
    assertDamageModification(1, 1, WeaponStatsType.Melee);
    assertDamageModification(1, 1, WeaponStatsType.Flame);
  }

  protected final void assertAccuracyUnmodified() {
    assertAccuracyModification(1, 1, WeaponStatsType.Bow);
    assertAccuracyModification(1, 1, WeaponStatsType.Thrown);
    assertAccuracyModification(1, 1, WeaponStatsType.Thrown_BowBonuses);
    assertAccuracyModification(1, 1, WeaponStatsType.Melee);
    assertAccuracyModification(1, 1, WeaponStatsType.Flame);
  }

  protected final void assertDefenseUnmodified() {
    assertDefenseModification(1, 1, WeaponStatsType.Bow);
    assertDefenseModification(1, 1, WeaponStatsType.Thrown);
    assertDefenseModification(1, 1, WeaponStatsType.Thrown_BowBonuses);
    assertDefenseModification(1, 1, WeaponStatsType.Melee);
    assertDefenseModification(1, 1, WeaponStatsType.Flame);
  }
}