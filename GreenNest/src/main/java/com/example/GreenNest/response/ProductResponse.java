package com.example.GreenNest.response;

import java.util.ArrayList;

public class ProductResponse {
    private long id;
    private String name;
    private String description;
    private double price;
    private int amount;
    private int reorder_level;
    private String mainImage;
    private ArrayList<String> subImages;

    public ProductResponse() {
    }

    public ProductResponse(long id, String name, String description, double price, int amount, int reorder_level, String mainImage, ArrayList<String> subImages) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.reorder_level = reorder_level;
        this.mainImage = mainImage;
        this.subImages = subImages;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getReorder_level() {
        return reorder_level;
    }

    public void setReorder_level(int reorder_level) {
        this.reorder_level = reorder_level;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public ArrayList<String> getSubImages() {
        return subImages;
    }

    public void setSubImages(ArrayList<String> subImages) {
        this.subImages = subImages;
    }
}
