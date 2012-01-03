package net.sf.anathema.character.reporting.pdf.rendering.boxes.essence;

import net.sf.anathema.character.reporting.pdf.content.BasicContent;
import net.sf.anathema.character.reporting.pdf.rendering.EncoderIds;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.AbstractBoxContentEncoderFactory;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.IBoxContentEncoder;
import net.sf.anathema.lib.resources.IResources;

public class SimpleEssenceBoxEncoderFactory extends AbstractBoxContentEncoderFactory {

  public SimpleEssenceBoxEncoderFactory() {
    super(EncoderIds.ESSENCE_SIMPLE);
  }

  @Override
  public IBoxContentEncoder create(IResources resources, BasicContent content) {
    return new SimpleEssenceBoxContentEncoder();
  }

  @Override
  public boolean supports(BasicContent content) {
    return content.isExalt();
  }
}