package net.sf.anathema.character.ghost.reporting.rendering;

import com.itextpdf.text.DocumentException;
import net.sf.anathema.character.ghost.reporting.content.GhostFetterContent;
import net.sf.anathema.character.reporting.pdf.content.ReportSession;
import net.sf.anathema.character.reporting.pdf.content.general.NamedValue;
import net.sf.anathema.character.reporting.pdf.rendering.extent.Bounds;
import net.sf.anathema.character.reporting.pdf.rendering.extent.Position;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.AbstractContentEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.general.traits.PdfTraitEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.graphics.SheetGraphics;

public class FetterEncoder extends AbstractContentEncoder<GhostFetterContent> {

  private final PdfTraitEncoder traitEncoder = PdfTraitEncoder.createSmallTraitEncoder();

  public FetterEncoder() {
    super(GhostFetterContent.class);
  }

  public void encode(SheetGraphics graphics, ReportSession reportSession, Bounds bounds) throws DocumentException {
    GhostFetterContent content = createContent(reportSession);
    float groupSpacing = traitEncoder.getTraitHeight() / 2;
    float x = bounds.x;
    float y = bounds.getMaxY() - 2 * groupSpacing;
    float width = bounds.getWidth() * 1 / 2;
    for (NamedValue fetter : content.getFetters()) {
      Position position = new Position(x, y);
      y -= traitEncoder.encode(graphics, fetter, position, width, content.getTraitMaximum());
      if (y < bounds.getMinY()) {
        y = bounds.getMaxY() - 2 * groupSpacing;
        x += bounds.width / 2;
      }
    }
  }
}
