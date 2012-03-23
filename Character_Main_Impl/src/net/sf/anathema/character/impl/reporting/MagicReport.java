package net.sf.anathema.character.impl.reporting;

import com.google.common.base.Joiner;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.MultiColumnText;
import com.itextpdf.text.pdf.PdfPTable;
import net.sf.anathema.character.generic.framework.magic.stringbuilder.ScreenDisplayInfoStringBuilder;
import net.sf.anathema.character.generic.framework.magic.stringbuilder.source.MagicSourceStringBuilder;
import net.sf.anathema.character.generic.framework.magic.stringbuilder.type.VerboseCharmTypeStringBuilder;
import net.sf.anathema.character.generic.framework.magic.view.CharmDescriptionProviderExtractor;
import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.generic.magic.IMagic;
import net.sf.anathema.character.generic.magic.ISpell;
import net.sf.anathema.character.generic.magic.description.MagicDescription;
import net.sf.anathema.character.impl.generic.GenericCharacter;
import net.sf.anathema.character.impl.model.advance.ExperiencePointManagement;
import net.sf.anathema.character.model.ICharacter;
import net.sf.anathema.character.reporting.pdf.content.stats.magic.CharmStats;
import net.sf.anathema.character.reporting.pdf.content.stats.magic.SpellStats;
import net.sf.anathema.framework.IAnathemaModel;
import net.sf.anathema.framework.reporting.pdf.AbstractPdfReport;
import net.sf.anathema.framework.repository.IItem;
import net.sf.anathema.lib.resources.IResources;

import static java.text.MessageFormat.format;

public abstract class MagicReport extends AbstractPdfReport {

  private final IResources resources;
  private final IAnathemaModel model;
  private final MagicPartFactory partFactory;

  public MagicReport(IResources resources, IAnathemaModel model, MagicPartFactory partFactory) {
    this.resources = resources;
    this.model = model;
    this.partFactory = partFactory;
  }
  
  protected IResources getResources() {
	  return resources;
  }
  
  protected MagicPartFactory getPartFactory() {
	  return partFactory;
  }
	  
  protected void printSpell(MultiColumnText columnText, ICharacter character,
		  ISpell spell, boolean includeSources) throws DocumentException {
    SpellStats spellStats = createSpellStats(spell);
	addMagicName(spell, columnText);
    addSpellCost(spell, columnText);
    addSpellTarget(spellStats, columnText);
    addCharmDescription(spell, columnText);
    if (includeSources && hasCharmDescription(spell)) {
    	addMagicSources(spell, columnText);
    }
  }
	  
  protected void printCharm(MultiColumnText columnText, ICharacter character,
			  ICharm charm, boolean includeSources) throws DocumentException {
	CharmStats charmStats = createCharmStats(character, charm);
    addMagicName(charm, columnText);
    addCharmData(charmStats, charm, columnText);
    addCharmDescription(charm, columnText);
    if (includeSources && hasCharmDescription(charm)) {
      addMagicSources(charm, columnText);
    }
  }

  private void addSpellCost(ISpell charm, MultiColumnText columnText) throws DocumentException {
    String costsLabel = resources.getString("MagicReport.Costs.Label") + ": ";
    String costsValue = new ScreenDisplayInfoStringBuilder(resources).createCostString(charm);
    columnText.addElement(partFactory.createDataPhrase(costsLabel, costsValue));
  }

  private void addSpellTarget(SpellStats spellStats, MultiColumnText columnText) throws DocumentException {
    String targetLabel = resources.getString("MagicReport.Target.Label") + ": ";
    String target = Joiner.on(", ").join(spellStats.getDetailStrings(resources));
    columnText.addElement(partFactory.createDataPhrase(targetLabel, target));
  }
  
  private void addMagicName(IMagic magic, MultiColumnText columnText) throws DocumentException {
    String charmName = resources.getString(magic.getId());
    columnText.addElement(partFactory.createCharmTitle(charmName));
  }

