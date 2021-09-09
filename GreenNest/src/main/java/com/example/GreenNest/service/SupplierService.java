package com.example.GreenNest.service;

import com.example.GreenNest.model.Category;
import com.example.GreenNest.model.Product;
import com.example.GreenNest.model.SupplierDetails;
import com.example.GreenNest.repository.SupplierRepository;
import com.example.GreenNest.response.CategoryResponse;
import com.example.GreenNest.response.ProductResponse;
import com.example.GreenNest.response.SupplierResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private CategoryService categoryService;

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
}
