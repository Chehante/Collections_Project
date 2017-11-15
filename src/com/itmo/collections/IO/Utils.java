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
        readSimmetricSawInputStream();

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
        InputStream ris = new RandomInputStream();
        ris.read(bb);
    }

    public static void readSimmetricSawInputStream() throws IOException{
        byte[] bb = new byte[100];

        HashMap<String, int[]> hm = new HashMap<String, int[]>();
        hm.put("1",   new int[]{0, 0, 0, 0, 0, 0, 0, 1});
        hm.put("3",   new int[]{0, 0, 0, 0, 0, 0, 1, 1});
        hm.put("7",   new int[]{0, 0, 0, 0, 0, 1, 1, 1});
        hm.put("15",  new int[]{0, 0, 0, 0, 1, 1, 1, 1});
        hm.put("31",  new int[]{0, 0, 0, 1, 1, 1, 1, 1});
        hm.put("63",  new int[]{0, 0, 1, 1, 1, 1, 1, 1});
        hm.put("127", new int[]{0, 1, 1, 1, 1, 1, 1, 1});
        hm.put("-1",  new int[]{1, 1, 1, 1, 1, 1, 1, 1});

        InputStream ssis = new SimmetricSawInputStream();
        ssis.read(bb);

        for (int k = 0; k < 8; k++) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bb.length; i++) {
                sb.append(hm.get("" + bb[i])[k] == 0 ? " " : "+");
            }
            System.out.println(sb.toString());
        }
    }
}
