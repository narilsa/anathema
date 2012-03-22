package net.sf.anathema.character.impl.reporting;

import net.sf.anathema.character.model.ICharacter;
import net.sf.anathema.framework.IAnathemaModel;
import net.sf.anathema.framework.reporting.ReportException;
import net.sf.anathema.framework.reporting.pdf.PdfReportUtils;
import net.sf.anathema.framework.repository.IItem;
import net.sf.anathema.lib.resources.IResources;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.MultiColumnText;
import com.itextpdf.text.pdf.PdfWriter;

public class MagicFlatReport extends MagicReport {
	
	public MagicFlatReport(IResources resources, IAnathemaModel model) {
		super(resources, model, new MagicPartFactory(new PdfReportUtils(),
				new IMagicReportProperties() {

					@Override
					public int getStandardFontSize() {
						return 10;
					}

					@Override
					public int getTitleFontSize() {
						return 14;
					}

					@Override
					public int getGroupFontSize() {
						return 18;
					}

					@Override
					public int getDescriptionFirstLineIndent() {
						return 15;
					}

					@Override
					public int getTitleLeading() {
						return 25;
					}
			
		}));
	}
	
	@Override
	public String toString() {
	   return getResources().getString("MagicReport.Name"); //$NON-NLS-1$
	}

	public void performPrint(IItem item, Document document, PdfWriter writer) throws ReportException {
	    MultiColumnText columnText = new MultiColumnText(document.top() - document.bottom() - 15);
	    columnText.addRegularColumns(document.left(), document.right(), 20, 2);
	    ICharacter character = (ICharacter) item.getItemData();
	    try {
	      printCharms(columnText, character);
	      printSpells(columnText, character);
	      writeColumnText(document, columnText);
	    } catch (DocumentException e) {
	      throw new ReportException(e);
	    }
	}
	
	private void writeColumnText(Document document, MultiColumnText columnText) throws DocumentException {
		do {
			document.add(columnText);
		    columnText.nextColumn();
		} while (columnText.isOverflow());
    }
}
