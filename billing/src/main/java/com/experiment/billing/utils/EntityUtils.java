package com.experiment.billing.utils;

import com.experiment.billing.model.components.Entity;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.UnitValue;
import java.util.ArrayList;
import java.util.List;
import static com.experiment.billing.constants.Constants.PROP;

public class EntityUtils {

  public static List<Cell> addEntityInfo(final Entity entity) {
    List<Cell> cells = new ArrayList<>();
    Cell entityName = new Cell()
        .setFontSize(20)
        .setWidth(UnitValue.createPercentValue(100))
        .setHorizontalAlignment(HorizontalAlignment.CENTER)
        .add(new Paragraph(entity.getFirm()));
    Cell entityProp = new Cell()
        .add(new Paragraph(String.format(PROP, entity.getProprietor())));
    Cell entityHomeAddress = new Cell()
        .add(new Paragraph(entity.getHomeAddress()));
    Cell entityFirmAddress = new Cell()
        .add(new Paragraph(entity.getFirmAddress()));
    cells.add(entityName);
    cells.add(entityProp);
    cells.add(entityHomeAddress);
    cells.add(entityFirmAddress);
    return cells;
  }
}
