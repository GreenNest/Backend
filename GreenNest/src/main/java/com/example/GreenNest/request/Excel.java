package com.example.GreenNest.request;

import org.springframework.web.multipart.MultipartFile;

public class Excel {
    private String name;
    private String details;
    private float price;
    private int amount;
    private boolean isfeatured;
    private int reorderLevel;
    private String category;

    public Excel() {
    }

    public Excel(String name, String details, float price, int amount, boolean isfeatured, int reorderLevel, String category) {
        this.name = name;
        this.details = details;
        this.price = price;
        this.amount = amount;
        this.isfeatured = isfeatured;
        this.reorderLevel = reorderLevel;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isIsfeatured() {
        return isfeatured;
    }

    public void setIsfeatured(boolean isfeatured) {
        this.isfeatured = isfeatured;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
