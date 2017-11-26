package com.itmo.multithreading.FileServer;

import com.itmo.multithreading.chat.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class FileClient {

    private SocketAddress serverAddr;

    private Socket socket;

    private ObjectOutputStream objOut;

    private Scanner scanner;

    private String name;

    public FileClient(SocketAddress serverAddr, Scanner scanner) {
        this.serverAddr = serverAddr;
        this.scanner = scanner;
    }

    private void start() throws IOException {
        System.out.println("Enter your name: ");

        String name = scanner.nextLine();

        openConnection();

        System.out.println("Enter file path: ");

        while (true) {
            String s = scanner.nextLine();

            if ("/exit".equals(s)) {
                IOUtils.closeQuietly(socket);

                break;
            }
            else if ("/nick".equals(s)) {
                System.out.println("Enter new name:");

                name = scanner.nextLine();

                continue;
            }

            if (s != null && !s.isEmpty())
                sendFile(s);
        }
    }

    private static SocketAddress parseAddress(String addr) {
        String[] split = addr.split(":");
        return new InetSocketAddress(split[0], Integer.parseInt(split[1]));
    }

    public static void main(String[] args) throws IOException {
        String addr = null;

        if (args != null && args.length > 0)
            addr = args[0];

        Scanner scanner = new Scanner(System.in);

        if (addr == null) {
            System.out.println("Enter server address");

            addr = scanner.nextLine();
        }

        FileClient client = new FileClient(parseAddress(addr), scanner);

        client.start();
    }

    private void openConnection() {
        try {
            socket = new Socket();

            socket.connect(serverAddr);

            objOut = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (IOException e) {
            IOUtils.closeQuietly(socket);
        }
    }

    private void sendFile(String filePath) {

        File file = new File(filePath);
        if (file.exists()) {
            FileDescriptor fldscr = new FileDescriptor(filePath, file.length(), name);
            try {
                objOut.writeObject(fldscr);
                objOut.flush();
            } catch (IOException e) {
                IOUtils.closeQuietly(socket);
                e.printStackTrace();
            }
        }

    }
}
