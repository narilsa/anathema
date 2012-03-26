package net.sf.anathema.character.equipment.view;

import junit.framework.TestCase;
import net.disy.commons.core.model.BooleanModel;
import net.sf.anathema.character.equipment.character.EquipmentObjectPresenter;
import net.sf.anathema.character.equipment.character.IEquipmentCharacterDataProvider;
import net.sf.anathema.character.equipment.character.IEquipmentCharacterOptionProvider;
import net.sf.anathema.character.equipment.character.IEquipmentStringBuilder;
import net.sf.anathema.character.equipment.character.model.IEquipmentItem;
import net.sf.anathema.character.equipment.character.view.IEquipmentObjectView;
import net.sf.anathema.character.equipment.dummy.DemoMeleeWeapon;
import net.sf.anathema.character.equipment.dummy.DummyEquipmentObject;
import net.sf.anathema.character.generic.equipment.weapon.IEquipmentStats;
import net.sf.anathema.character.generic.health.HealthType;
import net.sf.anathema.character.generic.traits.INamedGenericTrait;
import net.sf.anathema.character.generic.traits.ITraitType;
import net.sf.anathema.framework.resources.AnathemaResources;
import net.sf.anathema.lib.util.Identificate;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EquipmentItemPresenterTest extends TestCase {

  private IEquipmentStringBuilder equipmentStringBuilder = new IEquipmentStringBuilder() {
    @Override
    public String createString(IEquipmentItem item, IEquipmentStats equipment) {
      if (equipment.getName().getId().equals("Sword")) { //$NON-NLS-1$
        return "Passt!"; //$NON-NLS-1$
      }
      throw new IllegalArgumentException();
    }
  };

  public void testNameOnlyEquipment() throws Exception {
    IEquipmentItem model = new DummyEquipmentObject("First and Forsaken Weapon", null); //$NON-NLS-1$
    IEquipmentObjectView view = mock(IEquipmentObjectView.class);
    initPresentation(model, view);
    verify(view).setItemTitle("First and Forsaken Weapon"); //$NON-NLS-1$;
  }

  private void initPresentation(IEquipmentItem model, IEquipmentObjectView view) {
    IEquipmentCharacterDataProvider dataProvider = mock(IEquipmentCharacterDataProvider.class);
    IEquipmentCharacterOptionProvider optionProvider = mock(IEquipmentCharacterOptionProvider.class);
    when(dataProvider.getSpecialties(isA(ITraitType.class))).thenReturn(new INamedGenericTrait[0]);
    new EquipmentObjectPresenter(model, view, equipmentStringBuilder, dataProvider, optionProvider, 
            new AnathemaResources()).initPresentation();
  }

  public void testEquipmentWithoutStats() throws Exception {
    IEquipmentObjectView view = mock(IEquipmentObjectView.class);
    DummyEquipmentObject model = new DummyEquipmentObject("First and Forsaken Weapon", //$NON-NLS-1$
      "Abyssal-Weapon mit Bums"); //$NON-NLS-1$
    initPresentation(model, view);
    verify(view).setItemTitle("First and Forsaken Weapon"); //$NON-NLS-1$
    verify(view).setItemDescription("Abyssal-Weapon mit Bums"); //$NON-NLS-1$)
  }

  public void testEquipmentWithCloseCombatStats() throws Exception {
    IEquipmentObjectView view = mock(IEquipmentObjectView.class);
    view.setItemTitle("Title"); //$NON-NLS-1$
    BooleanModel isPrintSelectedModel = new BooleanModel();
    when(view.addStats("Passt!")).thenReturn(isPrintSelectedModel); //$NON-NLS-1$
    DummyEquipmentObject model = new DummyEquipmentObject("Title", null); //$NON-NLS-1$
    model.addEquipment(new DemoMeleeWeapon(new Identificate("Sword"), 5, 2, 7, 1, HealthType.Lethal, -1, 0, 2)); //$NON-NLS-1$
    initPresentation(model, view);
  }

  public void testPrintModelInitialization() throws Exception {
    IEquipmentObjectView view = mock(IEquipmentObjectView.class);
    view.setItemTitle("Title"); //$NON-NLS-1$
    BooleanModel isPrintSelectedModel = new BooleanModel();
    when(view.addStats("Passt!")).thenReturn(isPrintSelectedModel); //$NON-NLS-1$
    DummyEquipmentObject model = new DummyEquipmentObject("Title", null); //$NON-NLS-1$
    model.addEquipment(new DemoMeleeWeapon(new Identificate("Sword"), 5, 2, 7, 1, HealthType.Lethal, -1, 0, 2)); //$NON-NLS-1$
    initPresentation(model, view);
    assertFalse(isPrintSelectedModel.getValue());
  }
}