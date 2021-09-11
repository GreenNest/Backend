package com.example.GreenNest.service;

import com.example.GreenNest.model.Category;
import com.example.GreenNest.model.Product;
import com.example.GreenNest.model.SupplierDetails;
import com.example.GreenNest.repository.CategoryRepository;
import com.example.GreenNest.repository.SupplierRepository;
import com.example.GreenNest.request.SupplierRequest;
import com.example.GreenNest.response.CategoryResponse;
import com.example.GreenNest.response.ProductResponse;
import com.example.GreenNest.response.SupplierResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    public ArrayList<SupplierResponse> createResponse(List<SupplierDetails> supplierDetails){
        ArrayList<SupplierResponse> supplierResponses = new ArrayList<SupplierResponse>();
        for (SupplierDetails s: supplierDetails){
            if(s.getAccount_status() == 0) {
                SupplierResponse supplierResponse = new SupplierResponse();
                supplierResponse.setId(s.getSupplier_id());
                supplierResponse.setFirst_name(s.getFirst_name());
                supplierResponse.setLast_name(s.getLast_name());
                supplierResponse.setAddress(s.getAddress());
                supplierResponse.setEmail(s.getEmail());
                supplierResponse.setMobile(s.getMobile());

                List<Category> categories = new ArrayList<Category>();
                categories.addAll(s.getCategories());

                ArrayList<CategoryResponse> categoryResponses = categoryService.createResponse(categories);
                supplierResponse.setCategories(categoryResponses);

                supplierResponses.add(supplierResponse);
            }
        }
        return supplierResponses;
    }

    public void addSupplier(SupplierRequest supplierRequest) {
        SupplierDetails supplierDetails = new SupplierDetails();
        supplierDetails.setFirst_name(supplierRequest.getFirst_name());
        supplierDetails.setLast_name(supplierRequest.getLast_name());
        supplierDetails.setAddress(supplierRequest.getAddress());
        supplierDetails.setEmail(supplierRequest.getEmail());
        supplierDetails.setMobile(supplierRequest.getMobile());
        supplierRepository.save(supplierDetails);

        List<Category> categories = new ArrayList<Category>();

        for(int i=0; i<supplierRequest.getCategories().size(); i++) {
            Category category = categoryRepository.findByCategoryName(supplierRequest.getCategories().get(i));
            categories.add(category);
        }

        supplierDetails.getCategories().addAll(categories);
        supplierRepository.save(supplierDetails);
    }
}
