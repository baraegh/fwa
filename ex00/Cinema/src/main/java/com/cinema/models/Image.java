package com.cinema.models;

public class Image {
    private Long    id;
    private Long    userId;
    private String  fileName;
    private String  mimeType;
    private Long    size;
    private String  filePath;
    private String  formattedSize;

    public Image() {
    }

    public Image(Long id, Long userId, String fileName, String mimeType, Long size, String filePath) {
        this.id = id;
        this.userId = userId;
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.size = size;
        this.filePath = filePath;
    }

    public Image(Long userId, String fileName, String mimeType, Long size, String filePath) {
        this.userId = userId;
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.size = size;
        this.filePath = filePath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFormattedSize() {
        return formattedSize;
    }

    public void setFormattedSize(String formattedSize) {
        this.formattedSize = formattedSize;
    }
}
