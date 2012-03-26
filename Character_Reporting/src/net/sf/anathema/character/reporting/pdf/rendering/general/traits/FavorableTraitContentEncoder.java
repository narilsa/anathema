package net.sf.anathema.character.reporting.pdf.rendering.general.traits;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import net.sf.anathema.character.generic.character.IGenericTraitCollection;
import net.sf.anathema.character.generic.traits.IFavorableGenericTrait;
import net.sf.anathema.character.generic.traits.ITraitType;
import net.sf.anathema.character.generic.traits.groups.IIdentifiedTraitTypeGroup;
import net.sf.anathema.character.reporting.pdf.content.ReportSession;
import net.sf.anathema.character.reporting.pdf.content.traits.FavorableTraitContent;
import net.sf.anathema.character.reporting.pdf.rendering.extent.Bounds;
import net.sf.anathema.character.reporting.pdf.rendering.extent.Position;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.AbstractContentEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.graphics.SheetGraphics;
import net.sf.anathema.character.reporting.pdf.rendering.page.IVoidStateFormatConstants;

import java.util.ArrayList;
import java.util.List;

public class FavorableTraitContentEncoder<C extends FavorableTraitContent> extends AbstractContentEncoder<C> {

  private final List<INamedTraitEncoder> namedTraitEncoders = new ArrayList<INamedTraitEncoder>();
  private final PdfTraitEncoder traitEncoder = PdfTraitEncoder.createSmallTraitEncoder();

  public FavorableTraitContentEncoder(Class<C> contentClass) {
    super(contentClass);
  }

  public final void addNamedTraitEncoder(INamedTraitEncoder encoder) {
    namedTraitEncoders.add(encoder);
  }

  public PdfTraitEncoder getTraitEncoder() {
    return traitEncoder;
  }

  public void encode(SheetGraphics graphics, ReportSession reportSession, Bounds bounds) throws DocumentException {
    FavorableTraitContent content = createContent(reportSession);
    Position position = new Position(bounds.getMinX(), bounds.getMaxY());
    float width = bounds.width;
    float bottom = bounds.getMinY() + IVoidStateFormatConstants.TEXT_PADDING;
    int nExcellencies = content.getExcellencies().length;
    if (nExcellencies > 0) {
      bottom += encodeExcellencyCommentText(graphics, content, position, bottom);
    }
    if (!content.getMarkedTraitTypes().isEmpty()) {
      bottom += encodeMarkerCommentText(graphics, content, position, bottom);
    }
    float yPosition = encodeTraitGroups(graphics, content, position, width);
    float height = yPosition - bottom;
    for (INamedTraitEncoder encoder : namedTraitEncoders) {
      yPosition -= IVoidStateFormatConstants.LINE_HEIGHT;
      yPosition -= encoder.encode(graphics, reportSession, new Position(position.x, yPosition), width, height);
      height -= IVoidStateFormatConstants.LINE_HEIGHT;
    }
  }

  private float encodeTraitGroups(SheetGraphics graphics, FavorableTraitContent content, Position position, float width) {
    IIdentifiedTraitTypeGroup[] groups = content.getIdentifiedTraitTypeGroups();
    float yPosition = position.y;
    for (IIdentifiedTraitTypeGroup group : groups) {
      Position groupPosition = new Position(position.x, yPosition);
      yPosition -= encodeTraitGroup(graphics, content, group, groupPosition, width);
      yPosition -= IVoidStateFormatConstants.TEXT_PADDING;
    }
    return yPosition;
  }

