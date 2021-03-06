package net.sf.anathema.character.equipment.creation.presenter.stats;

import net.disy.commons.swing.ui.ObjectUiListCellRenderer;
import net.sf.anathema.character.equipment.creation.model.stats.IWeaponDamageModel;
import net.sf.anathema.character.equipment.creation.presenter.stats.properties.WeaponDamageProperties;
import net.sf.anathema.character.equipment.creation.view.IWeaponDamageView;
import net.sf.anathema.character.generic.health.HealthType;
import net.sf.anathema.lib.control.change.IChangeListener;
import net.sf.anathema.lib.control.objectvalue.IObjectValueChangedListener;
import net.sf.anathema.lib.gui.Presenter;
import net.sf.anathema.lib.gui.widgets.IntegerSpinner;
import net.sf.anathema.lib.resources.IResources;
import net.sf.anathema.lib.workflow.intvalue.IntValuePresentation;

public class WeaponDamagePresenter implements Presenter {

  private final IWeaponDamageModel model;
  private final IWeaponDamageView view;
  private final WeaponDamageProperties properties;

  public WeaponDamagePresenter(IResources resources, IWeaponDamageModel model, IWeaponDamageView view) {
    this.model = model;
    this.view = view;
    this.properties = new WeaponDamageProperties(resources);
  }

  public void initPresentation() {
    view.setDamageLabelText(properties.getDamageLabel());
    IntegerSpinner damageSpinner = view.getDamageIntegerSpinner();
    new IntValuePresentation().initView(damageSpinner, model.getDamageModel());
    damageSpinner.setMinimum(0);
    view.setObjects(HealthType.values());
    model.addHealthTypeChangeListener(new IChangeListener() {
      public void changeOccurred() {
        updateHealthTypeInView();
      }
    });
    view.addObjectSelectionChangedListener(new IObjectValueChangedListener<HealthType>() {
      public void valueChanged(HealthType newValue) {
        model.setHealthType(newValue);
      }
    });
    view.setHealthTypeRenderer(new ObjectUiListCellRenderer(properties.getHealthTypeUi()));
    updateHealthTypeInView();
    view.setMinDamageLabelText(properties.getMinDamageLabel());
    IntegerSpinner minDamageSpinner = view.getMinDamageIntegerSpinner();
    new IntValuePresentation().initView(minDamageSpinner, model.getMinDamageModel());
    minDamageSpinner.setMinimum(0);
  }

  private void updateHealthTypeInView() {
    view.setSelectedObject(model.getHealthType());
  }
}
