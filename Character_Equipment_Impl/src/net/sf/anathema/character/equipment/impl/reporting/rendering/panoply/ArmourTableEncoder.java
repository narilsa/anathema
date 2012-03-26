package net.sf.anathema.character.equipment.impl.reporting.rendering.panoply;

import com.itextpdf.text.pdf.PdfPTable;
import net.sf.anathema.character.equipment.impl.reporting.content.ArmourContent;
import net.sf.anathema.character.equipment.impl.reporting.content.stats.armour.IArmourStatsGroup;
import net.sf.anathema.character.equipment.impl.reporting.rendering.EquipmentTableEncoder;
import net.sf.anathema.character.generic.equipment.weapon.IArmourStats;
import net.sf.anathema.character.reporting.pdf.content.ReportSession;
import net.sf.anathema.character.reporting.pdf.content.stats.IStatsGroup;
import net.sf.anathema.character.reporting.pdf.rendering.extent.Bounds;
import net.sf.anathema.character.reporting.pdf.rendering.graphics.SheetGraphics;

public class ArmourTableEncoder extends EquipmentTableEncoder<IArmourStats, ArmourContent> {

  public ArmourTableEncoder(Class<? extends ArmourContent> contentClass) {
    super(contentClass);
  }

  @Override
  protected PdfPTable createTable(SheetGraphics graphics, ReportSession reportSession, Bounds bounds) {
    PdfPTable armourTable = super.createTable(graphics, reportSession, bounds);
    ArmourContent content = createContent(reportSession);
    IArmourStats totalArmour = content.getTotalArmour();
    IStatsGroup<IArmourStats>[] groups = content.createStatsGroups();
    for (int index = 0; index < groups.length; index++) {
      if (index != 0) {
        armourTable.addCell(createSpaceCell(graphics));
      }
      IStatsGroup<IArmourStats> group = groups[index];
      if (group instanceof IArmourStatsGroup) {
        ((IArmourStatsGroup) group).addTotal(armourTable, createFont(graphics), totalArmour);
      } else {
        group.addContent(armourTable, createFont(graphics), totalArmour);
      }
    }
    return armourTable;
  }
}
