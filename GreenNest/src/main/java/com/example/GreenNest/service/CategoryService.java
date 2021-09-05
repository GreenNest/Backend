package com.example.GreenNest.service;

import com.example.GreenNest.model.Category;
import com.example.GreenNest.model.Product;
import com.example.GreenNest.repository.CategoryRepository;
import com.example.GreenNest.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    public ArrayList<ProductResponse> getProductList(String name){
        Category category = categoryRepository.findByCategoryName(name);
        List<Product> products = new ArrayList<Product>();
        products.addAll(category.getProducts());
        ArrayList<ProductResponse> productResponses = productService.createResponse(products);
        return productResponses;
    }
}
