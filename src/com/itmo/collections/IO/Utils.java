package com.itmo.collections.IO;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;

public class Utils {

    public static void main(String[] args) throws IOException{
        copyFile();
    }

    public static void copyFile() throws IOException{
        File infile = new File("D:/ТРТИЛЕК/Java/IO/3.vsdx");
        File outfile = new File("D:/ТРТИЛЕК/Java/IO/3_Copy.vsdx");

        try (InputStream is = new FileInputStream(infile);
             OutputStream os = new FileOutputStream(outfile)){
            byte[] buf = new byte[1024];

            int len;

            while ((len = is.read(buf)) > 0)
                os.write(buf, 0, len);
        }
    }
}