  private void addCharmData(CharmStats charmStats, ICharm charm, MultiColumnText columnText) throws DocumentException {
    PdfPTable table = partFactory.createDataTable();
    addCostsCell(charm, table);
    addTypeCell(charm, table);
    addKeywordsRow(charmStats, table);
    addDurationRow(charmStats, table);
    columnText.addElement(table);
  }

  private void addCostsCell(ICharm charm, PdfPTable table) {
    String costsLabel = resources.getString("MagicReport.Costs.Label") + ": ";
    String costsValue = new ScreenDisplayInfoStringBuilder(resources).createCostString(charm);
    table.addCell(partFactory.createDataCell(costsLabel, costsValue));
  }

  private void addTypeCell(ICharm charm, PdfPTable table) {
    String typeLabel = resources.getString("MagicReport.Type.Label") + ": ";
    String typeValue = new VerboseCharmTypeStringBuilder(resources).createTypeString(charm.getCharmTypeModel());
    table.addCell(partFactory.createDataCell(typeLabel, typeValue));
  }

  private void addKeywordsRow(CharmStats charmStats, PdfPTable table) {
    String keywords = Joiner.on(", ").join(charmStats.getDetailStrings(resources));
    String keywordsLabel = resources.getString("MagicReport.Keywords.Label") + ": ";
    table.addCell(partFactory.createDoubleDataCell(keywordsLabel, keywords));
  }

  private void addDurationRow(CharmStats charmStats, PdfPTable table) {
    String durationLabel = resources.getString("MagicReport.Duration.Label") + ": ";
    String durationString = charmStats.getDurationString(resources);
    table.addCell(partFactory.createDoubleDataCell(durationLabel, durationString));
  }
  
  private boolean hasCharmDescription(IMagic magic) {
	  return !getCharmDescription(magic).isEmpty();
  }

  private void addCharmDescription(IMagic magic, MultiColumnText columnText) throws DocumentException {
    MagicDescription charmDescription = getCharmDescription(magic);
    if (charmDescription.isEmpty()) {
      addMagicSources(magic, columnText);
    }
    for (String paragraph : charmDescription.getParagraphs()) {
      columnText.addElement(partFactory.createDescriptionParagraph(paragraph));
    }
  }
  
  private void addMagicSources(IMagic magic, MultiColumnText columnText) throws DocumentException {
	  String sourceString = new MagicSourceStringBuilder<IMagic>(resources).createSourceString(magic);
      String sourceReference = resources.getString("MagicReport.See.Source", sourceString);
      columnText.addElement(partFactory.createDescriptionParagraph(sourceReference));
  }

  protected CharmStats createCharmStats(ICharacter character, ICharm charm) {
    return new CharmStats(charm, createGenericCharacter(character));
  }

  protected SpellStats createSpellStats(ISpell spell) {
    return new SpellStats(spell);
  }
  
  protected String getSpellGroupName(ISpell spell) {
	SpellStats spellStats = createSpellStats(spell);
	return format("{0} {1}", spellStats.getType(resources), spellStats.getGroupName(resources));
  }

  private GenericCharacter createGenericCharacter(ICharacter character) {
    return new GenericCharacter(character.getStatistics(), new ExperiencePointManagement(character.getStatistics()));
  }

  private MagicDescription getCharmDescription(IMagic magic) {
    return CharmDescriptionProviderExtractor.CreateFor(model, resources).getCharmDescription(magic);
  }

  public boolean supports(IItem item) {
    if (item == null || !(item.getItemData() instanceof ICharacter)) {
      return false;
    }
    ICharacter character = (ICharacter) item.getItemData();
    if (!character.hasStatistics()) {
      return false;
    }
    return getCurrentCharms(character).length > 0;
  }

  protected ISpell[] getCurrentSpells(ICharacter character) {
    return character.getStatistics().getSpells().getLearnedSpells(character.getStatistics().isExperienced());
  }

  protected ICharm[] getCurrentCharms(ICharacter character) {
    return character.getStatistics().getCharms().getLearnedCharms(character.getStatistics().isExperienced());
  }
}
