package com.example.GreenNest.repository;

import com.example.GreenNest.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    public Category findByCategoryName(String category_name);
}
