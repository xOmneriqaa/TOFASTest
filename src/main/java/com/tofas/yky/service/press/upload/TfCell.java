package com.tofas.yky.service.press.upload;
/* t40127 @ 23.05.2016. */

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;

import java.util.Date;

public class TfCell {

    private static DataFormatter dataFormatter = new DataFormatter();

    int columnIndex;
    Cell cell;
    TfRow row;

    public TfCell(int columnIndex, TfRow row, Cell cell) {
        this.columnIndex = columnIndex;
        this.cell = cell;
        this.row = row;
    }

    public TfCell(TfRow row) {
        cell = null;
        this.row = row;
    }

    public String getStringCellValue() {
        if(cell == null) {
            return "";
        } else {

            try {
                return dataFormatter.formatCellValue(cell);
            } catch (Exception e) {
                addError();
                throw new RuntimeException(e);
            }

        }
    }

    public Date getDateCellValue() {
        if(cell == null) {
            return null;
        } else {

            try {
                return cell.getDateCellValue();
            } catch (Exception e) {
                addError();
                throw new RuntimeException(e);
            }

        }
    }

    public double getNumericCellValue() {
        if(cell == null) {
            return -1d;
        } else {

            try {
                return cell.getNumericCellValue();
            } catch (Exception e) {
                addError();
                throw new RuntimeException(e);
            }

        }
    }

    public void addError() {
        this.row.addError(columnIndex);
    }

}
