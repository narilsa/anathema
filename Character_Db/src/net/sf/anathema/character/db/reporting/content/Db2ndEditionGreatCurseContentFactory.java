package net.sf.anathema.character.db.reporting.content;

import net.sf.anathema.character.generic.character.IGenericCharacter;
import net.sf.anathema.character.generic.character.IGenericDescription;
import net.sf.anathema.character.reporting.pdf.content.RegisteredReportContent;
import net.sf.anathema.character.reporting.pdf.content.ReportContentFactory;
import net.sf.anathema.character.reporting.pdf.content.ReportSession;
import net.sf.anathema.lib.resources.IResources;

@RegisteredReportContent(produces = Db2ndEditionGreatCurseContent.class)
public class Db2ndEditionGreatCurseContentFactory implements ReportContentFactory<Db2ndEditionGreatCurseContent> {

  private IResources resources;

  public Db2ndEditionGreatCurseContentFactory(IResources resources) {
    this.resources = resources;
  }

  @Override
  public Db2ndEditionGreatCurseContent create(ReportSession session, IGenericCharacter character,
          IGenericDescription description) {
    return new Db2ndEditionGreatCurseContent(resources, character);
  }
}
