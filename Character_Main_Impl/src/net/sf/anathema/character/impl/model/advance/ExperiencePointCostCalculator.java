package net.sf.anathema.character.impl.model.advance;

import net.sf.anathema.character.generic.IBasicCharacterData;
import net.sf.anathema.character.generic.character.IGenericTraitCollection;
import net.sf.anathema.character.generic.framework.additionaltemplate.model.IBasicTrait;
import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.generic.magic.ISpell;
import net.sf.anathema.character.generic.template.experience.ICurrentRatingCosts;
import net.sf.anathema.character.generic.template.experience.IExperiencePointCosts;
import net.sf.anathema.character.generic.template.magic.FavoringTraitType;
import net.sf.anathema.character.library.trait.ITrait;
import net.sf.anathema.character.library.trait.experience.TraitRatingCostCalculator;
import net.sf.anathema.character.library.trait.subtrait.ISubTrait;
import net.sf.anathema.character.library.trait.visitor.IAggregatedTrait;
import net.sf.anathema.character.library.trait.visitor.IDefaultTrait;
import net.sf.anathema.character.library.trait.visitor.ITraitVisitor;

public class ExperiencePointCostCalculator implements IPointCostCalculator {

  private final IExperiencePointCosts costs;

  public ExperiencePointCostCalculator(IExperiencePointCosts costs) {
    this.costs = costs;
  }

  protected int getTraitRatingCosts(IBasicTrait trait, ICurrentRatingCosts ratingCosts) {
    return TraitRatingCostCalculator.getTraitRatingCosts(trait, ratingCosts);
  }

  @Override
  public int getAbilityCosts(ITrait ability, final boolean favored) {
    final int[] abilityCosts = new int[1];
    ability.accept(new ITraitVisitor() {

      @Override
      public void visitAggregatedTrait(IAggregatedTrait visitedTrait) {
        int sumCost = 0;
        for (ISubTrait subTrait : visitedTrait.getSubTraits().getSubTraits()) {
          sumCost += getTraitRatingCosts(subTrait, costs.getAbilityCosts(favored));
        }
        abilityCosts[0] = sumCost;
      }

      @Override
      public void visitDefaultTrait(IDefaultTrait visitedTrait) {
        abilityCosts[0] = getTraitRatingCosts(visitedTrait, costs.getAbilityCosts(favored));
      }

    });
    return abilityCosts[0];
  }

  @Override
  public int getAttributeCosts(ITrait attribute, final boolean favored) {
    return getTraitRatingCosts((IDefaultTrait)attribute, costs.getAttributeCosts(favored));
  }

  @Override
  public int getEssenceCosts(IBasicTrait essence) {
    return getTraitRatingCosts(essence, costs.getEssenceCosts());
  }

  @Override
  public int getVirtueCosts(IBasicTrait virtue) {
    return getTraitRatingCosts(virtue, costs.getVirtueCosts());
  }

  @Override
  public int getWillpowerCosts(IBasicTrait willpower) {
    return getTraitRatingCosts(willpower, costs.getWillpowerCosts());
  }

  @Override
  public double getSpecialtyCosts(boolean favored) {
    return costs.getSpecialtyCosts(favored);
  }

  @Override
  public int getSpellCosts(ISpell spell, IBasicCharacterData basicCharacter, IGenericTraitCollection traitCollection) {
    return costs.getSpellCosts(spell, basicCharacter, traitCollection); 
  }

  @Override
  public int getCharmCosts(
      ICharm charm,
      IBasicCharacterData basicCharacter,
      IGenericTraitCollection traitCollection,
      FavoringTraitType type) {
    return costs.getCharmCosts(charm, new CostAnalyzer(basicCharacter, traitCollection));
  }
}