package com.example.GreenNest.response;

public class CategoryResponse {
    private Long category_id;
    private String categoryName;

    public CategoryResponse() {
    }

    public CategoryResponse(Long category_id, String categoryName) {
        this.category_id = category_id;
        this.categoryName = categoryName;
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
}
