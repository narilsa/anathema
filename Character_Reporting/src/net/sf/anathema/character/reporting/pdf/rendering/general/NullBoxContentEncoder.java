package net.sf.anathema.character.reporting.pdf.rendering.general;

import com.itextpdf.text.DocumentException;
import net.sf.anathema.character.reporting.pdf.content.ReportSession;
import net.sf.anathema.character.reporting.pdf.rendering.extent.Bounds;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.ContentEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.graphics.SheetGraphics;

public class NullBoxContentEncoder implements ContentEncoder {

  private final String headerKey;

  public NullBoxContentEncoder() {
    this("Null"); //$NON-NLS-1$
  }

  public NullBoxContentEncoder(String headerKey) {
    this.headerKey = headerKey;
  }

  @Override
  public void encode(SheetGraphics graphics, ReportSession reportSession, Bounds bounds) throws DocumentException {
    // Nothing to do
  }

  @Override
  public String getHeader(ReportSession session) {
    return headerKey;
  }

  @Override
  public boolean hasContent(ReportSession session) {
    return true;
  }
}
