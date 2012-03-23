package net.sf.anathema.character.impl.reporting;

import java.awt.Color;

import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.generic.magic.ISpell;
import net.sf.anathema.character.model.ICharacter;
import net.sf.anathema.character.reporting.pdf.content.stats.magic.CharmStats;
import net.sf.anathema.framework.IAnathemaModel;
import net.sf.anathema.framework.reporting.ReportException;
import net.sf.anathema.framework.reporting.pdf.PdfReportUtils;
import net.sf.anathema.framework.repository.IItem;
import net.sf.anathema.lib.resources.IResources;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.MultiColumnText;
import com.itextpdf.text.pdf.PdfWriter;

public class MagicTextReport extends MagicReport {
	
	public MagicTextReport(IResources resources, IAnathemaModel model) {
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
					
					@Override
					public Color getTitleColor() {
						return null;
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
	
	private void printSpells(MultiColumnText columnText, ICharacter character) throws DocumentException {
	  String currentGroup = "";
	  for (ISpell spell : getCurrentSpells(character)) {
	    currentGroup = printSpellWithGroup(columnText, character, spell, currentGroup);
	  }
	}
	
	private void printCharms(MultiColumnText columnText, ICharacter character) throws DocumentException {
	  String currentGroup = "";
	  for (ICharm charm : getCurrentCharms(character)) {
	    currentGroup = printCharmWithGroup(columnText, character, charm, currentGroup);
	  }
	}
	
	private String printSpellWithGroup(MultiColumnText columnText, ICharacter character,
			ISpell spell, String currentGroup) throws DocumentException {
	    String nextGroupName = getSpellGroupName(spell);
	    if (!currentGroup.equals(nextGroupName)) {
	        currentGroup = nextGroupName;
	        columnText.addElement(getPartFactory().createGroupTitle(currentGroup));
	    }
	    printSpell(columnText, character, spell, false);
	    return currentGroup;
	}
	
	private String printCharmWithGroup(MultiColumnText columnText, ICharacter character,
			ICharm charm, String currentGroup) throws DocumentException {
		CharmStats charmStats = createCharmStats(character, charm);
		if (!currentGroup.equals(charmStats.getGroupName(getResources()))) {
		      currentGroup = charmStats.getGroupName(getResources());
		      columnText.addElement(getPartFactory().createGroupTitle(currentGroup));
		}
		printCharm(columnText, character, charm, false);
		return currentGroup;
	}
	
	private void writeColumnText(Document document, MultiColumnText columnText) throws DocumentException {
		do {
			document.add(columnText);
		    columnText.nextColumn();
		} while (columnText.isOverflow());
    }
}
