package com.example.GreenNest.service;

import com.example.GreenNest.model.Category;
import com.example.GreenNest.model.Product;
import com.example.GreenNest.repository.CategoryRepository;
import com.example.GreenNest.repository.ProductRepository;
import com.example.GreenNest.request.ProductDetails;
import com.example.GreenNest.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public void addProduct(ProductDetails productDetails) throws IOException {
        ArrayList<Category> categories = new ArrayList<Category>();
        Product product = new Product();
        product.setProduct_name(productDetails.getName());
        product.setDescription(productDetails.getDetails());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getAmount());
        product.setFeatured(productDetails.isIsfeatured());
        product.setReorder_level(productDetails.getReorderLevel());
        product.setContent(productDetails.getFile().getBytes());
        product.setImage1(productDetails.getImage1().getBytes());
        product.setImage2(productDetails.getImage2().getBytes());
        product.setImage3(productDetails.getImage3().getBytes());
        productRepository.save(product);
        List<Category> categories1= new ArrayList<Category>();

        for(int i=0;i<productDetails.getCategories().size();i++){
            System.out.println(productDetails.getCategories().get(i));
            Category category = categoryRepository.findByCategoryName(productDetails.getCategories().get(i));
            categories1.add(category);
        }
        for(Category c:categories1){
            System.out.println(c.getCategory_id());
        }
        //product.setCategories(categories1);
        product.getCategories().addAll(categories1);


        productRepository.save(product);
    }

    public ProductResponse getSingleProduct(long id){
        Optional<Product> product = productRepository.findById(id);
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.get().getProduct_id());
        productResponse.setName(product.get().getProduct_name());
        productResponse.setDescription(product.get().getDescription());
        productResponse.setPrice(product.get().getQuantity());
        productResponse.setAmount(product.get().getQuantity());
        productResponse.setMainImage(Base64.getEncoder().encodeToString(product.get().getContent()));
        ArrayList<String> images = new ArrayList<>();
        images.add(Base64.getEncoder().encodeToString(product.get().getImage1()));
        images.add(Base64.getEncoder().encodeToString(product.get().getImage2()));
        images.add(Base64.getEncoder().encodeToString(product.get().getImage3()));
        productResponse.setSubImages(images);

        return productResponse;

    }
}
