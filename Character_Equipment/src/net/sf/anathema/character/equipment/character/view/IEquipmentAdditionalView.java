package net.sf.anathema.character.equipment.character.view;

import javax.swing.Action;

import net.sf.anathema.lib.gui.IView;
import net.sf.anathema.lib.gui.selection.IListObjectSelectionView;

public interface IEquipmentAdditionalView extends IView {

  public IListObjectSelectionView<String> getEquipmentTemplatePickList();

  public IEquipmentObjectView addEquipmentObjectView();

  public void removeEquipmentObjectView(IEquipmentObjectView objectView);

  public void setSelectButtonAction(Action action);

  public void setRefreshButtonAction(Action action);

  public IMagicalMaterialView getMagicMaterialView();

  public void revalidateEquipmentViews();
}