package com.example.GreenNest.request;

import java.util.List;

public class LoginResponse {
    private String token;
    private List<String> roles;
    private String name;

    public LoginResponse() {
    }

    public LoginResponse(String token, List<String> roles, String name) {
        this.token = token;
        this.roles = roles;
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
