package com.tofas.yky.service.press.upload;
/* t40127 @ 23.05.2016. */

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;

public class TfSheet {

    Sheet sheet;
    List<TfExcelError> errors;

    public TfSheet(Sheet sheet) {
        this.sheet = sheet;
        this.errors = new ArrayList<>();
    }

    public int getLastRowNum() {
        return sheet.getLastRowNum();
    }

    public TfRow getRow(int i) {
        try {
            Row row = sheet.getRow(i);
            return new TfRow(i, this, row);
        } catch (Exception e) {
            addError(i, 0);
            throw new RuntimeException(e);
        }
    }

    public void addError(int row, int column) {
        this.errors.add(new TfExcelError(row, column));
    }

    public List<TfExcelError> getErrors() {
        return errors;
    }
}
