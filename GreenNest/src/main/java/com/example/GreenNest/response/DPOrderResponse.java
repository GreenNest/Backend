package com.example.GreenNest.response;

public class DPOrderResponse {
    private long order_id;
    private String address;
    private String city;
    private int mobile;
    private String order_type;
    private double total_price;
    private String first_name;
    private String last_name;

    public DPOrderResponse() {
    }

    public DPOrderResponse(String address, String city, int mobile, String order_type, double total_price, String first_name, String last_name) {
        this.address = address;
        this.city = city;
        this.mobile = mobile;
        this.order_type = order_type;
        this.total_price = total_price;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
