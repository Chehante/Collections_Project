package com.itmo.collections.ServerClient.print;

import com.itmo.collections.IO.RandomInputStream;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by xmitya on 28.08.16.
 */
public class PrintServer {

    private int port;

    List<String> users = new ArrayList<>();

    private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");

    public PrintServer(int port) {
        this.port = port;
    }

    private void start() throws IOException {
        try (ServerSocket ssocket = new ServerSocket(port)) {
            System.out.println("Server started on " + ssocket);

            while (true) {
                Socket sock = ssocket.accept();

                try {
                    process(sock);
                } catch (ClassNotFoundException e) {
                    System.err.println("Wrong message was received");

                    e.printStackTrace();
                } finally {
                    sock.close();
                }
            }
        }
    }

    private void process(Socket sock) throws IOException, ClassNotFoundException {
        String host = sock.getInetAddress().getHostAddress();

        try (InputStream in = sock.getInputStream()) {

            ObjectInputStream objIn = new ObjectInputStream(sock.getInputStream());

            Object obj = objIn.readObject();

            if (obj instanceof Message)
                printMessage((Message) obj, host);
            else {
                try (OutputStream out = sock.getOutputStream()) {
                    ObjectOutputStream objOut = new ObjectOutputStream(sock.getOutputStream());
                    if (obj instanceof ServerTimeCommand)
                        objOut.writeObject(new Date(System.currentTimeMillis()));
                    else if (obj instanceof UsersListCommand)
                        objOut.writeObject(users);
                    else if (obj instanceof PingComand) {
                        Random rndm = new Random();
                        objOut.writeObject(rndm.nextInt());
                    }
                }
            }
        } catch (IOException | ClassNotFoundException | RuntimeException e) {
            System.err.println("Failed process connection from: " + host);

            e.printStackTrace();

            throw e;
        }
    }

    private void printMessage(Message msg, String senderAddr) {
        System.out.printf("%s (%s) at %s wrote: %s\n", msg.getSender(), senderAddr, format.format(new Date(msg.getTimestamp())), msg.getText());
        users.add(msg.getSender());
    }

    public static void main(String[] args) throws IOException {
        if (args == null || args.length == 0)
            throw new IllegalArgumentException("Port must be specified");

        int port = Integer.parseInt(args[0]);

        PrintServer printServer = new PrintServer(port);

        printServer.start();
    }
}
