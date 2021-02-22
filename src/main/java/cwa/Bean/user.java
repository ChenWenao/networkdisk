package cwa.Bean;

import java.util.Date;

public class user {
    private int id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Date createTime;
    private float dataSize;
    private float dataMax;

    public float getDataMax() {
        return dataMax;
    }

    public void setDataMax(float dataMax) {
        this.dataMax = dataMax;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public float getDataSize() {
        return dataSize;
    }

    public void setDataSize(float dataSize) {
        this.dataSize = dataSize;
    }

}
