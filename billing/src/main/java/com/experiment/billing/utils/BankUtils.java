package com.experiment.billing.utils;

import com.experiment.billing.model.components.Bank;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import static com.experiment.billing.constants.Constants.ACCOUNT_NUMBER;
import static com.experiment.billing.constants.Constants.IFSC;
import static com.experiment.billing.constants.Constants.NAME_OF_BANK;

public class BankUtils {

  public static Cell getIFSCCell(final Bank bankDetails) {
    return new Cell(1, 5)
        .add(new Paragraph(String.format(IFSC, bankDetails.getIfsc())))
        .setTextAlignment(TextAlignment.LEFT)
        .setBorder(Border.NO_BORDER);
  }

  public static Cell getAccountNumberCell(final Bank bankDetails) {
    return new Cell(1, 5)
        .add(new Paragraph(String.format(ACCOUNT_NUMBER, bankDetails.getAccountNumber())))
        .setTextAlignment(TextAlignment.LEFT)
        .setBorder(Border.NO_BORDER);
  }

  public static Cell getBankCell(final Bank bankDetails) {
    return new Cell(1, 5)
        .add(new Paragraph(String.format(NAME_OF_BANK, bankDetails.getNameOfBank())))
        .setTextAlignment(TextAlignment.LEFT)
        .setBorder(Border.NO_BORDER);
  }
}
