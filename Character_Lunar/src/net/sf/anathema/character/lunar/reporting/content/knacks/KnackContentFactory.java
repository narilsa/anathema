package net.sf.anathema.character.lunar.reporting.content.knacks;

import net.sf.anathema.character.generic.character.IGenericCharacter;
import net.sf.anathema.character.generic.character.IGenericDescription;
import net.sf.anathema.character.reporting.pdf.content.RegisteredReportContent;
import net.sf.anathema.character.reporting.pdf.content.ReportContentFactory;
import net.sf.anathema.character.reporting.pdf.content.ReportSession;
import net.sf.anathema.lib.resources.IResources;

@RegisteredReportContent(produces = KnackContent.class)
public class KnackContentFactory implements ReportContentFactory<KnackContent> {

  private IResources resources;

  public KnackContentFactory(IResources resources) {
    this.resources = resources;
  }

  @Override
  public KnackContent create(ReportSession session, IGenericCharacter character, IGenericDescription description) {
    return new KnackContent(resources, character);
  }
}
