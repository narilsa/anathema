package net.sf.anathema.character.linguistics.model;

import java.util.Arrays;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import net.disy.commons.core.util.ArrayUtilities;
import net.disy.commons.core.util.Ensure;
import net.disy.commons.core.util.StringUtilities;
import net.sf.anathema.character.generic.framework.additionaltemplate.listening.ConfigurableCharacterChangeListener;
import net.sf.anathema.character.generic.framework.additionaltemplate.model.ICharacterModelContext;
import net.sf.anathema.character.generic.rules.IEditionVisitor;
import net.sf.anathema.character.generic.rules.IExaltedEdition;
import net.sf.anathema.character.generic.traits.types.AbilityType;
import net.sf.anathema.character.generic.traits.types.AttributeType;
import net.sf.anathema.character.library.removableentry.model.AbstractRemovableEntryModel;
import net.sf.anathema.character.linguistics.presenter.ILinguisticsModel;
import net.sf.anathema.lib.control.change.ChangeControl;
import net.sf.anathema.lib.control.change.IChangeListener;
import net.sf.anathema.lib.util.IIdentificate;
import net.sf.anathema.lib.util.Identificate;

public class LinguisticsModel extends AbstractRemovableEntryModel<IIdentificate> implements ILinguisticsModel {

  private final IIdentificate[] languages = new IIdentificate[] { new Identificate("HighRealm"), //$NON-NLS-1$
      new Identificate("LowRealm"), new Identificate("OldRealm"), new Identificate("Riverspeak"), new Identificate("Skytongue"), new Identificate("Flametongue"), new Identificate("Seatongue"), new Identificate("Foresttongue"), new Identificate("GuildCant"), new Identificate("ClawSpeak"), new Identificate("HighHolySpeech"), new Identificate("Pelagial") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$

  private IIdentificate selection;
  private int languagePointsAllowed;
  private int barbarianLanguagesPerPoint;
  private final ICharacterModelContext context;
  private final ChangeControl pointControl = new ChangeControl();

  public LinguisticsModel(final ICharacterModelContext context) {
    this.context = context;
    context.getBasicCharacterContext().getEdition().accept(new IEditionVisitor() {
      @Override
      public void visitFirstEdition(IExaltedEdition visitedEdition) {
        ConfigurableCharacterChangeListener listener = new ConfigurableCharacterChangeListener() {
          @Override
          public void configuredChangeOccured() {
            updateBarbarianLanguageAllowance();
          }
        };
        listener.addTraitTypes(AttributeType.Intelligence);
        context.getCharacterListening().addChangeListener(listener);
        updateBarbarianLanguageAllowance();
      }

      @Override
      public void visitSecondEdition(IExaltedEdition visitedEdition) {
        barbarianLanguagesPerPoint = 4;
      }
    });
    ConfigurableCharacterChangeListener listener = new ConfigurableCharacterChangeListener() {
      @Override
      public void configuredChangeOccured() {
        updateLanguagePointAllowance();
      }
    };
    listener.addTraitTypes(AbilityType.Linguistics);
    context.getCharacterListening().addChangeListener(listener);
    updateLanguagePointAllowance();
  }

  private void updateLanguagePointAllowance() {
	int currentPoints = languagePointsAllowed;
    languagePointsAllowed = context.getTraitCollection().getTrait(AbilityType.Linguistics).getCurrentValue() + 1;
    if (currentPoints != languagePointsAllowed)
    	pointControl.fireChangedEvent();
  }

  private void updateBarbarianLanguageAllowance() {
    barbarianLanguagesPerPoint = context.getTraitCollection().getTrait(AttributeType.Intelligence).getCurrentValue();
    pointControl.fireChangedEvent();
  }

  @Override
  public IIdentificate[] getPredefinedLanguages() {
    return languages;
  }

  @Override
  protected IIdentificate createEntry() {
    IIdentificate selectionCopy = selection;
    this.selection = null;
    fireEntryChanged();
    return selectionCopy;
  }

  @Override
  public boolean isEntryAllowed() {
    return selection != null && !getEntries().contains(selection);
  }

  @Override
  public boolean isPredefinedLanguage(Object object) {
    return ArrayUtilities.containsValue(languages, object);
  }

  @Override
  public void selectBarbarianLanguage(String customName) {
    if (StringUtilities.isNullOrTrimmedEmpty(customName)) {
      this.selection = null;
      fireEntryChanged();
    }
    selectLanguage(new Identificate(customName));
  }

  @Override
  public void selectLanguage(final IIdentificate language) {
    Ensure.ensureNotNull(language);
    IIdentificate foundLanguage = Iterables.find(getEntries(), new Predicate<IIdentificate>() {
      @Override
      public boolean apply(IIdentificate selectedLanguage) {
        return Objects.equal(language, selectedLanguage);
      }
    }, null);
    if (foundLanguage != null) {
      return;
    }
    this.selection = language;
    fireEntryChanged();
  }

  @Override
  public IIdentificate getPredefinedLanguageById(final String id) {
    return Iterables.find(Arrays.asList(languages), new Predicate<IIdentificate>(){
      @Override
      public boolean apply(IIdentificate definedLanuage) {
        return Objects.equal(id, definedLanuage.getId());
      }
    },null);
  }

  @Override
  public void addCharacterChangedListener(IChangeListener listener) {
    pointControl.addChangeListener(listener);
  }

  @Override
  public int getBarbarianLanguageCount() {
    int count = 0;
    for (IIdentificate language : getEntries()) {
      if (!isPredefinedLanguage(language)) {
        count++;
      }
    }
    return count;
  }

  @Override
  public int getLanguagePointsAllowed() {
	updateLanguagePointAllowance();
    return languagePointsAllowed;
  }

  @Override
  public int getLanguagePointsSpent() {
    int spent = getPredefinedLanguageCount();
    spent += Math.ceil((double) getBarbarianLanguageCount() / barbarianLanguagesPerPoint);
    return spent;
  }

  @Override
  public int getPredefinedLanguageCount() {
    int count = 0;
    for (IIdentificate language : getEntries()) {
      if (isPredefinedLanguage(language)) {
        count++;
      }
    }
    return count;
  }
}