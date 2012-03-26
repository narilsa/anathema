package net.sf.anathema.character.equipment.impl.reporting.content.stats.weapons;

import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPTable;
import net.sf.anathema.character.equipment.impl.reporting.content.stats.AbstractValueEquipmentStatsGroup;
import net.sf.anathema.character.generic.equipment.IEquipmentModifiers;
import net.sf.anathema.character.generic.equipment.weapon.IWeaponStats;
import net.sf.anathema.lib.resources.IResources;

public abstract class AbstractSpeedWeaponStatsGroup extends AbstractValueEquipmentStatsGroup<IWeaponStats> {

  private final IEquipmentModifiers equipment;

  public AbstractSpeedWeaponStatsGroup(IResources resources, IEquipmentModifiers equipment) {
    super(resources, "Speed"); //$NON-NLS-1$
    this.equipment = equipment;
  }

  @Override
  public int getColumnCount() {
    return 1;
  }

  @Override
  public void addContent(PdfPTable table, Font font, IWeaponStats weapon) {
    if (weapon == null) {
      table.addCell(createFinalValueCell(font));
    } else {
      table.addCell(createFinalValueCell(font, getSpeedValue(weapon, equipment)));
    }
  }

  protected abstract int getSpeedValue(IWeaponStats weapon, IEquipmentModifiers equipment);
}
