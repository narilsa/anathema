package net.sf.anathema.character.lunar.reporting.rendering.anima;

import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import net.sf.anathema.character.reporting.pdf.content.ReportSession;
import net.sf.anathema.character.reporting.pdf.content.stats.anima.ColumnDescriptor;
import net.sf.anathema.character.reporting.pdf.rendering.graphics.SheetGraphics;
import net.sf.anathema.lib.resources.IResources;

public class AnimaTableEncoder extends net.sf.anathema.character.reporting.pdf.rendering.boxes.anima.AnimaTableEncoder {

  public AnimaTableEncoder(IResources resources, float fontSize) {
    super(resources, fontSize);
  }

  @Override
  protected ColumnDescriptor[] getColumns() {
    return new ColumnDescriptor[]{new ColumnDescriptor(0.135f, "Sheet.AnimaTable.Header.Motes"), //$NON-NLS-1$
            new ColumnDescriptor(0.495f, "Sheet.AnimaTable.Header.BannerFlare"), //$NON-NLS-1$
            new ColumnDescriptor(0.145f, "Sheet.AnimaTable.Header.FormsLocked"), //$NON-NLS-1$
            new ColumnDescriptor(0.225f, "Sheet.AnimaTable.Header.Stealth")}; //$NON-NLS-1$
  }

  @Override
  protected void addAnimaRow(SheetGraphics graphics, PdfPTable table, int level, ReportSession session, String descriptionPrefix) {
    table.addCell(createRangeCell(graphics, level, session.getCharacter()));
    table.addCell(createDescriptionCell(graphics, level, descriptionPrefix));
    table.addCell(createFormCell(graphics, level));
    table.addCell(createStealthCell(graphics, level));
  }

  protected final PdfPCell createFormCell(SheetGraphics graphics, int level) {
    if (level < 2) {
      return createContentCell(graphics, ""); //$NON-NLS-1$
    }
    PdfPCell cell = new PdfPCell(new Phrase(graphics.createSymbolChunk()));
    configureCell(cell);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    return cell;
  }
}
