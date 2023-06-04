package com.udacity.jwdnd.course1.cloudstorage.model;

public class User {
    private Integer userId;
    private String username;
    private String salt;
    private String password;
    private String name;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public User(Integer userId, String username, String salt, String password, String name) {
        this.userId = userId;
        this.username = username;
        this.salt = salt;
        this.password = password;
        this.name = name;
    }
}