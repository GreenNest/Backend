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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public int addSupplier(@RequestBody SupplierRequest supplierRequest) {
        String supplierEmail = supplierRepository.getProfileEmail(supplierRequest.getEmail());
        System.out.println(supplierEmail);

        if(supplierEmail == null) {
            supplierService.addSupplier(supplierRequest);
            return 1;
        }else {
            return 0;
        }
    }

    //edit supplier
    @PutMapping("/editSupplier/{id}")
    public int editSupplier(@RequestBody SupplierRequest supplierRequest, @PathVariable int id){
        String supplier_id = supplierRepository.getProfileId(supplierRequest.getEmail());
        String s_id = Integer.toString(id);

        if(supplier_id.equals(s_id) || supplier_id == null) {
            SupplierDetails supplierDetails = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not exist"));
//          System.out.println(supplierRequest.getCategories());
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

            return 1;

        }else {
            return 0;
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

    //get supplier by id
    @GetMapping(value = "/supplierById/{id}")
    public ResponseEntity<Object> getSupplierById(@PathVariable int id){
        try {
            Optional<SupplierDetails> supplierDetails = supplierRepository.findById(id);

            SupplierResponse supplierResponse = new SupplierResponse();
            supplierResponse.setId(supplierDetails.get().getSupplier_id());
            supplierResponse.setFirst_name(supplierDetails.get().getFirst_name());
            supplierResponse.setLast_name(supplierDetails.get().getLast_name());
            supplierResponse.setAddress(supplierDetails.get().getAddress());
            supplierResponse.setEmail(supplierDetails.get().getEmail());
            supplierResponse.setMobile(supplierDetails.get().getMobile());

            return ResponseHandle.response("successfully get the supplier.", HttpStatus.OK, supplierResponse);
        }catch (Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

}
