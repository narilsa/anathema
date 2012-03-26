package net.sf.anathema.character.equipment.impl.character.model.stats;

import net.disy.commons.core.util.ObjectUtilities;
import net.sf.anathema.character.equipment.MagicalMaterial;
import net.sf.anathema.character.equipment.impl.character.model.stats.modification.FatigueModification;
import net.sf.anathema.character.equipment.impl.character.model.stats.modification.HardnessModification;
import net.sf.anathema.character.equipment.impl.character.model.stats.modification.IArmourStatsModification;
import net.sf.anathema.character.equipment.impl.character.model.stats.modification.MobilityPenaltyModification;
import net.sf.anathema.character.equipment.impl.character.model.stats.modification.SoakModification;
import net.sf.anathema.character.generic.equipment.weapon.IArmourStats;
import net.sf.anathema.character.generic.health.HealthType;
import net.sf.anathema.character.generic.util.IProxy;
import net.sf.anathema.lib.util.IIdentificate;

public class ProxyArmourStats extends AbstractStats implements IArmourStats, IProxy<IArmourStats> {

  private final IArmourStats delegate;
  private final MagicalMaterial material;

  public ProxyArmourStats(IArmourStats stats, MagicalMaterial material) {
    this.delegate = stats;
    this.material = material;
  }

  @Override
  public IArmourStats getUnderlying() {
    return this.delegate;
  }

  @Override
  public Integer getFatigue() {
    Integer fatigue = delegate.getFatigue();
    return getModifiedValue(new FatigueModification(material), fatigue);
  }

  @Override
  public Integer getHardness(HealthType type) {
    Integer hardness = delegate.getHardness(type);
    return getModifiedValue(new HardnessModification(material), hardness);
  }

  private Integer getModifiedValue(IArmourStatsModification modification, Integer original) {
    if (original == null) {
      return null;
    }
    return !useAttunementModifiers() ? original : modification.getModifiedValue(original);
  }

  @Override
  public Integer getMobilityPenalty() {
    Integer mobilityPenalty = delegate.getMobilityPenalty();
    return getModifiedValue(new MobilityPenaltyModification(material), mobilityPenalty);
  }

  @Override
  public Integer getSoak(HealthType type) {
    Integer soak = delegate.getSoak(type);
    return getModifiedValue(new SoakModification(material, type), soak);
  }

  @Override
  public IIdentificate getName() {
    return delegate.getName();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof ProxyArmourStats)) {
      return false;
    }
    ProxyArmourStats other = (ProxyArmourStats) obj;
    return ObjectUtilities.equals(delegate, other.delegate) && ObjectUtilities.equals(material, other.material);
  }

  @Override
  public int hashCode() {
    return delegate.hashCode();
  }

  @Override
  public String getId() {
    return getName().getId();
  }

  @Override
  public Object[] getApplicableMaterials() {
    return delegate.getApplicableMaterials();
  }

  @Override
  public boolean representsItemForUseInCombat() {
    return delegate.representsItemForUseInCombat();
  }
}
