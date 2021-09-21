package com.example.GreenNest.response;

public class OrderItemResponse {
    private long item_id;
    private int quantity;
    private long product_id;
    private String Product_name;
    private long order_id;

    public OrderItemResponse() {
    }

    public OrderItemResponse(int quantity, long product_id, String product_name, long order_id) {
        this.quantity = quantity;
        this.product_id = product_id;
        Product_name = product_name;
        this.order_id = order_id;
    }

    public long getItem_id() {
        return item_id;
    }

    public void setItem_id(long item_id) {
        this.item_id = item_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return Product_name;
    }

    public void setProduct_name(String product_name) {
        Product_name = product_name;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }
}
