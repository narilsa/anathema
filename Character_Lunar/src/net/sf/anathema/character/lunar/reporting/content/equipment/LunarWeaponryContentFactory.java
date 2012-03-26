package net.sf.anathema.character.lunar.reporting.content.equipment;

import net.sf.anathema.character.generic.character.IGenericCharacter;
import net.sf.anathema.character.generic.character.IGenericDescription;
import net.sf.anathema.character.reporting.pdf.content.RegisteredReportContent;
import net.sf.anathema.character.reporting.pdf.content.ReportContentFactory;
import net.sf.anathema.character.reporting.pdf.content.ReportSession;
import net.sf.anathema.lib.resources.IResources;

@RegisteredReportContent(produces = LunarWeaponryContent.class)
public class LunarWeaponryContentFactory implements ReportContentFactory<LunarWeaponryContent> {

  private IResources resources;

  public LunarWeaponryContentFactory(IResources resources)  {
    this.resources = resources;
  }

  @Override
  public LunarWeaponryContent create(ReportSession session, IGenericCharacter character,
          IGenericDescription description) {
    return new LunarWeaponryContent(resources, character);
  }
}
