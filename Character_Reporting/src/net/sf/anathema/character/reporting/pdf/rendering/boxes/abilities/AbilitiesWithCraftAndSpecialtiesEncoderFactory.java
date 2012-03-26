package net.sf.anathema.character.reporting.pdf.rendering.boxes.abilities;

import net.sf.anathema.character.reporting.pdf.content.BasicContent;
import net.sf.anathema.character.reporting.pdf.content.abilities.AbilitiesContent;
import net.sf.anathema.character.reporting.pdf.rendering.EncoderIds;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.GlobalEncoderFactory;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.RegisteredEncoderFactory;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.ContentEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.general.traits.FavorableTraitContentEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.general.traits.PdfTraitEncoder;
import net.sf.anathema.lib.resources.IResources;

@RegisteredEncoderFactory
public class AbilitiesWithCraftAndSpecialtiesEncoderFactory extends GlobalEncoderFactory {

  public AbilitiesWithCraftAndSpecialtiesEncoderFactory() {
    super(EncoderIds.ABILITIES_WITH_CRAFTS_AND_SPECIALTIES);
  }

  @Override
  public ContentEncoder create(IResources resources, BasicContent content) {
    FavorableTraitContentEncoder<AbilitiesContent> encoder = new FavorableTraitContentEncoder<AbilitiesContent>(AbilitiesContent.class);
    PdfTraitEncoder traitEncoder = encoder.getTraitEncoder();
    encoder.addNamedTraitEncoder(new CraftEncoder(resources, traitEncoder, 9));
    encoder.addNamedTraitEncoder(new SpecialtiesEncoder(resources, traitEncoder, 9));
    return encoder;
  }
}
