package net.sf.anathema.character.intimacies.reporting.content;

import net.sf.anathema.character.generic.character.IGenericCharacter;
import net.sf.anathema.character.generic.character.IGenericDescription;
import net.sf.anathema.character.reporting.pdf.content.RegisteredReportContent;
import net.sf.anathema.character.reporting.pdf.content.ReportContentFactory;
import net.sf.anathema.character.reporting.pdf.content.ReportSession;
import net.sf.anathema.lib.resources.IResources;

@RegisteredReportContent(produces = SimpleIntimaciesContent.class)
public class SimpleIntimaciesContentFactory implements ReportContentFactory<SimpleIntimaciesContent> {
  private IResources resources;

  public SimpleIntimaciesContentFactory(IResources resources) {
    this.resources = resources;
  }

  @Override

  public SimpleIntimaciesContent create(ReportSession session, IGenericCharacter character,
          IGenericDescription description) {
    return new SimpleIntimaciesContent(resources, character);
  }
}
