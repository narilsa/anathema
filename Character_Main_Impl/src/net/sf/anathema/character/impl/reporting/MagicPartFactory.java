package net.sf.anathema.character.impl.reporting;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import net.sf.anathema.framework.reporting.pdf.PdfReportUtils;

import static com.itextpdf.text.Font.BOLD;
import static com.itextpdf.text.Font.NORMAL;

public final class MagicPartFactory {

  private IMagicReportProperties properties;

  public MagicPartFactory(IMagicReportProperties properties) {
    this.properties = properties;
  }

  public Element createFirstGroupTitle(String groupTitle) {
    Paragraph paragraph = createGroupTitle(groupTitle);
    paragraph.setLeading(0f);
    return paragraph;
  }

  public Paragraph createGroupTitle(String groupTitle) {
    Chunk title = new Chunk(groupTitle, PdfReportUtils.createFont(properties.getFontFace(),
    		properties.getGroupFontSize(), BOLD));
    Paragraph paragraph = new Paragraph(properties.getTitleLeading(), title);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    return paragraph;
  }

  public Paragraph createCharmTitle(String charmName) {
    Chunk title = new Chunk(charmName, PdfReportUtils.createFont(properties.getFontFace(),
    		properties.getTitleFontSize(), BOLD));
    if (properties.getTitleColor() != null) {
    	title.setBackground(new BaseColor(properties.getTitleColor()));
    }
    return new Paragraph(properties.getTitleLeading(), title);
  }

  public Paragraph createDescriptionParagraph(String text) {
    Chunk chunk = new Chunk(text, PdfReportUtils.createFont(properties.getFontFace(),
    		properties.getStandardFontSize(), NORMAL));
    Paragraph paragraph = new Paragraph(chunk);
    paragraph.setFirstLineIndent(properties.getDescriptionFirstLineIndent());
    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
    return paragraph;
  }

  public PdfPTable createDataTable() {
    float[] columnWidths = new float[]{2f, 2f};
    PdfPTable table = new PdfPTable(columnWidths);
    table.setWidthPercentage(100);
    return table;
  }

  public PdfPCell createDataCell(String title, String value) {
    Phrase phrase = createDataPhrase(title, value);
    PdfPCell cell = new PdfPCell(phrase);
    cell.setBorder(Rectangle.NO_BORDER);
    return cell;
  }

  public Phrase createDataPhrase(String title, String value) {
    Phrase phrase = new Phrase();
    phrase.add(new Chunk(title, PdfReportUtils.createFont(properties.getFontFace(),
    		properties.getStandardFontSize(), BOLD)));
    phrase.add(new Chunk(value, PdfReportUtils.createFont(properties.getFontFace(),
    		properties.getStandardFontSize(), NORMAL)));
    return phrase;
  }

  public PdfPCell createDoubleDataCell(String title, String value) {
    PdfPCell dataCell = createDataCell(title, value);
    dataCell.setColspan(2);
    return dataCell;
  }
}
