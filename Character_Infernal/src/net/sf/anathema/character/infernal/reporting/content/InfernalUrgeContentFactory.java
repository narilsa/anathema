package net.sf.anathema.character.infernal.reporting.content;

import net.sf.anathema.character.generic.character.IGenericCharacter;
import net.sf.anathema.character.generic.character.IGenericDescription;
import net.sf.anathema.character.reporting.pdf.content.RegisteredReportContent;
import net.sf.anathema.character.reporting.pdf.content.ReportContentFactory;
import net.sf.anathema.character.reporting.pdf.content.ReportSession;
import net.sf.anathema.lib.resources.IResources;

@RegisteredReportContent(produces = InfernalUrgeContent.class)
public class InfernalUrgeContentFactory implements ReportContentFactory<InfernalUrgeContent> {
  private IResources resources;

  public InfernalUrgeContentFactory(IResources resources) {
    this.resources = resources;
  }

  @Override
  public InfernalUrgeContent create(ReportSession session, IGenericCharacter character, IGenericDescription description) {
    return new InfernalUrgeContent(resources, character);
  }
}
