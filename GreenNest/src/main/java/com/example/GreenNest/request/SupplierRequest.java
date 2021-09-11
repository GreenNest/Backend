package com.example.GreenNest.request;

import com.example.GreenNest.response.CategoryResponse;

import java.util.ArrayList;

public class SupplierRequest {
    private String first_name;
    private String last_name;
    private String address;
    private String email;
    private int mobile;
    private int account_status;
    private ArrayList<String> categories;

    public SupplierRequest() {
    }

    public SupplierRequest(String first_name, String last_name, String address, String email, int mobile, int account_status, ArrayList<String> categories) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
        this.account_status = account_status;
        this.categories = categories;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public int getAccount_status() {
        return account_status;
    }

    public void setAccount_status(int account_status) {
        this.account_status = account_status;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }
}
