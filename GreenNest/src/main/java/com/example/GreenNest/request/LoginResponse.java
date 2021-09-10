package com.example.GreenNest.request;

import java.util.List;

public class LoginResponse {
    private String token;
    private List<String> roles;
    private String name;
    private int id;

    public LoginResponse() {
    }

    public LoginResponse(String token, List<String> roles, String name, int id) {
        this.token = token;
        this.roles = roles;
        this.name = name;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
