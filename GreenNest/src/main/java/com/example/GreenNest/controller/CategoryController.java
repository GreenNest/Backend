package com.example.GreenNest.controller;

import com.example.GreenNest.model.Category;
import com.example.GreenNest.model.Product;
import com.example.GreenNest.repository.CategoryRepository;
import com.example.GreenNest.response.ResponseHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.monitor.StringMonitor;
import java.util.ArrayList;
import java.util.HashMap;
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
    @GetMapping("/addCategory/{name}")
    public int addCategory(@PathVariable("name") String name){
        String categoryName = categoryRepository.categoryGet(name);

        if(categoryName == null) {
            Category category = new Category();
            category.setCategory_name(name);
            categoryRepository.save(category);

            return 1;
        } else {
            return 0;
        }
    }


    @GetMapping(value = "/category/count")
    public ResponseEntity<Object> getTheCategoryCount(){
        try{
            List<String> categories = categoryRepository.getCategory();
            HashMap<String, Integer> data = new HashMap<String, Integer>();
            for(String c : categories){
                //System.out.println(c);
                Category category = categoryRepository.findByCategoryName(c);
                List<Product> products = new ArrayList<Product>();
                products.addAll(category.getProducts());
                //System.out.println(products.size());
                data.put(category.getCategory_name(), products.size());
            }


            return ResponseHandle.response("get the count", HttpStatus.OK, data);
        }catch (Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
