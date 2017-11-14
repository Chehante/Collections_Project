package com.itmo.collections.IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class RandomInputStream extends InputStream {
    public static Object read;

    @Override
    public int read() throws IOException {
        return 0;
    }

    @Override
    public int read(byte[] bb) throws IOException {
        Random rnd = new Random();

        int counter = 0;
        rnd.nextBytes(bb);

        return bb.length;
    }
}
