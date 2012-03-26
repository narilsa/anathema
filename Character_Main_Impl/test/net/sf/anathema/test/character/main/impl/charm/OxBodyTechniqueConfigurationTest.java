package net.sf.anathema.test.character.main.impl.charm;

import net.sf.anathema.character.dummy.trait.DummyCoreTraitConfiguration;
import net.sf.anathema.character.generic.caste.ICasteType;
import net.sf.anathema.character.generic.dummy.DummyCasteType;
import net.sf.anathema.character.generic.dummy.DummyCharacterModelContext;
import net.sf.anathema.character.generic.framework.additionaltemplate.model.ITraitContext;
import net.sf.anathema.character.generic.framework.additionaltemplate.model.ITraitValueStrategy;
import net.sf.anathema.character.generic.health.HealthLevelType;
import net.sf.anathema.character.generic.impl.magic.charm.special.OxBodyTechniqueCharm;
import net.sf.anathema.character.generic.impl.traits.SimpleTraitTemplate;
import net.sf.anathema.character.generic.traits.IGenericTrait;
import net.sf.anathema.character.generic.traits.ITraitTemplate;
import net.sf.anathema.character.generic.traits.ITraitType;
import net.sf.anathema.character.generic.traits.types.AbilityType;
import net.sf.anathema.character.impl.model.HealthConfiguration;
import net.sf.anathema.character.impl.model.charm.special.OxBodyTechniqueConfiguration;
import net.sf.anathema.character.impl.model.context.trait.CreationTraitValueStrategy;
import net.sf.anathema.character.library.trait.DefaultTrait;
import net.sf.anathema.character.library.trait.FriendlyValueChangeChecker;
import net.sf.anathema.character.library.trait.IFavorableDefaultTrait;
import net.sf.anathema.character.library.trait.favorable.FriendlyIncrementChecker;
import net.sf.anathema.character.library.trait.rules.FavorableTraitRules;
import net.sf.anathema.character.model.charm.OxBodyCategory;
import net.sf.anathema.character.model.charm.special.IOxBodyTechniqueConfiguration;
import net.sf.anathema.test.character.BasicCharacterTestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;

public class OxBodyTechniqueConfigurationTest {

  private IFavorableDefaultTrait endurance;
  private IOxBodyTechniqueConfiguration configuration;
  private HealthConfiguration health;
  private final DummyCoreTraitConfiguration collection = new DummyCoreTraitConfiguration();

  @SuppressWarnings("serial")
  @Before
  public void setUp() throws Exception {
    ITraitValueStrategy strategy = new CreationTraitValueStrategy();
    DummyCharacterModelContext modelContext = new BasicCharacterTestCase().createModelContextWithEssence2(strategy);
    ITraitTemplate enduranceTemplate = SimpleTraitTemplate.createEssenceLimitedTemplate(0);
    ITraitContext traitContext = modelContext.getTraitContext();
    FavorableTraitRules enduranceRules = new FavorableTraitRules(
            AbilityType.Resistance,
            enduranceTemplate,
            traitContext.getLimitationContext());
    endurance = new DefaultTrait(
            enduranceRules,
            new ICasteType[]{new DummyCasteType()},
            traitContext,
            modelContext.getBasicCharacterContext(),
            modelContext.getCharacterListening(),
            new FriendlyValueChangeChecker(),
            new FriendlyIncrementChecker());
    health = new HealthConfiguration(new IGenericTrait[]{endurance});
    collection.addTestTrait(endurance);
    configuration = new OxBodyTechniqueConfiguration(
            traitContext,
            collection,
            null,
            new ITraitType[]{endurance.getType()},
            health.getOxBodyLearnArbitrator(),
            createObtCharm());
    health.getOxBodyLearnArbitrator().addOxBodyTechniqueConfiguration(configuration);
    health.addHealthLevelProvider(configuration.getHealthLevelProvider());
  }

  @Test
  public void testOxBodyDecrease() {
    OxBodyCategory[] categories = configuration.getCategories();
    endurance.setCurrentValue(5);
    assertEquals(5, endurance.getCreationValue());
    categories[0].setCurrentValue(3);
    assertEquals(3, categories[0].getCreationValue());
    categories[1].setCurrentValue(2);
    assertEquals(2, categories[1].getCreationValue());
    endurance.setCurrentValue(0);
    assertEquals(0, endurance.getCreationValue());
    categories[0].setCurrentValue(0);
    assertEquals(0, categories[0].getCreationValue());
    categories[1].setCurrentValue(0);
    assertEquals(0, categories[1].getCreationValue());
  }

  @Test
  public void testTwoOxBodyTechniques() {
    @SuppressWarnings("serial")
    OxBodyTechniqueConfiguration secondConfiguration = new OxBodyTechniqueConfiguration(
            new BasicCharacterTestCase().createModelContextWithEssence2(new CreationTraitValueStrategy()).getTraitContext(),
            collection,
            null,
            new ITraitType[]{endurance.getType()},
            health.getOxBodyLearnArbitrator(),
            createObtCharm());
    health.getOxBodyLearnArbitrator().addOxBodyTechniqueConfiguration(secondConfiguration);
    health.addHealthLevelProvider(secondConfiguration.getHealthLevelProvider());
    endurance.setCurrentValue(2);
    configuration.getCategories()[0].setCurrentValue(2);
    assertEquals(2, configuration.getCategories()[0].getCreationValue());
    secondConfiguration.getCategories()[0].setCurrentValue(1);
    assertEquals(0, secondConfiguration.getCategories()[0].getCreationValue());
  }

  private OxBodyTechniqueCharm createObtCharm() {
    return new OxBodyTechniqueCharm(
            "Abyssal.Ox-BodyTechnique", AbilityType.Resistance, new LinkedHashMap<String, HealthLevelType[]>() { //$NON-NLS-1$
      {
        this.put("OxBody0", new HealthLevelType[]{HealthLevelType.ZERO}); //$NON-NLS-1$
        this.put("OxBody1", new HealthLevelType[]{HealthLevelType.ONE}); //$NON-NLS-1$
      }
    });
  }
}