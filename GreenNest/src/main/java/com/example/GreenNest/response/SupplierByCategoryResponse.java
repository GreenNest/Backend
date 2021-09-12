package com.example.GreenNest.response;

import java.util.ArrayList;

public class SupplierByCategoryResponse {
    private Long category_id;
    private String categoryName;
    private ArrayList<SupplierResponse> suppliers;

    public SupplierByCategoryResponse() {
    }

    public SupplierByCategoryResponse(String categoryName, ArrayList<SupplierResponse> suppliers) {
        this.categoryName = categoryName;
        this.suppliers = suppliers;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ArrayList<SupplierResponse> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(ArrayList<SupplierResponse> suppliers) {
        this.suppliers = suppliers;
    }
}
