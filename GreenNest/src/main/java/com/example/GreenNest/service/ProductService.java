package com.example.GreenNest.service;

import com.example.GreenNest.model.Product;
import com.example.GreenNest.repository.ProductRepository;
import com.example.GreenNest.request.ProductDetails;
import com.example.GreenNest.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void addProduct(ProductDetails productDetails) throws IOException {
        Product product = new Product();
        product.setProduct_name(productDetails.getName());
        System.out.println(productDetails.getName());
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
    }

    public ProductResponse getSingleProduct(long id){
        Optional<Product> product = productRepository.findById(id);
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.get().getProduct_id());
        productResponse.setName(product.get().getProduct_name());
        productResponse.setDescription(product.get().getDescription());
        productResponse.setPrice(product.get().getQuantity());
        productResponse.setMainImage(Base64.getEncoder().encodeToString(product.get().getContent()));
        ArrayList<String> images = new ArrayList<>();
        images.add(Base64.getEncoder().encodeToString(product.get().getImage1()));
        images.add(Base64.getEncoder().encodeToString(product.get().getImage2()));
        images.add(Base64.getEncoder().encodeToString(product.get().getImage3()));
        productResponse.setSubImages(images);

        return productResponse;

    }
}
