package com.example.GreenNest.request;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ProductDetails {
    private String name;
    private String details;
    private float price;
    private int amount;
    private boolean isfeatured;
    private int reorderLevel;
    private MultipartFile file;
    private MultipartFile image1;
    private MultipartFile image2;
    private MultipartFile image3;


    public ProductDetails() {
    }

    public ProductDetails(String name, String details, float price, int amount, boolean isfeatured, int reorderLevel, MultipartFile file, MultipartFile image1, MultipartFile image2, MultipartFile image3) {
        this.name = name;
        this.details = details;
        this.price = price;
        this.amount = amount;
        this.isfeatured = isfeatured;
        this.reorderLevel = reorderLevel;
        this.file = file;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public MultipartFile getImage1() {
        return image1;
    }

    public void setImage1(MultipartFile image1) {
        this.image1 = image1;
    }

    public MultipartFile getImage2() {
        return image2;
    }

    public void setImage2(MultipartFile image2) {
        this.image2 = image2;
    }

    public MultipartFile getImage3() {
        return image3;
    }

    public void setImage3(MultipartFile image3) {
        this.image3 = image3;
    }
}
