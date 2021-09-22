package com.example.GreenNest.response;

public class ReportResponse {
    private String first_name;
    private String last_name;
    private String address;
    private String city;
    private int mobile;
    private int Quantity;

    public ReportResponse() {
    }

    public ReportResponse(String first_name, String last_name, String address, int mobile, int quantity, String City) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.mobile = mobile;
        Quantity = quantity;
        this.city = city;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
