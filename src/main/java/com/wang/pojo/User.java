package com.wang.pojo;


import java.io.Serializable;

public class User implements Serializable {
    private String username;

    private String password;
    private String email;
    private String emails;
    private String table;
    private String message;
    public User() {
    }

    public User(String username, String password, String email,String emails,String message,String table) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.emails = emails;
        this.table = table;
        this.message = message;
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

    public void setEmail(String email) {this.email = email;}

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {this.emails = emails;}

    public String gettable() {return table;}

    public void settable(String table) {
        this.table = table;
    }

    public String getmessage() {
        return message;
    }

    public void setmessage(String message) {
        this.message = message;
    }
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", emails='" + emails + '\'' +
                ", table='" + table + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}