  private float encodeTraitGroup(SheetGraphics graphics, FavorableTraitContent content, IIdentifiedTraitTypeGroup group, Position position, float width) {
    float height = 0;
    float groupLabelWidth = IVoidStateFormatConstants.LINE_HEIGHT + IVoidStateFormatConstants.TEXT_PADDING;
    float traitX = position.x + groupLabelWidth;
    ITraitType[] traitTypes = group.getAllGroupTypes();
    float groupLabelX = position.x + 4;
    float markerX = groupLabelX + IVoidStateFormatConstants.TEXT_PADDING;
    for (int index = 0; index < traitTypes.length; index++) {
      ITraitType traitType = traitTypes[index];
      float yPosition = position.y - (index + 1) * traitEncoder.getTraitHeight();
      if (content.getMarkedTraitTypes().contains(traitType)) {
        encodeMarker(graphics, new Position(markerX, yPosition + 1));
      }
      IGenericTraitCollection traitCollection = content.getTraitCollection();
      IFavorableGenericTrait trait = traitCollection.getFavorableTrait(traitType);
      String label = content.getTraitLabel(traitType);
      if (content.shouldShowExcellencies()) {
        boolean[] excellencyLearned = content.hasExcellenciesLearned(traitType);
        height += encodeFavorableTrait(graphics, content, label, trait, excellencyLearned, new Position(traitX, yPosition), width - groupLabelWidth);
      } else {
        height += encodeFavorableTrait(graphics, content, label, trait, new Position(traitX, yPosition), width - groupLabelWidth);
      }
    }
    Position groupLabelPosition = new Position(groupLabelX, position.y - height / 2f);
    addGroupLabel(graphics, content, group, groupLabelPosition);
    return height;
  }

  private void addGroupLabel(SheetGraphics graphics, FavorableTraitContent content, IIdentifiedTraitTypeGroup group, Position position) {
    if (content.hasGroupLabel()) {
      return;
    }
    String groupLabel = content.getGroupLabel(group.getGroupId());
    graphics.drawVerticalText(groupLabel, position, PdfContentByte.ALIGN_CENTER);
  }

  private float encodeFavorableTrait(SheetGraphics graphics, FavorableTraitContent content, String label, IFavorableGenericTrait trait, Position position, float width) {
    int value = trait.getCurrentValue();
    boolean favored = trait.isCasteOrFavored();
    return traitEncoder.encodeWithTextAndRectangle(graphics, label, position, width, value, favored, content.getTraitMax());
  }

  private float encodeFavorableTrait(SheetGraphics graphics, FavorableTraitContent content, String label, IFavorableGenericTrait trait, boolean[] excellencyLearned, Position position, float width) {
    int value = trait.getCurrentValue();
    boolean favored = trait.isCasteOrFavored();
    return traitEncoder.encodeWithExcellencies(graphics, label, position, width, value, favored, excellencyLearned, content.getTraitMax());
  }

  private void encodeMarker(SheetGraphics graphics, Position markerPosition) {
    PdfContentByte directContent = graphics.getDirectContent();
    directContent.setLineWidth(1.0f);
    directContent.moveTo(markerPosition.x, markerPosition.y + 2);
    directContent.lineTo(markerPosition.x + 4, markerPosition.y + 2);
    directContent.moveTo(markerPosition.x + 2, markerPosition.y);
    directContent.lineTo(markerPosition.x + 2, markerPosition.y + 4);
    directContent.stroke();
  }

  private float encodeMarkerCommentText(SheetGraphics graphics, FavorableTraitContent content, Position position, float yPosition) {
    encodeMarker(graphics, new Position(position.x, yPosition));
    String mobilityPenaltyText = content.getMobilityPenaltyText();
    Position commentPosition = new Position(position.x + 5, yPosition);
    graphics.drawComment(mobilityPenaltyText, commentPosition, PdfContentByte.ALIGN_LEFT);
    return 7;
  }

  private float encodeExcellencyCommentText(SheetGraphics graphics, FavorableTraitContent content, Position position, float yPosition) {
    String mobilityPenaltyText = content.getExcellenciesComment();
    Position commentPosition = new Position(position.x, yPosition);
    graphics.drawComment(mobilityPenaltyText, commentPosition, PdfContentByte.ALIGN_LEFT);
    return 7;
  }
}
