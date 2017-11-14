package com.itmo.collections.IO;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.util.*;

public class Utils {

    public static void main(String[] args) throws IOException{
        //copyFile();
//        String dirPath = "E:/Java/IO/auxil";
//        List<String> listFiles = separateOnParts(dirPath);
//        glueFileFromParts(listFiles, dirPath);
//        readRandomInputStream();
//        Byte b = Byte.parseByte("000000001", 2);
//        System.out.println("" + b);
//        System.out.println(Integer.toBinaryString(b & 0xFF));

    }

    public static void copyFile() throws IOException{
        File infile = new File("E:/Java/IO/WarAndPiece.txt");
        File outfile = new File("E:/Java/IO/WarAndPiece_Copy.txt");

        try (InputStream is = new FileInputStream(infile);
             OutputStream os = new FileOutputStream(outfile)){
            byte[] buf = new byte[1024];

            int len;

            while ((len = is.read(buf)) > 0)
                os.write(buf, 0, len);
        }
    }

    public static List<String> separateOnParts(String dirPath) throws IOException{

        List<String> listFiles = new ArrayList<>();
        File infile = new File("E:/Java/IO/WarAndPiece.txt");
        File auxDirectory = new File(dirPath);
        if (!auxDirectory.exists())
            auxDirectory.mkdir();

        try (InputStream is = new FileInputStream(infile)){

            int counter = 0;

            mark:
            while (true) {
                File outfile = new File(auxDirectory.getPath() + "/" + counter + ".txt");
                listFiles.add(outfile.getName());
                OutputStream os = new FileOutputStream(outfile);

                byte[] buf = new byte[1024];

                int len;
                int fileSize = 0;

                while (fileSize < 102400) {
                    len = is.read(buf);
                    if (len > 0) {
                        os.write(buf, 0, len);
                        fileSize += len;
                    } else {
                        os.close();
                        break mark;
                    }
                }
                os.close();
                counter++;
            }
        }
        return listFiles;
    }

    public static void glueFileFromParts(List<String> listFiles, String dirPath) throws IOException{
        File auxDirectory = new File(dirPath);
        File outfile = new File("E:/Java/IO/WarAndPiece_2.txt");
        File[] files = auxDirectory.listFiles();

        try (OutputStream os = new FileOutputStream(outfile)){

            for (String s : listFiles) {
                File infile = new File(dirPath + "/" + s);
                InputStream is = new FileInputStream(infile);
                byte[] buf = new byte[1024];

                int len;

                while ((len = is.read(buf)) > 0)
                    os.write(buf, 0, len);
                is.close();
            }
        }
    }

    public static void readRandomInputStream() throws IOException{
        byte[] bb = new byte[10];
        RandomInputStream ris = new RandomInputStream();
        ris.read(bb);
    }
}
