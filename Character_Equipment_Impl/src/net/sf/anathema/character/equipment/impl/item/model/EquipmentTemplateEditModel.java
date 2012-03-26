package net.sf.anathema.character.equipment.impl.item.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.disy.commons.core.util.Ensure;
import net.disy.commons.core.util.ObjectUtilities;
import net.sf.anathema.character.equipment.MagicalMaterial;
import net.sf.anathema.character.equipment.MaterialComposition;
import net.sf.anathema.character.equipment.impl.character.model.EquipmentTemplate;
import net.sf.anathema.character.equipment.item.model.IEquipmentDatabase;
import net.sf.anathema.character.equipment.item.model.IEquipmentTemplateEditModel;
import net.sf.anathema.character.equipment.template.IEquipmentTemplate;
import net.sf.anathema.character.generic.equipment.weapon.IEquipmentStats;
import net.sf.anathema.character.generic.impl.rules.ExaltedRuleSet;
import net.sf.anathema.character.generic.rules.IExaltedRuleSet;
import net.sf.anathema.framework.itemdata.model.IItemDescription;
import net.sf.anathema.framework.itemdata.model.ItemDescription;
import net.sf.anathema.framework.styledtext.model.ITextPart;
import net.sf.anathema.lib.collection.MultiEntryMap;
import net.sf.anathema.lib.control.change.ChangeControl;
import net.sf.anathema.lib.control.change.IChangeListener;

public class EquipmentTemplateEditModel implements IEquipmentTemplateEditModel {

  private final IItemDescription description = new ItemDescription();
  private final IEquipmentDatabase database;
  private IEquipmentTemplate editedTemplate;
  private final MultiEntryMap<IExaltedRuleSet, IEquipmentStats> statsByRuleSet = new MultiEntryMap<IExaltedRuleSet, IEquipmentStats>();
  private final ChangeControl statsChangeControl = new ChangeControl();
  private final ChangeControl magicalMaterialControl = new ChangeControl();
  private final ChangeControl compositionControl = new ChangeControl();
  private String editTemplateId;
  private MaterialComposition composition;
  private MagicalMaterial material;

  public EquipmentTemplateEditModel(IEquipmentDatabase database) {
    this.database = database;
  }

  @Override
  public IItemDescription getDescription() {
    return description;
  }

  @Override
  public void setEditTemplate(String templateId) {
    Ensure.ensureArgumentNotNull(templateId);
    this.editTemplateId = templateId;
    editedTemplate = database.loadTemplate(templateId);
    getDescription().getName().setText(editedTemplate.getName());
    getDescription().getContent().setText(editedTemplate.getDescription());
    setMaterialComposition(editedTemplate.getComposition());
    setMagicalMaterial(editedTemplate.getMaterial());
    statsByRuleSet.clear();
    for (ExaltedRuleSet ruleSet : ExaltedRuleSet.values()) {
      statsByRuleSet.add(ruleSet, editedTemplate.getStats(ruleSet));
    }
    fireStatsChangedEvent();
  }

  @Override
  public String getEditTemplateId() {
    return editTemplateId;
  }

  private void fireStatsChangedEvent() {
    statsChangeControl.fireChangedEvent();
  }

  @Override
  public void setNewTemplate() {
    editTemplateId = null;
    editedTemplate = null;
    getDescription().getName().setText(null);
    getDescription().getContent().setText(new ITextPart[0]);
    setMaterialComposition(MaterialComposition.None);
    statsByRuleSet.clear();
    fireStatsChangedEvent();
  }

  @Override
  public boolean isDirty() {
    List<IEquipmentStats> currentStats = getAllCurrentStats();
    List<IEquipmentStats> previousStats = getAllPreviousStats();
    if (currentStats.size() != previousStats.size() || !currentStats.containsAll(previousStats)) {
      return true;
    }
    if (editedTemplate == null) {
      return !getDescription().getName().isEmpty() || !getDescription().getContent().isEmpty();
    }
    return !ObjectUtilities.equals(editedTemplate.getName(), getDescription().getName().getText())
        || !ObjectUtilities.equals(editedTemplate.getDescription(), getDescription().getContent().getText())
        || !(editedTemplate.getComposition() == getMaterialComposition())
        || !(editedTemplate.getMaterial() == getMagicalMaterial());
  }

