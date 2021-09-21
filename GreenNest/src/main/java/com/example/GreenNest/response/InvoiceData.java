package com.example.GreenNest.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceData{
    private Date date;
    private String address1;
    private String town;
    private String customer_name;
    private List<OrderResponse> orderResponses = new ArrayList<OrderResponse>();

    public InvoiceData(Date date, String address1, String town, String customer_name, List<OrderResponse> orderResponses) {
        this.date = date;
        this.address1 = address1;
        this.town = town;
        this.customer_name = customer_name;
        this.orderResponses = orderResponses;
    }

    public InvoiceData() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public List<OrderResponse> getOrderResponses() {
        return orderResponses;
    }

    public void setOrderResponses(List<OrderResponse> orderResponses) {
        this.orderResponses = orderResponses;
    }
}
