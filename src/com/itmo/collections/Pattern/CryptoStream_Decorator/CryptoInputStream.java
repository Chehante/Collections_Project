package com.itmo.collections.Pattern.CryptoStream_Decorator;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xmitya on 05.12.16.
 */
public class CryptoInputStream extends FilterInputStream {
    private byte[] key;

    private int idx;

    public CryptoInputStream(InputStream in, byte[] key) {
        super(in);

        this.key = key.clone();
    }

    protected CryptoInputStream(InputStream in, byte[] key, long off) {
        super(in);

        this.key = key.clone();

        idx = (int) (off % key.length);
    }

    @Override
    public int read() throws IOException {
        int res = super.read();

        if (res < 0)
            return res;

        byte b = (byte) res;

        b ^= key[idx++];

        idx %= key.length;

        return b & 0xFF;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int read = super.read(b, off, len);

        if (read > 0) {
            for (int i = off; i < read + off; i++) {
                b[i] ^= key[idx++];

                idx %= key.length;
            }
        }

        return read;
    }

}