  private List<IEquipmentStats> getAllPreviousStats() {
    List<IEquipmentStats> allStats = new ArrayList<IEquipmentStats>();
    if (editedTemplate != null) {
      for (ExaltedRuleSet ruleSet : ExaltedRuleSet.values()) {
        allStats.addAll(Arrays.asList(editedTemplate.getStats(ruleSet)));
      }
    }
    return allStats;
  }

  private List<IEquipmentStats> getAllCurrentStats() {
    List<IEquipmentStats> allStats = new ArrayList<IEquipmentStats>();
    for (IExaltedRuleSet ruleSet : statsByRuleSet.keySet()) {
      allStats.addAll(statsByRuleSet.get(ruleSet));
    }
    return allStats;
  }

  @Override
  public void addStatistics(IExaltedRuleSet ruleSet, IEquipmentStats stats) {
    statsByRuleSet.add(ruleSet, stats);
    fireStatsChangedEvent();
  }

  @Override
  public void removeStatistics(IExaltedRuleSet ruleSet, IEquipmentStats... stats) {
    for (IEquipmentStats stat : stats) {
      statsByRuleSet.removeValue(ruleSet, stat);
    }
    fireStatsChangedEvent();
  }

  @Override
  public IEquipmentStats[] getStats(IExaltedRuleSet ruleSet) {
    List<IEquipmentStats> allStats = statsByRuleSet.get(ruleSet);
    return allStats.toArray(new IEquipmentStats[allStats.size()]);
  }

  @Override
  public void addStatsChangeListener(IChangeListener changeListener) {
    statsChangeControl.addChangeListener(changeListener);
  }

  @Override
  public IEquipmentTemplate createTemplate() {
    String name = getDescription().getName().getText();
    String descriptionText = getDescription().getContent().getText();
    EquipmentTemplate template = new EquipmentTemplate(
        name,
        descriptionText,
        composition,
        material,
        database.getCollectionFactory());
    for (IExaltedRuleSet ruleSet : statsByRuleSet.keySet()) {
      for (IEquipmentStats stats : statsByRuleSet.get(ruleSet)) {
        template.addStats(ruleSet, stats);
      }
    }
    return template;
  }

  @Override
  public void addMagicalMaterialChangeListener(IChangeListener listener) {
    magicalMaterialControl.addChangeListener(listener);
  }

  @Override
  public void addCompositionChangeListener(IChangeListener listener) {
    compositionControl.addChangeListener(listener);
  }

  @Override
  public MagicalMaterial getMagicalMaterial() {
    return material;
  }

  @Override
  public void setMagicalMaterial(MagicalMaterial material) {
    if (material == this.material) {
      return;
    }
    if (!composition.requiresMaterial() && material != null) {
      return;
    }
    this.material = material;
    magicalMaterialControl.fireChangedEvent();
  }

  @Override
  public void setMaterialComposition(MaterialComposition composition) {
    if (composition == this.composition) {
      return;
    }
    this.composition = composition;
    if (composition.requiresMaterial()) {
      setMagicalMaterial(MagicalMaterial.Orichalcum);
    }
    else {
      setMagicalMaterial(null);
    }
    compositionControl.fireChangedEvent();
  }

  @Override
  public MaterialComposition getMaterialComposition() {
    return composition;
  }

  @Override
  public void replaceStatistics(IExaltedRuleSet ruleset, IEquipmentStats oldStats, IEquipmentStats newStats) {
    statsByRuleSet.replace(ruleset, oldStats, newStats);
    fireStatsChangedEvent();
  }
}