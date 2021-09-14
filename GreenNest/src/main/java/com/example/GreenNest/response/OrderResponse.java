package com.example.GreenNest.response;

import java.util.Date;

public class OrderResponse {
    private String image;
    private String name;
    private double price;
    private int quantity;
    private double subtotal;
    private long productId;
    public OrderResponse() {
    }

    public OrderResponse(String image, String name, double price, int quantity, double subtotal, long productId) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.productId = productId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
