package com.itmo.collections.CryptoStream;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CryptoInputStream extends FilterInputStream {

    String pass;

    protected CryptoInputStream(InputStream in, String pass){
        super(in);
        this.pass = pass;
    }

    @Override
    public int read(byte[] b) throws IOException {

        byte[] passBytes = pass.getBytes();

        for (int i = 0; i < b.length; i++) {
            b[i] ^= passBytes[i % passBytes.length];
        }

        return b.length;
    }
}
