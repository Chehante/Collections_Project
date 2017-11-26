package com.itmo.multithreading.FileServer;

import java.io.Serializable;

public class FileDescriptor implements Serializable{
    private String fileName;
    private long fileLength;
    private String userName;

    public FileDescriptor(String fileName, long fileLength, String userName){
        this.fileName = fileName;
        this.fileLength = fileLength;
        this.userName = userName;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileLength() {
        return fileLength;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
