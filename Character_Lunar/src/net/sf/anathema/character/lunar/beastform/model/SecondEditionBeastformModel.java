package net.sf.anathema.character.lunar.beastform.model;

import net.sf.anathema.character.equipment.IEquipmentAdditionalModelTemplate;
import net.sf.anathema.character.equipment.character.model.IEquipmentAdditionalModel;
import net.sf.anathema.character.equipment.character.model.IEquipmentPrintModel;
import net.sf.anathema.character.equipment.impl.character.model.print.EquipmentPrintModel;
import net.sf.anathema.character.generic.additionaltemplate.AbstractAdditionalModelAdapter;
import net.sf.anathema.character.generic.additionaltemplate.AdditionalModelType;
import net.sf.anathema.character.generic.character.IGenericTraitCollection;
import net.sf.anathema.character.generic.framework.additionaltemplate.listening.GlobalCharacterChangeAdapter;
import net.sf.anathema.character.generic.framework.additionaltemplate.model.ICharacterModelContext;
import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.generic.traits.types.AttributeGroupType;
import net.sf.anathema.character.generic.traits.types.AttributeType;
import net.sf.anathema.character.lunar.beastform.BeastformTemplate;
import net.sf.anathema.character.lunar.beastform.model.gift.SecondEditionMutationModel;
import net.sf.anathema.character.lunar.beastform.presenter.IBeastformAttribute;
import net.sf.anathema.character.lunar.beastform.presenter.IBeastformModel;
import net.sf.anathema.character.mutations.model.IMutationsModel;
import net.sf.anathema.lib.control.change.GlobalChangeAdapter;
import net.sf.anathema.lib.control.change.IChangeListener;
import net.sf.anathema.lib.control.intvalue.IIntValueChangedListener;
import net.sf.anathema.lib.control.intvalue.IntValueControl;

import java.util.ArrayList;
import java.util.List;

public class SecondEditionBeastformModel extends AbstractAdditionalModelAdapter implements IBeastformModel {
  private final ICharacterModelContext context;
  private final IntValueControl charmLearnControl = new IntValueControl();
  private final IBeastformGroupCost cost;
  private final BeastformTraitCollection beastCollection;
  private final BeastformTraitCollection spiritCollection;
  private final IMutationsModel mutationModel;
  private final BeastformGenericTraitCollection allTraitsCollection;
  private final IEquipmentPrintModel equipmentModel;
  private String spiritForm = "";

  public SecondEditionBeastformModel(ICharacterModelContext context) {
    this.context = context;
    this.mutationModel = new SecondEditionMutationModel(context, this);
    this.beastCollection = new BeastformTraitCollection();
    this.spiritCollection = new BeastformTraitCollection();
    this.cost = new BeastformGroupCost(beastCollection, this);
    createAttributes();
    this.allTraitsCollection = new BeastformGenericTraitCollection(context.getTraitCollection(), beastCollection,
            mutationModel);

    IEquipmentAdditionalModel equipment = (IEquipmentAdditionalModel) context.getAdditionalModel(
            IEquipmentAdditionalModelTemplate.ID);
    this.equipmentModel = new EquipmentPrintModel(equipment,
            new BeastformNaturalSoak(allTraitsCollection, mutationModel));
    context.getCharacterListening().addChangeListener(new GlobalCharacterChangeAdapter() {
      @Override
      public void characterChanged() {
        update();
      }
    });
    update();
  }

  @Override
  public IEquipmentPrintModel getEquipmentModel() {
    return equipmentModel;
  }

