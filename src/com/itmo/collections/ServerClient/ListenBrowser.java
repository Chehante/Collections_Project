package com.itmo.collections.ServerClient;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ListenBrowser {

    public static void main(String[] args) throws IOException {
        ServerSocket ssocket = new ServerSocket(12345);
        System.out.println("Server started on " + ssocket);

        try (Socket sock = ssocket.accept()) {

            try (InputStream in = sock.getInputStream()) {
                Scanner scr = new Scanner(in);
                while (scr.hasNext())
                    System.out.println(scr.nextLine());

            }

            sock.close();
        }catch (IOException io){
            System.out.println("IO Exception");
        }

    }


}
