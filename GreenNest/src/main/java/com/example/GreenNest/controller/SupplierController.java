package com.example.GreenNest.controller;

import com.example.GreenNest.exception.ResourceNotFoundException;
import com.example.GreenNest.model.Category;
import com.example.GreenNest.model.SupplierDetails;
import com.example.GreenNest.repository.CategoryRepository;
import com.example.GreenNest.repository.SupplierRepository;
import com.example.GreenNest.request.ProductDetails;
import com.example.GreenNest.request.SupplierRequest;
import com.example.GreenNest.response.ProductResponse;
import com.example.GreenNest.response.ResponseHandle;
import com.example.GreenNest.response.SupplierByCategoryResponse;
import com.example.GreenNest.response.SupplierResponse;
import com.example.GreenNest.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class SupplierController {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private CategoryRepository categoryRepository;

    //get all suppliers
    @GetMapping("/getSuppliers")
    public ResponseEntity<?> getSuppliers(){
        List<SupplierDetails> supplierDetails = supplierRepository.findAll();
        ArrayList<SupplierResponse> supplierResponses = supplierService.createResponse(supplierDetails);
        return ResponseEntity.ok().body(supplierResponses);
    }

    //add supplier
    @PostMapping("/addSupplier")
    public ResponseEntity<Object> addSupplier(@RequestBody SupplierRequest supplierRequest) {
        try {
//          System.out.println(supplierRequest.getCategories());
            supplierService.addSupplier(supplierRequest);
            return ResponseHandle.response("successfully added data", HttpStatus.OK, null);
        }catch (Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //delete suppliers
    @PutMapping("/deleteSupplier/{id}")
    public int deleteSupplier(@PathVariable int id){
        SupplierDetails supplierDetails = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not exist"));

        supplierDetails.setAccount_status(1);
        SupplierDetails deleteSupplier = supplierRepository.save(supplierDetails);

        if(deleteSupplier.getAccount_status() == 1){
            return  1;
        }
        return 0;
    }

    //get suppliers by category
    @GetMapping(value = "/suppliersByCategory")
    public ResponseEntity<Object> getSuppliersByCategory(){
        try {
            List<Category> categories = categoryRepository.findAll();
            List<SupplierByCategoryResponse> supplierByCategoryResponses = supplierService.getSuppliers(categories);

            return ResponseHandle.response("successfully get the categories.", HttpStatus.OK, supplierByCategoryResponses);
        }catch (Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

}
