package net.sf.anathema.character.presenter.magic.spells;

import net.sf.anathema.character.model.ICharacterStatistics;
import net.sf.anathema.character.presenter.magic.IContentPresenter;
import net.sf.anathema.character.presenter.magic.detail.MagicAndDetailPresenter;
import net.sf.anathema.character.presenter.magic.detail.MagicDetailPresenter;
import net.sf.anathema.character.view.magic.IMagicViewFactory;
import net.sf.anathema.framework.presenter.view.IViewContent;
import net.sf.anathema.framework.presenter.view.SimpleViewContent;
import net.sf.anathema.framework.view.util.ContentProperties;
import net.sf.anathema.lib.resources.IResources;

public class SpellContentPresenter implements IContentPresenter {

  public static IContentPresenter ForSorcery(MagicDetailPresenter detailPresenter, ICharacterStatistics statistics,
          IResources resources, IMagicViewFactory factory) {
    String title = resources.getString("CardView.CharmConfiguration.Spells.Title");
    SpellPresenter spellPresenter = SpellPresenter.ForSorcery(statistics, resources, factory);
    return new MagicAndDetailPresenter(title, detailPresenter, spellPresenter);
  }

  public static IContentPresenter ForNecromancy(MagicDetailPresenter detailPresenter, ICharacterStatistics statistics,
          IResources resources, IMagicViewFactory factory) {
    String title = resources.getString("CardView.CharmConfiguration.Necromancy.Title");
    SpellPresenter spellPresenter = SpellPresenter.ForNecromancy(statistics, resources, factory);
    return new MagicAndDetailPresenter(title, detailPresenter, spellPresenter);
  }

  private String title;
  private SpellPresenter spellPresenter;

  public SpellContentPresenter(String title, SpellPresenter spellPresenter) {
    this.title = title;
    this.spellPresenter = spellPresenter;
  }

  @Override
  public void initPresentation() {
    spellPresenter.initPresentation();
  }

  @Override
  public IViewContent getTabContent() {
    return new SimpleViewContent(new ContentProperties(title), spellPresenter.getView());
  }
}
