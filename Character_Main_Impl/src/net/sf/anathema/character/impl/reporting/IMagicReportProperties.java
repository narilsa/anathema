package net.sf.anathema.character.impl.reporting;

import java.awt.Color;

public interface IMagicReportProperties {
	
	int getStandardFontSize();
	
	int getTitleFontSize();
	
	int getGroupFontSize();
	
	int getDescriptionFirstLineIndent();
	
	int getTitleLeading();
	
	String getFontFace();
	
	Color getTitleColor();
}
