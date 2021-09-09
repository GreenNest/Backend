package com.example.GreenNest.controller;

import com.example.GreenNest.model.Category;
import com.example.GreenNest.repository.CategoryRepository;
import com.example.GreenNest.response.ResponseHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.monitor.StringMonitor;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    //get all categories
    @GetMapping("/get/categories")
    public ResponseEntity<?> getAllCategories(){
        try {
            ArrayList<String> categories = categoryRepository.getCategory();
            return ResponseHandle.response("successfully get the categories.", HttpStatus.OK, categories);
        }catch (Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //add category
    @GetMapping("/addCategory/{id}")
    public int addCategory(@PathVariable("id") String categoryName){
        Category category = new Category();
        category.setCategory_name(categoryName);
        Category saveCategory = categoryRepository.save(category);

        if(saveCategory.getCategory_name() != null){
            return 1;
        }
        return 0;
    }
}
