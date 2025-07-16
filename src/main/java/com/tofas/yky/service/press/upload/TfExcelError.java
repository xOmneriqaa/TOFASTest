package com.tofas.yky.service.press.upload;
/* t40127 @ 23.05.2016. */

public class TfExcelError {

    int row;
    int column;

    public TfExcelError(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
