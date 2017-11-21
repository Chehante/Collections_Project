package com.itmo.collections.CryptoStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class PrintClient {
    public static void main(String[] args){
        try {
            Socket socket = new Socket("localhost", 12345);

            try (InputStream in = new CryptoInputStream(socket.getInputStream(), "pass");
                 OutputStream out = new CryptoOutputStream(socket.getOutputStream(), "pass")){

                String s = "Hello";
                out.write(s.getBytes());
                System.out.println("Client sent Hello");

                byte[] bb = new byte[1024];
                int readBytes = in.read(bb);
                System.out.println("Client got " + new String(bb, 0, readBytes));
            }

        }
        catch (IOException io) {
            System.out.println("IO Exception");
        }
    }


}
