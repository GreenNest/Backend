package com.example.GreenNest.response;

import java.util.Date;

public class COResponse {
    private long id;
    private int items;
    private Date date;
    private double cost;

    public COResponse() {
    }

    public COResponse(long id, int items, Date date, double cost) {
        this.id = id;
        this.items = items;
        this.date = date;
        this.cost = cost;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
