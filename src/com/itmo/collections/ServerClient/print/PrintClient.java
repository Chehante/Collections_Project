package com.itmo.collections.ServerClient.print;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Enumeration;
import java.util.Scanner;

/**
 * Created by xmitya on 28.08.16.
 */
public class PrintClient {

    private SocketAddress serverAddr;

    private String name;

    private Scanner scanner;

    public PrintClient(SocketAddress serverAddr, Scanner scanner) {
        this.serverAddr = serverAddr;
        this.scanner = scanner;
    }

    private void start() throws IOException, ClassNotFoundException {
        System.out.println("Enter your name: ");

        name = scanner.nextLine();

        while (true) {
            System.out.println("Enter message to send: ");

            String msg = scanner.nextLine();

            if ("/exit".equals(msg))
                break;
            else if ("/nick".equals(msg)) {
                System.out.println("Enter new name:");

                name = scanner.nextLine();

                continue;
            }
            else if ("/myaddr".equals(msg)) {
                printAddresses();

                continue;
            }

            else if ("/servertime".equals(msg)) {
                getServerTime();

                continue;
            }

            else if ("/userslist".equals(msg)) {
                getUsersList();

                continue;
            }

            else if ("/ping".equals(msg)) {
                getPing();

                continue;
            }

            buildAndSendMessage(msg);
        }
    }

    private void printAddresses() throws SocketException {
        Enumeration e = NetworkInterface.getNetworkInterfaces();

        while(e.hasMoreElements()) {
            NetworkInterface n = (NetworkInterface) e.nextElement();

            Enumeration ee = n.getInetAddresses();

            while (ee.hasMoreElements()) {
                InetAddress i = (InetAddress) ee.nextElement();

                System.out.println(i.getHostAddress());
            }
        }
    }

    private void buildAndSendMessage(String msg) throws IOException {
        Message message = new Message(System.currentTimeMillis(), name, msg);

        sendPrintMessage(message);

        System.out.println("Sent: " + message);
    }

    private void sendPrintMessage(Message msg) throws IOException {
        try (Socket sock = new Socket()) {
            sock.connect(serverAddr);

            try (OutputStream out = sock.getOutputStream()) {
                ObjectOutputStream objOut = new ObjectOutputStream(out);

                objOut.writeObject(msg);

                objOut.flush();
            }
        }
    }

    private static SocketAddress parseAddress(String addr) {
        String[] split = addr.split(":");
        return new InetSocketAddress(split[0], Integer.parseInt(split[1]));
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String addr = null;

        if (args != null && args.length > 0)
            addr = args[0];

        Scanner scanner = new Scanner(System.in);

        if (addr == null) {
            System.out.println("Enter server address");

            addr = scanner.nextLine();
        }

        PrintClient client = new PrintClient(parseAddress(addr), scanner);

        client.start();
    }

    private void getServerTime() throws IOException, ClassNotFoundException{

        try (Socket sock = new Socket()) {
            sock.connect(serverAddr);

            ServerTimeCommand srvTmCom = new ServerTimeCommand();

            try (OutputStream out = sock.getOutputStream()) {

                ObjectOutputStream objOut = new ObjectOutputStream(out);

                objOut.writeObject(srvTmCom);

                try (InputStream in = sock.getInputStream()) {

                    ObjectInputStream objIn = new ObjectInputStream(in);

                    Object obj = objIn.readObject();

                    System.out.println(obj.toString());

                }

                objOut.flush();
            }
        }

    }

    private void getUsersList() throws IOException, ClassNotFoundException{

        try (Socket sock = new Socket()) {
            sock.connect(serverAddr);

            UsersListCommand usrListCom = new UsersListCommand();

            try (OutputStream out = sock.getOutputStream()) {

                ObjectOutputStream objOut = new ObjectOutputStream(out);

                objOut.writeObject(usrListCom);

                try (InputStream in = sock.getInputStream()) {

                    ObjectInputStream objIn = new ObjectInputStream(in);

                    Object obj = objIn.readObject();

                    System.out.println(obj.toString());

                }

                objOut.flush();
            }
        }

    }

    private void getPing() throws IOException, ClassNotFoundException{

        try (Socket sock = new Socket()) {
            sock.connect(serverAddr);

            try (OutputStream out = sock.getOutputStream()) {

                ObjectOutputStream objOut = new ObjectOutputStream(out);

                PingComand pngCom = new PingComand();
                long secondtime = 0;
                pngCom.firsTime = System.currentTimeMillis();

                objOut.writeObject(pngCom);

                try (InputStream in = sock.getInputStream()) {

                    ObjectInputStream objIn = new ObjectInputStream(in);

                    Object obj = objIn.readObject();

                    secondtime = System.currentTimeMillis();

                    System.out.println("Ping is " + (pngCom.firsTime - secondtime) + " mlsec");

                }

                objOut.flush();
            }
        }

    }
}
