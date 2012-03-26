package net.sf.anathema.character.lunar.reporting.content;

import net.sf.anathema.character.generic.character.IGenericCharacter;
import net.sf.anathema.character.generic.character.IGenericDescription;
import net.sf.anathema.character.reporting.pdf.content.RegisteredReportContent;
import net.sf.anathema.character.reporting.pdf.content.ReportContentFactory;
import net.sf.anathema.character.reporting.pdf.content.ReportSession;
import net.sf.anathema.lib.resources.IResources;

@RegisteredReportContent(produces = GiftContent.class)
public class GiftContentFactory implements ReportContentFactory<GiftContent> {

  private IResources resources;

  public GiftContentFactory(IResources resources) {
    this.resources = resources;
  }

  @Override
  public GiftContent create(ReportSession session, IGenericCharacter character, IGenericDescription description) {
    return new GiftContent(resources, character);
  }
}
