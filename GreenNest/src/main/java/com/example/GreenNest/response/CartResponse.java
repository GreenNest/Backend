package com.example.GreenNest.response;

public class CartResponse {

    private long id;
    private int quantity;
    private double price;
    private String image;
    private String name;
    private long product_id;
    // product name
    //product id

    public CartResponse() {
    }

    public CartResponse(long id, int quantity, double price, String image, String name, long product_id) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        this.name = name;
        this.product_id = product_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }
}
