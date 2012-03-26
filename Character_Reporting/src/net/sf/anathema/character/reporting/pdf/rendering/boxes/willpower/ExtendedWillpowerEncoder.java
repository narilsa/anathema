package net.sf.anathema.character.reporting.pdf.rendering.boxes.willpower;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import net.sf.anathema.character.reporting.pdf.content.ReportSession;
import net.sf.anathema.character.reporting.pdf.content.general.BulletList;
import net.sf.anathema.character.reporting.pdf.content.willpower.WillpowerContent;
import net.sf.anathema.character.reporting.pdf.rendering.extent.Bounds;
import net.sf.anathema.character.reporting.pdf.rendering.extent.Position;
import net.sf.anathema.character.reporting.pdf.rendering.general.ListUtils;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.AbstractContentEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.general.traits.PdfTraitEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.graphics.SheetGraphics;
import net.sf.anathema.character.reporting.pdf.rendering.page.IVoidStateFormatConstants;

public class ExtendedWillpowerEncoder extends AbstractContentEncoder<WillpowerContent> {

  public ExtendedWillpowerEncoder() {
    super(WillpowerContent.class);
  }

  public void encode(SheetGraphics graphics, ReportSession reportSession, Bounds bounds) throws DocumentException {
    float lineHeight = 1.1f * IVoidStateFormatConstants.COMMENT_FONT_SIZE;
    PdfTraitEncoder traitEncoder = PdfTraitEncoder.createLargeTraitEncoder();
    WillpowerContent content = createContent(reportSession);
    float padding = IVoidStateFormatConstants.PADDING / 2f;
    float width = bounds.width - 2f * padding;
    float leftX = bounds.x + padding;
    float height = bounds.height - padding;
    float topY = bounds.getMaxY();

    float entryHeight = traitEncoder.getTraitHeight();
    float yPosition = topY - entryHeight;
    traitEncoder.encodeDotsCenteredAndUngrouped(graphics, new Position(leftX, yPosition), width, content.getWillpowerValue(), 10);
    yPosition -= entryHeight;
    traitEncoder.encodeSquaresCenteredAndUngrouped(graphics, new Position(leftX, yPosition), width, 0, 10);
    height -= 2f * entryHeight;

    yPosition -= lineHeight;
    height -= lineHeight;

    float columnPadding = padding / 2f;
    float columnWidth = (width - columnPadding) / 2f;

    Bounds spendingBounds = new Bounds(leftX, yPosition - height, columnWidth, height);
    Phrase spendingPhrase = new Phrase("", graphics.createCommentFont());  //$NON-NLS-1$
    BulletList willpowerSpendingRules = content.getWillpowerSpendingRules();
    Chunk symbolChunk = graphics.createSymbolChunk();
    ListUtils.addBulletList(spendingPhrase, symbolChunk, willpowerSpendingRules.header, willpowerSpendingRules.items);
    spendingPhrase.add("\n" + content.getWillpowerSpendingNote() + "\n"); //$NON-NLS-1$
    graphics.createSimpleColumn(spendingBounds).withLeading(lineHeight).andTextPart(spendingPhrase).encode().getYLine();

    float centerX = leftX + columnWidth + columnPadding;
    Bounds regainingBounds = new Bounds(centerX, yPosition - height, columnWidth, height);
    Phrase regainingPhrase = new Phrase("", graphics.createCommentFont());   //$NON-NLS-1$
    BulletList willpowerRegainingRules = content.getWillpowerRegainingRules();
    ListUtils.addBulletList(regainingPhrase, symbolChunk, willpowerRegainingRules.header, willpowerRegainingRules.items);
    graphics.createSimpleColumn(regainingBounds).withLeading(lineHeight).andTextPart(regainingPhrase).encode().getYLine();
  }
}
