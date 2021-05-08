package cwa.bean;

import java.util.Date;

public class NetShare {
    private int share_fileId;
    private int share_userId;
    private Date shareTime;
    private String shareCode;
    private int shareLifeTime;

    //文件表
    private int fileId;
    private String fileName;
    private Date uploadTime;
    private Date updateTime;
    private String fileType;
    private long fileSize;
    private int fileStatus;
    private String fileLocation;

    //文件关系表
    private int file_userId;
    private int file_parentId;
    private String file_Path;


    private int userId;
    private String userName;
    private String password;
    private String email;
    private String phone;
    private Date createTime;
    private double dataSize;
    private double dataMax;


    public int getShare_fileId() {
        return share_fileId;
    }

    public void setShare_fileId(int share_fileId) {
        this.share_fileId = share_fileId;
    }

    public int getShare_userId() {
        return share_userId;
    }

    public void setShare_userId(int share_userId) {
        this.share_userId = share_userId;
    }

    public Date getShareTime() {
        return shareTime;
    }

    public void setShareTime(Date shareTime) {
        this.shareTime = shareTime;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public int getShareLifeTime() {
        return shareLifeTime;
    }

    public void setShareLifeTime(int shareLifeTime) {
        this.shareLifeTime = shareLifeTime;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
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

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public double getDataSize() {
        return dataSize;
    }

    public void setDataSize(double dataSize) {
        this.dataSize = dataSize;
    }

    public double getDataMax() {
        return dataMax;
    }

    public void setDataMax(double dataMax) {
        this.dataMax = dataMax;
    }

}
