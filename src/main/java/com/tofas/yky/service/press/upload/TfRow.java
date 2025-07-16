package com.tofas.yky.service.press.upload;
/* t40127 @ 23.05.2016. */

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class TfRow {

    int rowIndex;
    Row row;
    TfSheet sheet;

    public TfRow(int rowIndex, TfSheet sheet, Row row) {
        this.rowIndex = rowIndex;
        this.row = row;
        this.sheet = sheet;
    }

    public TfCell getCell(int i) {
        try {
            Cell cell = row.getCell(i);
            return new TfCell(i, this, cell);
        } catch (Exception e){
            addError(i);
            throw new RuntimeException(e);
        }
    }

    public TfCell getNullableCell(int i) {
        try {
            Cell cell = row.getCell(i);
            return new TfCell(i, this, cell);
        } catch (Exception e){
            return new TfCell(this);
        }
    }

    public void addError(int column) {
        sheet.addError(rowIndex, column);
    }

}
