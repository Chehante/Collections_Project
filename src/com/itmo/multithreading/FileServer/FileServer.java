package com.itmo.multithreading.FileServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {

    private int port;

    public FileServer(int port){
        this.port = port;
    }

    private void start() throws IOException {

        try (ServerSocket ssocket = new ServerSocket(port)) {
            System.out.println("Server started on " + ssocket);

            while (true) {
                Socket socket = ssocket.accept();

                new Thread(new FileServer.Reader(socket)).start();

            }
        }
    }

    private class Reader implements Runnable{
        private final Socket socket;

        private Reader(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {

            try (InputStream in = socket.getInputStream();
                 ObjectInputStream objIn = new ObjectInputStream(in)) {

                System.out.printf("%s connected\n", socket.getInetAddress().getHostAddress());
                while (!Thread.currentThread().isInterrupted()) {
                    FileDescriptor fldscr = (FileDescriptor) objIn.readObject();

                    System.out.println("Beginning to catch file: " + fldscr.getFileName());

                    byte[] buf = new byte[1024];

                    int len;

                    File file = new File("D:/ТРТИЛЕК/Java/Collections_Project/src/com/itmo/multithreading/FileServer/in/" + fldscr.getFileName());

                    FileOutputStream outFile = new FileOutputStream(file);

                    while ((len = in.read(buf)) > 0)
                        outFile.write(buf, 0, len);
                    System.out.println("We've received new file: " + file.getName());
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        if (args == null || args.length == 0)
            throw new IllegalArgumentException("Port must be specified");

        int port = Integer.parseInt(args[0]);

        FileServer fileServer = new FileServer(port);

        fileServer.start();
    }
}