  private void createAttributes() {
    List<IBeastformAttribute> attributes = new ArrayList<IBeastformAttribute>();
    attributes.add(
            new BeastformAttribute(context, context.getTraitCollection().getTrait(AttributeType.Strength), 1, cost));
    attributes.add(
            new BeastformAttribute(context, context.getTraitCollection().getTrait(AttributeType.Dexterity), 2, cost));
    attributes.add(
            new BeastformAttribute(context, context.getTraitCollection().getTrait(AttributeType.Stamina), 1, cost));
    for (IBeastformAttribute attribute : attributes) {
      beastCollection.addBeastFormAttribute(attribute);
    }

    attributes.clear();
    attributes.add(new SpiritFormAttribute(context.getTraitCollection().getTrait(AttributeType.Strength), context));
    attributes.add(new SpiritFormAttribute(context.getTraitCollection().getTrait(AttributeType.Dexterity), context));
    attributes.add(new SpiritFormAttribute(context.getTraitCollection().getTrait(AttributeType.Stamina), context));
    attributes.add(new SpiritFormAttribute(context.getTraitCollection().getTrait(AttributeType.Appearance), context));
    for (IBeastformAttribute attribute : attributes) {
      spiritCollection.addBeastFormAttribute(attribute);
    }
  }

  private void update() {
    charmLearnControl.fireValueChangedEvent(getCharmValue());
    for (IBeastformAttribute attribute : getAttributes()) {
      attribute.recalculate();
    }
    for (IBeastformAttribute attribute : getSpiritAttributes()) {
      attribute.recalculate();
    }
  }

  @Override
  public IBeastformAttribute[] getAttributes() {
    List<IBeastformAttribute> traits = new ArrayList<IBeastformAttribute>();
    for (AttributeType type : AttributeType.getAllFor(AttributeGroupType.Physical)) {
      traits.add(beastCollection.getDeadlyBeastmanAttribute(type));
    }
    return traits.toArray(new IBeastformAttribute[traits.size()]);
  }

  public String getSpiritForm() {
    return spiritForm;
  }

  public void setSpiritForm(String newName) {
    spiritForm = newName;
  }

  public IBeastformAttribute[] getSpiritAttributes() {
    List<IBeastformAttribute> traits = new ArrayList<IBeastformAttribute>();
    for (AttributeType type : AttributeType.getAllFor(AttributeGroupType.Physical)) {
      traits.add(spiritCollection.getDeadlyBeastmanAttribute(type));
    }
    traits.add(spiritCollection.getDeadlyBeastmanAttribute(AttributeType.Appearance));
    return traits.toArray(new IBeastformAttribute[traits.size()]);
  }
  @Override
  public int getCharmValue() {
    for (ICharm charm : context.getCharmContext().getCharmConfiguration().getLearnedCharms())
      if (charm.getId().equals("Lunar.DeadlyBeastmanTransformation")) return 1;
    return 0;
  }

  @Override
  public void addCharmLearnCountChangedListener(IIntValueChangedListener listener) {
    charmLearnControl.addIntValueChangeListener(listener);
  }

  @Override
  public String getTemplateId() {
    return BeastformTemplate.TEMPLATE_ID;
  }

  @Override
  public AdditionalModelType getAdditionalModelType() {
    return AdditionalModelType.Magic;
  }

  @Override
  public void addChangeListener(IChangeListener listener) {
    mutationModel.addModelChangeListener(listener);
    for (IBeastformAttribute trait : getAttributes()) {
      trait.getTrait().addCurrentValueListener(new GlobalChangeAdapter<Object>(listener));
    }
    for (IBeastformAttribute trait : getSpiritAttributes()) {
      trait.getTrait().addCurrentValueListener(new GlobalChangeAdapter<Object>(listener));
    }
  }

  @Override
  public IMutationsModel getMutationModel() {
    return mutationModel;
  }

  @Override
  public IBeastformAttribute getAttributeByType(AttributeType type) {
    return beastCollection.getDeadlyBeastmanAttribute(type);
  }

  public IBeastformAttribute getSpiritAttributeByType(AttributeType type) {
    return spiritCollection.getDeadlyBeastmanAttribute(type);
  }

  public IGenericTraitCollection getSpiritTraitCollection() {
    return spiritCollection;
  }

  @Override
  public IGenericTraitCollection getBeastTraitCollection() {
    return allTraitsCollection;
  }
}