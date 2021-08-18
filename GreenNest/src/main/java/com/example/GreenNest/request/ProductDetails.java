package com.example.GreenNest.request;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.web.multipart.MultipartFile;

//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ProductDetails {
    private String name;
    private String details;
    private float price;
    private int amount;
    private boolean isfeatured;
    private int reorderLevel;
    private MultipartFile[] files;

    public ProductDetails() {
    }

    public ProductDetails(String name, String details, float price, int amount, boolean isfeatured, int reorderLevel, MultipartFile[] files) {
        this.name = name;
        this.details = details;
        this.price = price;
        this.amount = amount;
        this.isfeatured = isfeatured;
        this.reorderLevel = reorderLevel;
        this.files = files;
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

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }
}
