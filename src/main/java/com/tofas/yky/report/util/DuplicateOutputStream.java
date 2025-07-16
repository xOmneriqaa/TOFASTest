package com.tofas.yky.report.util;
/* t40127 @ 29.03.2016. */

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.IOException;

public class DuplicateOutputStream extends ServletOutputStream {

    private ServletOutputStream outputStream1;
    private StringBuilder outputStream2 ;

    public DuplicateOutputStream(ServletOutputStream outputStream1) {
        super();
        this.outputStream1 = outputStream1;
        this.outputStream2 = new StringBuilder();
    }

    @Override
    public void write(int b) throws IOException {
        outputStream2.append((char) b);
    }

    public String getContent() {
        return outputStream2.toString();
    }

    @Override
    public boolean isReady() {
        return outputStream1.isReady();
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        outputStream1.setWriteListener(writeListener);
    }

    @Override
    public void print(String s) throws IOException {
        outputStream1.print(s);
        super.print(s);
    }

}
