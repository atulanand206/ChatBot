package com.experiment.billing.utils;

import com.experiment.billing.model.components.Particular;
import com.experiment.billing.model.components.Rates;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;

import org.apache.commons.text.WordUtils;

import java.util.ArrayList;
import java.util.List;

import static com.experiment.billing.constants.Constants.CGST;
import static com.experiment.billing.constants.Constants.GRAND_TOTAL;
import static com.experiment.billing.constants.Constants.IGST;
import static com.experiment.billing.constants.Constants.ROUND_OFF;
import static com.experiment.billing.constants.Constants.RS_IN_WORDS;
import static com.experiment.billing.constants.Constants.SGST;
import static com.experiment.billing.constants.Constants.TOTAL;
import static com.experiment.billing.utils.CalculationUtils.getPercentage;

public class TotalUtils {

  public static List<Cell> addTotalRow(final Rates rates, final int total) {
    List<Cell> cells = new ArrayList<>();
    cells.add(new Cell().add(new Paragraph(TOTAL)));
    cells.add(new Cell().add(new Paragraph(getPercentage((int) rates.getGstRate()))));
    cells.add(new Cell().add(new Paragraph(String.valueOf((int) total))));
    return cells;
  }

  public static List<Cell> addRoundOffRow() {
    List<Cell> cells = new ArrayList<>();
    cells.add(new Cell().add(new Paragraph(ROUND_OFF)));
    cells.add(new Cell().add(new Paragraph("")));
    cells.add(new Cell().add(new Paragraph("")));
    return cells;
  }

  public static List<Cell> addGrandTotalRow(final int grandTotal) {
    List<Cell> cells = new ArrayList<>();
    cells.add(new Cell().add(new Paragraph(GRAND_TOTAL)));
    cells.add(new Cell().add(new Paragraph("")));
    cells.add(new Cell().add(new Paragraph(String.valueOf(grandTotal))));
    return cells;
  }

  public static List<Cell> getTotalInWords(final int grandTotal) {
    List<Cell> cells = new ArrayList<>();
    cells.add(new Cell()
        .add(new Paragraph(String.format(
            RS_IN_WORDS,
            WordUtils.capitalize(EnglishNumberToWords.convert(grandTotal)).trim().replaceAll("  ", " ")))));
    return cells;
  }

  public static List<Cell> addSgstRow(final Rates rates, final int total) {
    return getXgstRow(SGST, rates.getSgstRate(), false, total);
  }

  public static List<Cell> addCgstRow(final Rates rates, final int total) {
    return getXgstRow(CGST, rates.getCgstRate(), false, total);
  }

  public static List<Cell> addIgstRow(final Rates rates, final int total) {
    return getXgstRow(IGST, rates.getIgstRate(), true, total);
  }

  public static List<Cell> getXgstRow(
      final String xgst,
      final double xgstRate,
      final boolean skip,
      final int total) {
    List<Cell> cells = new ArrayList<>();
    cells.add(new Cell().add(new Paragraph(xgst)));
    cells.add(new Cell().add(new Paragraph(skip ? "" : getPercentage(xgstRate))));
    cells.add(new Cell().add(new Paragraph(skip ? "" :
        String.valueOf((int) ((total * xgstRate) / 100)))));
    return cells;
  }

  public static int getTotal(
      final Rates rates,
      final List<Particular> particulars) {
    int total = 0;
    for (Particular particular : particulars) {
      total += particular.getQuantityValue() * rates.getRateValue();
    }
    return total;
  }

  public static int grandTotal(
      final Rates rates,
      final int total) {
    return (int) (total + (total * (rates.getSgstRate() + rates.getCgstRate())) / 100);
  }
}
