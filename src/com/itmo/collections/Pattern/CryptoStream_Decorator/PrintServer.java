package com.itmo.collections.Pattern.CryptoStream_Decorator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PrintServer {

    public static void main(String[] args) throws IOException {
        ServerSocket ssocket = new ServerSocket(12345);
        Socket socket = ssocket.accept();

        InputStream in = new CryptoInputStream(socket.getInputStream(), "pass".getBytes());

        int b = in.read();
        System.out.println("Number: " + b);

        try (OutputStream out = new CryptoOutputStream(socket.getOutputStream(), "pass".getBytes())) {

            int a = 8;
            out.write(a);
            System.out.println("Client sent 8");

        }
    }
}
