package net.sf.anathema.character.sidereal.reporting.rendering;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import net.sf.anathema.character.reporting.pdf.content.ReportSession;
import net.sf.anathema.character.reporting.pdf.rendering.CellPadding;
import net.sf.anathema.character.reporting.pdf.rendering.extent.Bounds;
import net.sf.anathema.character.reporting.pdf.rendering.general.table.AbstractTableEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.graphics.SheetGraphics;
import net.sf.anathema.character.reporting.pdf.rendering.graphics.TableCell;
import net.sf.anathema.character.reporting.pdf.rendering.graphics.TableList;
import net.sf.anathema.lib.resources.IResources;

public class FrequencyTableEncoder extends AbstractTableEncoder<ReportSession> {

  private final IResources resources;

  public FrequencyTableEncoder(IResources resources) {
    this.resources = resources;
  }

  @Override
  protected PdfPTable createTable(SheetGraphics graphics, ReportSession session, Bounds bounds) throws DocumentException {
    Font font = graphics.createTableFont();
    Font commentFont = graphics.createCommentFont();
    Font boldCommentFont = graphics.createCommentFont();
    boldCommentFont.setStyle(Font.BOLD);
    TableList list = new TableList(commentFont, new CellPadding(2, 0, 1, 1));
    TableCell spaceCell = new TableCell(new Phrase(" ", commentFont), Rectangle.NO_BORDER); //$NON-NLS-1$
    spaceCell.setPadding(0);

    list.addHeader(new Chunk(resources.getString("Sheet.Astrology.Frequency"), font), true); //$NON-NLS-1$
    list.addCell(spaceCell);
    list.addCell(spaceCell);
    list.addItem(resources.getString("Sheet.Astrology.Frequency.Weekly")); //$NON-NLS-1$
    list.addItem(resources.getString("Sheet.Astrology.Frequency.Daily")); //$NON-NLS-1$
    list.addItem(resources.getString("Sheet.Astrology.Frequency.Scene")); //$NON-NLS-1$
    list.addItem(resources.getString("Sheet.Astrology.Frequency.Anytime")); //$NON-NLS-1$
    list.addCell(spaceCell);
    list.addCell(spaceCell);

    PdfPTable table = new PdfPTable(new float[]{1f});
    table.addCell(new TableCell(list.getTable(), Rectangle.BOX));
    return table;
  }
}
