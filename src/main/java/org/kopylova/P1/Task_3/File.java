package org.kopylova.P1.Task_3;

public class File {
    private String fileType;
    private int fileSize;
    public File(String fileType, int fileSize) {
        this.fileType = fileType;
        this.fileSize = fileSize;
    }
    public String getFileType() {
        return fileType;
    }
    public int getFileSize() {
        return fileSize;
    }
}