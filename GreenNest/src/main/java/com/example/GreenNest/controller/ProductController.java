package com.example.GreenNest.controller;

import com.example.GreenNest.exception.ResourceNotFoundException;
import com.example.GreenNest.model.Category;
import com.example.GreenNest.model.Product;
import com.example.GreenNest.model.SupplierDetails;
import com.example.GreenNest.repository.CategoryRepository;
import com.example.GreenNest.repository.ProductRepository;
import com.example.GreenNest.request.ProductDetails;
import com.example.GreenNest.response.CategoryResponse;
import com.example.GreenNest.response.ProductResponse;
import com.example.GreenNest.response.ResponseHandle;
import com.example.GreenNest.response.SupplierResponse;
import com.example.GreenNest.service.CategoryService;
import com.example.GreenNest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

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

    //add product details
    @PostMapping(value = "/add/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> addProduct(@ModelAttribute ProductDetails productDetails) throws IOException {
        try {
            productService.addProduct(productDetails);
            //System.out.println(productDetails.getCategories());

            return ResponseHandle.response("successfully added data", HttpStatus.OK, null);
        }catch (Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //get product by id
    @GetMapping(value = "/get/product/{id}")
    public ResponseEntity<Object> getImage(@PathVariable("id") Long id){
        try {
            ProductResponse productResponse = productService.getSingleProduct(id);
            return ResponseHandle.response("successfully get the product", HttpStatus.OK, productResponse);
        }catch (Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }

    }

    //get featured product
    @GetMapping(value = "/get/featured/{feature}")
    public ResponseEntity<?> getFeaturedProduct(@PathVariable("feature") Boolean feature, HttpServletResponse response) throws IOException {
        List<Product> product = productRepository.findByFeatured(feature);
        ArrayList<ProductResponse> productResponses = productService.createResponse(product);
        return ResponseEntity.ok().body(productResponses);
    }

    //get products by category
    @GetMapping(value = "/product/{category}")
    public ResponseEntity<Object> getProductByCategory(@PathVariable("category") String category){
        try {
            ArrayList<ProductResponse> productResponses = categoryService.getProductList(category);
            return ResponseHandle.response("successfully get the categories.", HttpStatus.OK, productResponses);
        }catch (Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //update the product stock quantity
    @PutMapping(value = "/product/update/{id}/{amount}")
    public Boolean updateProductStock(@PathVariable long id, @PathVariable int amount){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not exist"));
        product.setQuantity(amount);
        Product product1 = productRepository.save(product);
        return product1.getQuantity() == amount;
    }

}
