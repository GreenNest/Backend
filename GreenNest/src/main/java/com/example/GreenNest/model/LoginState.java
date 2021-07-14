package com.example.GreenNest.model;

public class LoginState {
    private int loginState;
    private int userId;

    public LoginState() {
    }

    public LoginState(int loginState, int userId) {
        this.loginState = loginState;
        this.userId = userId;
    }

    public int getLoginState() {
        return loginState;
    }

    public void setLoginState(int loginState) {
        this.loginState = loginState;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
