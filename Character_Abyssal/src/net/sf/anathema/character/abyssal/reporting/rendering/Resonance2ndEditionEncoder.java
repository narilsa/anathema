package net.sf.anathema.character.abyssal.reporting.rendering;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import net.sf.anathema.character.abyssal.reporting.content.Abyssal2ndResonanceContent;
import net.sf.anathema.character.reporting.pdf.content.ReportSession;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.virtueflaw.VirtueFlawBoxEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.extent.Bounds;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.AbstractContentEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.graphics.SheetGraphics;

import static net.sf.anathema.character.reporting.pdf.rendering.page.IVoidStateFormatConstants.REDUCED_LINE_HEIGHT;

public class Resonance2ndEditionEncoder extends AbstractContentEncoder<Abyssal2ndResonanceContent> {

  private final VirtueFlawBoxEncoder traitEncoder = new VirtueFlawBoxEncoder();

  public Resonance2ndEditionEncoder() {
    super(Abyssal2ndResonanceContent.class);
  }

  public void encode(SheetGraphics graphics, ReportSession reportSession, Bounds bounds) throws DocumentException {
    Abyssal2ndResonanceContent content = createContent(reportSession);
    Bounds textBounds = traitEncoder.encode(graphics, bounds, content.getLimitValue());
    Phrase phrase = new Phrase("", createDefaultFont(graphics)); //$NON-NLS-1$
    phrase.add(new Chunk(content.getFlawedVirtueLabel(), createNameFont(graphics)));
    if (content.isComplete()) {
      phrase.add(new Chunk(content.getFlawedVirtue()));
      phrase.add(".\n");  //$NON-NLS-1$
    } else {
      phrase.add(new Chunk("                                          ", createUndefinedFont(graphics))); //$NON-NLS-1$
      phrase.add(".\n");
    }
    phrase.add(content.getResonanceReference());
    graphics.createSimpleColumn(textBounds).withLeading(REDUCED_LINE_HEIGHT).andTextPart(phrase).encode();
  }

  private Font createDefaultFont(SheetGraphics graphics) {
    return graphics.createTableFont();
  }

  private Font createNameFont(SheetGraphics graphics) {
    Font newFont = createDefaultFont(graphics);
    newFont.setStyle(Font.BOLD);
    return newFont;
  }

  private Font createUndefinedFont(SheetGraphics graphics) {
    Font newFont = createDefaultFont(graphics);
    newFont.setStyle(Font.UNDERLINE);
    return newFont;
  }
}
