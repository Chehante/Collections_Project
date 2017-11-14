package com.itmo.collections.IO;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;

public class Utils {

    public static void main(String[] args) throws IOException{
        //copyFile();
        separateOnParts();
    }

    public static void copyFile() throws IOException{
        File infile = new File("D:/ТРТИЛЕК/Java/IO/WarAndPiece.txt");
        File outfile = new File("D:/ТРТИЛЕК/Java/IO/WarAndPiece_Copy.txt");

        try (InputStream is = new FileInputStream(infile);
             OutputStream os = new FileOutputStream(outfile)){
            byte[] buf = new byte[1024];

            int len;

            while ((len = is.read(buf)) > 0)
                os.write(buf, 0, len);
        }
    }

    public static void separateOnParts() throws IOException{

        File infile = new File("D:/ТРТИЛЕК/Java/IO/WarAndPiece.txt");
        File auxDirectory = new File("D:/ТРТИЛЕК/Java/IO/auxil");
        if (!auxDirectory.exists())
            auxDirectory.mkdir();

        try (InputStream is = new FileInputStream(infile)){

            int counter = 0;
            boolean endOfBook = false;

            mark:
            while (!endOfBook) {
                File outfile = new File(auxDirectory.getPath() + "/" + counter + ".txt");
                OutputStream os = new FileOutputStream(outfile);

                byte[] buf = new byte[1024];

                int len;
                int fileSize = 0;

                while (fileSize <= 102400) {
                    len = is.read(buf);
                    if (len > 0){
                    os.write(buf, 0, len);
                    fileSize += len;
                    }
                    else {endOfBook = true;};
                    break mark;
                }
                os.close();
                counter++;
            }
        }
    }
}
