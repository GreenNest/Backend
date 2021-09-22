package com.example.GreenNest.request;

import com.example.GreenNest.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class OrderItemsRequest {
    private List<Cart> carts;

    public OrderItemsRequest() {
    }

    public OrderItemsRequest(List<Cart> carts) {
        this.carts = carts;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }
}
