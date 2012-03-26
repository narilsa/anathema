package net.sf.anathema.character.equipment.impl.reporting.content;

import net.sf.anathema.character.generic.character.IGenericCharacter;
import net.sf.anathema.character.generic.character.IGenericDescription;
import net.sf.anathema.character.reporting.pdf.content.RegisteredReportContent;
import net.sf.anathema.character.reporting.pdf.content.ReportContentFactory;
import net.sf.anathema.character.reporting.pdf.content.ReportSession;
import net.sf.anathema.lib.resources.IResources;

@RegisteredReportContent(produces = PossessionsContent.class)
public class PossessionsContentFactory implements ReportContentFactory<PossessionsContent> {

  private IResources resources;

  public PossessionsContentFactory(IResources resources) {
    this.resources = resources;
  }

  @Override
  public PossessionsContent create(ReportSession session, IGenericCharacter character, IGenericDescription description) {
    return new PossessionsContent(resources, character);
  }
}
