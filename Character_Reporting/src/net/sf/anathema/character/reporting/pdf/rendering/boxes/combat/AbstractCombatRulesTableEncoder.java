package net.sf.anathema.character.reporting.pdf.rendering.boxes.combat;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import net.sf.anathema.character.reporting.pdf.content.ReportSession;
import net.sf.anathema.character.reporting.pdf.content.general.QualifiedText;
import net.sf.anathema.character.reporting.pdf.content.general.TextType;
import net.sf.anathema.character.reporting.pdf.rendering.extent.Bounds;
import net.sf.anathema.character.reporting.pdf.rendering.general.table.AbstractTableEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.graphics.SheetGraphics;
import net.sf.anathema.character.reporting.pdf.rendering.graphics.TableCell;

public abstract class AbstractCombatRulesTableEncoder extends AbstractTableEncoder<ReportSession> {

  @Override
  protected final PdfPTable createTable(SheetGraphics graphics, ReportSession session, Bounds bounds) {
    float cellPadding = 0.05f;
    PdfPTable table = new PdfPTable(new float[]{1f, cellPadding, 1.1f, cellPadding, 1f});
    addFirstCell(graphics, session, table);
    table.addCell(createSpaceCell(graphics));
    addSecondCell(graphics, session, table);
    table.addCell(createSpaceCell(graphics));
    addThirdCell(graphics, session, table);
    return table;
  }

  protected abstract void addFirstCell(SheetGraphics graphics, ReportSession reportSession, PdfPTable table);

  protected abstract void addSecondCell(SheetGraphics graphics, ReportSession reportSession, PdfPTable table);

  protected abstract void addThirdCell(SheetGraphics graphics, ReportSession reportSession, PdfPTable table);

  private PdfPCell createSpaceCell(SheetGraphics graphics) {
    return new TableCell(new Phrase(" ", graphics.createTextFont()), Rectangle.NO_BORDER); //$NON-NLS-1$
  }

  protected PdfPCell createContentCell(Phrase phrase) {
    return new TableCell(phrase, Rectangle.BOX);
  }

  protected void addAsCell(SheetGraphics graphics, PdfPTable table, QualifiedText[] textChunks) {
    Phrase knockdownAndStunningPhrase = new Phrase("");
    for (QualifiedText text : textChunks) {
      knockdownAndStunningPhrase.add(createChunk(graphics, text));
    }
    table.addCell(createContentCell(knockdownAndStunningPhrase));
  }

  private final Chunk createChunk(SheetGraphics graphics, QualifiedText text) {
    Font font = text.type == TextType.Comment ? graphics.createCommentFont() : graphics.createTextFont();
    return new Chunk(text.text, font);
  }
}
