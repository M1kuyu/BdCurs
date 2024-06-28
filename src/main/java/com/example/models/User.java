package com.example.models;

public class User {
    private int userId;
    private String login;
    private String password;
    private int typeId;

    public User(int userId, String login, String password, int typeId) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.typeId = typeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

}
