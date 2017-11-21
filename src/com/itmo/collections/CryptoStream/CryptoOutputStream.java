package com.itmo.collections.CryptoStream;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CryptoOutputStream extends FilterOutputStream {

    String pass;

    protected CryptoOutputStream(OutputStream out, String pass){
        super(out);
        this.pass = pass;
    }

    @Override
    public void write(byte[] b) throws IOException {

        byte[] passBytes = pass.getBytes();

        for (int i = 0; i < b.length; i++) {
            b[i] ^= passBytes[i % passBytes.length];
        }
    }
}
