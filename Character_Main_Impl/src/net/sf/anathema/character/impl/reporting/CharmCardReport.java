package net.sf.anathema.character.impl.reporting;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.MultiColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.generic.magic.IMagic;
import net.sf.anathema.character.generic.magic.ISpell;
import net.sf.anathema.character.model.ICharacter;
import net.sf.anathema.framework.IAnathemaModel;
import net.sf.anathema.framework.reporting.ReportException;
import net.sf.anathema.framework.reporting.pdf.PdfReportUtils;
import net.sf.anathema.framework.repository.IItem;
import net.sf.anathema.lib.resources.IResources;

public class CharmCardReport extends MagicReport {
	  
	  private final int NUM_COLUMNS = 3;
	  private final int NUM_ROWS = 3;
	  private final int MARGIN = 15;
	  private final int GUTTER = 20;

	  public CharmCardReport(IResources resources, IAnathemaModel model) {
	    super(resources, model, new MagicPartFactory(new PdfReportUtils(),
	    		new IMagicReportProperties() {

					@Override
					public int getStandardFontSize() {
						return 6;
					}

					@Override
					public int getTitleFontSize() {
						return 9;
					}

					@Override
					public int getGroupFontSize() {
						return 10;
					}

					@Override
					public int getDescriptionFirstLineIndent() {
						return 5;
					}

					@Override
					public int getTitleLeading() {
						return 5;
					}
					
					@Override
					public Color getTitleColor() {
						return null;
					}
	    }));
	  }

	  @Override
	  public String toString() {
	    return getResources().getString("CharmCardReport.Name"); //$NON-NLS-1$
	  }

	  public void performPrint(IItem item, Document document, PdfWriter writer) throws ReportException {
		ICharacter character = (ICharacter) item.getItemData();
		PdfContentByte directContent = writer.getDirectContent();
	    
	    try {
	    List<IMagic> magicList = new ArrayList<IMagic>();
	    Collections.addAll(magicList, getCurrentCharms(character));
	    Collections.addAll(magicList, getCurrentSpells(character));
	    int positionIndex = 0;
	    MultiColumnText columnText = null;
	    for (IMagic magic : magicList) {
	    	int row = positionIndex / NUM_COLUMNS;
	    	int column = positionIndex % NUM_COLUMNS;
	    	
	    	if (column == 0) {
	    		columnText = getColumnText(document, row, column);
	    	}
	    	else {
    			columnText.nextColumn();
	    	}
	    	encodeMagic(columnText, character, magic);
	    	document.add(columnText);
	    	drawBox(document, directContent, row, column); 
	    	positionIndex++;
	    	if (positionIndex == NUM_ROWS * NUM_COLUMNS) {
	    		document.newPage();
	    		positionIndex = 0;
	    	}
	    }
	    
	    } catch (DocumentException e) {
	    	
	    }
	  }
	  
	  private void encodeMagic(final MultiColumnText columnText, final ICharacter character, IMagic magic) throws DocumentException {
		  // would prefer to use visitor, but no way to percolate the exception
		  if (magic instanceof ICharm) {
			  ICharm charm = (ICharm)magic;
			  printCharm(columnText, character, charm, true);
		  }
		  if (magic instanceof ISpell) {
			  ISpell spell = (ISpell)magic;
			  printSpell(columnText, character, spell, true);
		  }
		  character.getStatistics().getCharacterTemplate().getPresentationProperties().getCharmPresentationProperties().getColor();
	  }
	  
	  private MultiColumnText getColumnText(Document document, int row, int column) {
		  MultiColumnText columnText = new MultiColumnText(getDocumentTop(document)
				  - getRowHeight(document) * row - MARGIN, getRowHeight(document));
		  columnText.addRegularColumns(document.left(), document.right(), GUTTER, NUM_COLUMNS);
		  return columnText;
	  }
	  
	  private void drawBox(Document document, PdfContentByte directContent, int row, int column) {
		  float upperLeftX = document.left() + column * (getColumnWidth(document)) - MARGIN/2;
		  float upperLeftY = getDocumentTop(document) - (row + 1) * getRowHeight(document);
		  float width = getColumnWidth(document);
		  float height = getRowHeight(document);
		  directContent.setColorStroke(new BaseColor(Color.LIGHT_GRAY));
		  directContent.rectangle(upperLeftX, upperLeftY, width, height);
		  directContent.closePathStroke();
	  }
	  
	  private float getDocumentTop(Document document) {
		  return document.top() - MARGIN;
	  }
	  
	  private float getDocumentHeight(Document document) {
		  return getDocumentTop(document) - document.bottom();
	  }
	  
	  private float getRowHeight(Document document) {
		  return getDocumentHeight(document) / NUM_ROWS;
	  }
	  
	  private float getColumnTextWidth(Document document) {
		  return (document.right() - document.left() - GUTTER * (NUM_COLUMNS - 1)) / NUM_COLUMNS; 
	  }
	  
	  private float getColumnWidth(Document document) {
		  return getColumnTextWidth(document) + GUTTER;
	  }
}
