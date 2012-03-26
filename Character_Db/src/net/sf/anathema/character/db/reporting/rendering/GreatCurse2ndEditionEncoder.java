package net.sf.anathema.character.db.reporting.rendering;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import net.sf.anathema.character.db.reporting.content.Db2ndEditionGreatCurseContent;
import net.sf.anathema.character.reporting.pdf.content.ReportSession;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.virtueflaw.VirtueFlawBoxEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.extent.Bounds;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.AbstractContentEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.graphics.SheetGraphics;

import static net.sf.anathema.character.reporting.pdf.rendering.page.IVoidStateFormatConstants.REDUCED_LINE_HEIGHT;

public class GreatCurse2ndEditionEncoder extends AbstractContentEncoder<Db2ndEditionGreatCurseContent> {

  private VirtueFlawBoxEncoder traitEncoder = new VirtueFlawBoxEncoder();

  public GreatCurse2ndEditionEncoder() {
    super(Db2ndEditionGreatCurseContent.class);
  }

  @Override
  public void encode(SheetGraphics graphics, ReportSession reportSession, Bounds bounds) throws DocumentException {
    Db2ndEditionGreatCurseContent content = createContent(reportSession);
    Bounds textBounds = traitEncoder.encode(graphics, bounds, content.getLimitValue());
    Phrase phrase = new Phrase(content.getGreatCurseMessage(), graphics.createTableFont());
    graphics.createSimpleColumn(textBounds).withLeading(REDUCED_LINE_HEIGHT).andTextPart(phrase).encode();
  }
}
