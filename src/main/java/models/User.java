package models;

public abstract class User {
    private int userId;
    private String login;
    private String password;
    private UserType userType;

    public User(int userId, String login, String password, UserType userType) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.userType = userType;
    }

    // Геттеры и сеттеры
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
