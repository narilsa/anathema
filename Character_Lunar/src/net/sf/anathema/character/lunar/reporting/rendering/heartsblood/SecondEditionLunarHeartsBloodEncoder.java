package net.sf.anathema.character.lunar.reporting.rendering.heartsblood;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import net.sf.anathema.character.lunar.heartsblood.HeartsBloodTemplate;
import net.sf.anathema.character.lunar.heartsblood.presenter.IAnimalForm;
import net.sf.anathema.character.lunar.heartsblood.presenter.IHeartsBloodModel;
import net.sf.anathema.character.lunar.reporting.content.stats.heartsblood.*;
import net.sf.anathema.character.reporting.pdf.content.ReportSession;
import net.sf.anathema.character.reporting.pdf.content.stats.IStatsGroup;
import net.sf.anathema.character.reporting.pdf.rendering.extent.Bounds;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.ContentEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.general.stats.AbstractStatsTableEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.graphics.SheetGraphics;
import net.sf.anathema.lib.resources.IResources;
import net.sf.anathema.lib.util.IIdentificate;
import net.sf.anathema.lib.util.Identificate;

import java.util.ArrayList;
import java.util.List;

public class SecondEditionLunarHeartsBloodEncoder extends AbstractStatsTableEncoder<IHeartsBloodStats, ReportSession> implements ContentEncoder {

  private final IResources resources;

  public SecondEditionLunarHeartsBloodEncoder(IResources resources) {
    this.resources = resources;
  }

  @Override
  public void encode(SheetGraphics graphics, ReportSession reportSession, Bounds bounds) throws DocumentException {
    encodeTable(graphics, reportSession, bounds);
  }

  protected IHeartsBloodStats[] getPrintStats(ReportSession session) {
    IHeartsBloodModel model = (IHeartsBloodModel) session.getCharacter().getAdditionalModel(HeartsBloodTemplate.TEMPLATE_ID);
    List<IHeartsBloodStats> stats = new ArrayList<IHeartsBloodStats>();
    for (final IAnimalForm form : model.getEntries()) {
      stats.add(new IHeartsBloodStats() {
        @Override
        public IIdentificate getName() {
          return new Identificate(form.getName());
        }

        @Override
        public String getAppearanceString() {
          return String.valueOf(form.getAppearance());
        }

        @Override
        public String getStaminaString() {
          return String.valueOf(form.getStamina());
        }

        @Override
        public String getDexterityString() {
          return String.valueOf(form.getDexterity());
        }

        @Override
        public String getStrengthString() {
          return String.valueOf(form.getStrength());
        }
      });
    }
    return stats.toArray(new IHeartsBloodStats[stats.size()]);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected IStatsGroup<IHeartsBloodStats>[] createStatsGroups(ReportSession session) {
    return new IStatsGroup[]{new HeartsBloodNameStatsGroup(resources), new HeartsBloodStrengthStatsGroup(resources), new HeartsBloodDexterityStatsGroup(resources), new HeartsBloodStaminaStatsGroup(resources), new HeartsBloodAppearanceStatsGroup(resources), new HeartsBloodNotesStatsGroup(resources)};
  }

  @Override
  protected void encodeContent(SheetGraphics graphics, PdfPTable table, ReportSession session, Bounds bounds) {
    float heightLimit = bounds.height - 3;
    IHeartsBloodStats[] statSet = getPrintStats(session);
    IStatsGroup<IHeartsBloodStats>[] statGroups = createStatsGroups(session);

    //boolean encodeLine = true;
    for (IHeartsBloodStats stats : statSet) {
      if (table.getTotalHeight() < heightLimit) {
        encodeContentLine(graphics, table, statGroups, stats);
      }
    }
    while (table.getTotalHeight() < heightLimit) {
      encodeContentLine(graphics, table, statGroups, null);
    }
    table.deleteLastRow();
  }

  @Override
  public String getHeader(ReportSession session) {
    return resources.getString("Sheet.Header.Lunar.HeartsBlood");
  }

  @Override
  public boolean hasContent(ReportSession session) {
    return true;
  }
}
