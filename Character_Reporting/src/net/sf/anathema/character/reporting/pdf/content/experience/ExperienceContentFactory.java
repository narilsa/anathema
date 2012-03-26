package net.sf.anathema.character.reporting.pdf.content.experience;

import net.sf.anathema.character.generic.character.IGenericCharacter;
import net.sf.anathema.character.generic.character.IGenericDescription;
import net.sf.anathema.character.reporting.pdf.content.RegisteredReportContent;
import net.sf.anathema.character.reporting.pdf.content.ReportContentFactory;
import net.sf.anathema.character.reporting.pdf.content.ReportSession;
import net.sf.anathema.lib.resources.IResources;

@RegisteredReportContent(produces = ExperienceContent.class)
public class ExperienceContentFactory implements ReportContentFactory<ExperienceContent> {

  private IResources resources;

  public ExperienceContentFactory(IResources resources) {
    this.resources = resources;
  }

  @Override
  public ExperienceContent create(ReportSession session, IGenericCharacter character, IGenericDescription description) {
    return new ExperienceContent(resources, character);
  }
}
