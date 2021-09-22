package com.example.GreenNest.response;

public class ProductIdNameResponse {
    private long id;
    private String name;

    public ProductIdNameResponse() {
    }

    public ProductIdNameResponse(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
