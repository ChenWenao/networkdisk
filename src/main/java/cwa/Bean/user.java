package cwa.Bean;

import java.util.Date;

public class user {
    private int userId;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Date createTime;
    private double dataSize;
    private double dataMax;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
