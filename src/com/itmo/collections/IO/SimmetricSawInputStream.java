package com.itmo.collections.IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimmetricSawInputStream extends InputStream {

//    00000001 = 1
//            00000011 = 3
//            00000111 = 7
//            00001111 = 15
//            00011111 = 31
//            00111111 = 63
//            01111111 = 127
//            11111111 = -1
    byte[] teeth = new byte[]{1, 3, 7, 15, 31, 63, 127, -1};

    int currentIndex = 0;

    @Override
    public int read(byte[] b) throws IOException {

        for (int i = 0; i < b.length; i++){
            b[i] = teeth[currentIndex];
            currentIndex = currentIndex == 7 ? 0 : currentIndex + 1;
        }
        return b.length;

    }

    @Override
    public int read() throws IOException {
        return 0;
    }
}
