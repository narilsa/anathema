package net.sf.anathema.character.reporting.pdf.rendering.boxes.virtues;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import net.sf.anathema.character.reporting.pdf.content.ReportSession;
import net.sf.anathema.character.reporting.pdf.content.general.NamedValue;
import net.sf.anathema.character.reporting.pdf.content.virtues.VirtueContent;
import net.sf.anathema.character.reporting.pdf.rendering.extent.Bounds;
import net.sf.anathema.character.reporting.pdf.rendering.extent.Position;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.AbstractContentEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.general.traits.PdfTraitEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.graphics.SheetGraphics;
import net.sf.anathema.character.reporting.pdf.rendering.page.IVoidStateFormatConstants;

public class VirtueEncoder extends AbstractContentEncoder<VirtueContent> {

  public VirtueEncoder() {
    super(VirtueContent.class);
  }

  public void encode(SheetGraphics graphics, ReportSession session, Bounds bounds) throws DocumentException {
    VirtueContent virtueContent = createContent(session);
    encodeVirtues(graphics, bounds, virtueContent);
  }

  private void encodeVirtues(SheetGraphics graphics, Bounds bounds, VirtueContent virtueContent) {
    float virtuePadding = bounds.width / 8;
    float leftVirtueX = bounds.x + virtuePadding / 2;
    float width = (bounds.width - 2 * virtuePadding) / 2;
    float rightVirtueX = (int) (bounds.x + width + virtuePadding * 1.5);
    float upperY = (int) bounds.getMaxY();
    float centerY = (int) bounds.getCenterY();
    encodeVirtue(graphics, virtueContent.getUpperLeftVirtue(), new Position(leftVirtueX, upperY), width);
    encodeVirtue(graphics, virtueContent.getUpperRightVirtue(), new Position(rightVirtueX, upperY), width);
    encodeVirtue(graphics, virtueContent.getLowerLeftVirtue(), new Position(leftVirtueX, centerY), width);
    encodeVirtue(graphics, virtueContent.getLowerRightVirtue(), new Position(rightVirtueX, centerY), width);
  }

  private void encodeVirtue(SheetGraphics graphics, NamedValue trait, Position position, float width) {
    PdfTraitEncoder traitEncoder = PdfTraitEncoder.createSmallTraitEncoder();
    float yPosition = position.y;
    yPosition -= IVoidStateFormatConstants.LINE_HEIGHT - 3;
    String label = trait.getLabel();
    float labelX = position.x + width / 2;
    graphics.drawText(label, new Position(labelX, yPosition), PdfContentByte.ALIGN_CENTER);
    yPosition -= traitEncoder.getTraitHeight() - 1;
    Position traitPosition = new Position(position.x, yPosition);
    int value = trait.getValue();
    traitEncoder.encodeDotsCenteredAndUngrouped(graphics, traitPosition, width, value, 5);
    yPosition -= traitEncoder.getTraitHeight() - 1;
    traitEncoder.encodeSquaresCenteredAndUngrouped(graphics, new Position(position.x, yPosition), width, 0, 5);
  }
}
