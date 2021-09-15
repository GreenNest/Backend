package com.example.GreenNest.response;

import java.util.Date;

public class ReviewResponse {
    private String reviews;
    private int rate;
    private Date date;
    private String customerName;

    public ReviewResponse() {
    }

    public ReviewResponse(String reviews, int rate, Date date, String customerName) {
        this.reviews = reviews;
        this.rate = rate;
        this.date = date;
        this.customerName = customerName;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
