package com.experiment.billing.utils;

import com.experiment.billing.model.components.Trip;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.property.TextAlignment;
import static com.experiment.billing.constants.Constants.FROM_TO;

public class TripUtils {
  public static Cell getFromToCell(final Trip tripDetails) {
    return new Cell(1, 5)
        .add(String.format(FROM_TO,
            tripDetails != null ? tripDetails.getFrom() : "",
            tripDetails != null ? tripDetails.getTo() : ""))
        .setTextAlignment(TextAlignment.LEFT)
        .setBorder(Border.NO_BORDER);
  }
}
