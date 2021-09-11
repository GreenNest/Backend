package com.example.GreenNest.controller;

import com.example.GreenNest.exception.ResourceNotFoundException;
import com.example.GreenNest.model.Category;
import com.example.GreenNest.model.Product;
import com.example.GreenNest.model.SupplierDetails;
import com.example.GreenNest.repository.CategoryRepository;
import com.example.GreenNest.repository.ProductRepository;
import com.example.GreenNest.response.CategoryResponse;
import com.example.GreenNest.response.SupplierResponse;
import com.example.GreenNest.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    //delete product
    @PutMapping("/deleteProduct/{id}")
    public int deleteProduct(@PathVariable long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not exist"));

        product.setProduct_status(1);
        Product deleteProduct = productRepository.save(product);

        if(deleteProduct.getProduct_status() == 1){
            return  1;
        }
        return 0;
    }

}
