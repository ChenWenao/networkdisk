package cwa.Bean;

import java.util.Date;

public class file {
    //文件表
    private int fileId;
    private int userId;
    private String fileName;
    private Date uploadTime;
    private Date updateTime;
    private String fileType;
    private int fileSize;
    private int fileStatus;
    private String fileLocation;

    //文件关系表
    private int file_userId;
    private int file_parentId;
    private String file_Path;


    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public int getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(int fileStatus) {
        this.fileStatus = fileStatus;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public int getFile_userId() {
        return file_userId;
    }

    public void setFile_userId(int file_userId) {
        this.file_userId = file_userId;
    }

    public int getFile_parentId() {
        return file_parentId;
    }

    public void setFile_parentId(int file_parentId) {
        this.file_parentId = file_parentId;
    }

    public String getFile_Path() {
        return file_Path;
    }

    public void setFile_Path(String file_Path) {
        this.file_Path = file_Path;
    }



}
