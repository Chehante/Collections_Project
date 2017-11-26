package com.itmo.collections.Pattern.CryptoStream_Decorator;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class PrintClient {
    public static void main(String[] args){
        try {
            Socket socket = new Socket("localhost", 12345);

            try (OutputStream out = new CryptoOutputStream(socket.getOutputStream(), "pass".getBytes())){

                int a = 8;
                out.write(a);
                System.out.println("Client sent 8");

                try (CryptoInputStream in = new CryptoInputStream(socket.getInputStream(), "pass".getBytes())) {

                    int b = in.read();
                    System.out.println("Client got: " + b);
                }
            }

        }
        catch (IOException io) {
            System.out.println("IO Exception");
        }
    }


}
