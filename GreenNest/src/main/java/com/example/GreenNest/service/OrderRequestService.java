package com.example.GreenNest.service;

import com.example.GreenNest.model.Category;
import com.example.GreenNest.model.SupplierDetails;
import com.example.GreenNest.repository.CategoryRepository;
import com.example.GreenNest.response.SupplierByCategoryResponse;
import com.example.GreenNest.response.SupplierResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderRequestService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierService supplierService;

    public ArrayList<SupplierByCategoryResponse> getRequestSuppliers(ArrayList<String> categories){
        ArrayList<SupplierByCategoryResponse> supplierByCategoryResponses = new ArrayList<SupplierByCategoryResponse>();
        for(int i=0; i<categories.size(); i++){
            Category category = categoryRepository.findByCategoryName(categories.get(i));
            SupplierByCategoryResponse supplierByCategoryResponse = new SupplierByCategoryResponse();
            supplierByCategoryResponse.setCategory_id(category.getCategory_id());
            supplierByCategoryResponse.setCategoryName(category.getCategory_name());

            List<SupplierDetails> supplierDetails = new ArrayList<SupplierDetails>();
            supplierDetails.addAll(category.getSupplierDetails());

            ArrayList<SupplierResponse> supplierResponses = supplierService.supplierByCategory(supplierDetails);
            supplierByCategoryResponse.setSuppliers(supplierResponses);

            supplierByCategoryResponses.add(supplierByCategoryResponse);
        }
        return supplierByCategoryResponses;
    }
}
