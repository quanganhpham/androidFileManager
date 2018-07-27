package com.quanganh95.pham.filemanager.entity;

public class DetailFile {
    private boolean isDirectory; // nếu là thư mục thì true, còn không thì false
    private int imageFormatFile;
    private String nameFile; // Đưa ra tên file
    private String detailFile; // nếu là thư mục thì Directory, còn file thì đưa ra dung lượng
    private String dateFile; // Thời gian tạo file

    public DetailFile(boolean isDirectory, int imageFormatFile, String nameFile, String detailFile, String dateFile) {
        this.isDirectory = isDirectory;
        this.imageFormatFile = imageFormatFile;
        this.nameFile = nameFile;
        this.detailFile = detailFile;
        this.dateFile = dateFile;
    }

    public int getImageFormatFile() {
        return imageFormatFile;
    }

    public void setImageFormatFile(int imageFormatFile) {
        this.imageFormatFile = imageFormatFile;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public String getDetailFile() {
        return detailFile;
    }

    public void setDetailFile(String detailFile) {
        this.detailFile = detailFile;
    }

    public String getDateFile() {
        return dateFile;
    }

    public void setDateFile(String dateFile) {
        this.dateFile = dateFile;
    }
}
