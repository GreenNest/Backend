package com.example.GreenNest.controller;

import com.example.GreenNest.model.Category;
import com.example.GreenNest.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.monitor.StringMonitor;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    //get all categories
    @GetMapping("/getCategories")
    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    //add category
//    @PostMapping("/addCategory")
//    public int addCategory(@RequestBody Category ctr){
//        String name = "kamal";
////        System.out.println(name);
////        categoryName.setCategory_name(name);
////        System.out.println(categoryName.setCategory_name(name));
//        Category category = categoryRepository.save(ctr);
//
//        if(category.getCategory_name() != null){
//            return 1;
//        }
//        return 0;
//    }

    @GetMapping("/addCategory/{id}")
    public int addCategory(@PathVariable("id") String ctgName){
        System.out.println(ctgName);
        Category ctr = new Category();
        ctr.setCategory_name(ctgName);
        Category ctr1 = categoryRepository.save(ctr);
//
        if(ctr1.getCategory_name() != null){
            return 1;
        }
        return 0;
    }
}
