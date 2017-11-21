package com.itmo.collections.CryptoStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PrintServer {

    public static void main(String[] args){
        try {
            ServerSocket ssocket = new ServerSocket(12345);
            Socket socket = ssocket.accept();

            try (InputStream in = new CryptoInputStream(socket.getInputStream(), "pass");
                 OutputStream out = new CryptoOutputStream(socket.getOutputStream(), "pass")) {

                byte[] bb = new byte[1024];
                int readBytes = in.read(bb);
                String line = new String(bb, 0, readBytes);
                System.out.println("Server got: " + line);

                out.write(line.getBytes());
                System.out.println("Server sent " + line);
                out.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
