package com.example.GreenNest.repository;

import com.example.GreenNest.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    public Category findByCategoryName(String category_name);

    @Query("SELECT c.categoryName FROM Category c")
    ArrayList<String> getCategory();


}
