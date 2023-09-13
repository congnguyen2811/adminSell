package com.manager.appbanhang.model;

public class User {
    private  int id;
    private String email;
    private String pass;
    private String username;
    private String mobile;
    private String uid;
    private String token;


    public String getToken() {
        return token;
    }

    public User(int id, String email, String pass, String username, String mobile, String uid, String token) {
        this.id = id;
        this.email = email;
        this.pass = pass;
        this.username = username;
        this.mobile = mobile;
        this.uid = uid;
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User(int id, String email, String pass, String username, String mobile) {
        this.id = id;
        this.email = email;
        this.pass = pass;
        this.username = username;
        this.mobile = mobile;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
