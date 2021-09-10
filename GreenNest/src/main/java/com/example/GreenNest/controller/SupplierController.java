package com.example.GreenNest.controller;

import com.example.GreenNest.exception.ResourceNotFoundException;
import com.example.GreenNest.model.Category;
import com.example.GreenNest.model.SupplierDetails;
import com.example.GreenNest.repository.CategoryRepository;
import com.example.GreenNest.repository.SupplierRepository;
import com.example.GreenNest.response.ProductResponse;
import com.example.GreenNest.response.ResponseHandle;
import com.example.GreenNest.response.SupplierResponse;
import com.example.GreenNest.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class SupplierController {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierService supplierService;

    //get all suppliers
    @GetMapping("/getSuppliers")
    public ResponseEntity<?> getSuppliers(){
        List<SupplierDetails> supplierDetails = supplierRepository.findAll();
        ArrayList<SupplierResponse> supplierResponses = supplierService.createResponse(supplierDetails);
        return ResponseEntity.ok().body(supplierResponses);
    }

    //add supplier
    @PostMapping("/addSupplier")
    public int saveSupplier(@RequestBody SupplierDetails supplier){
        SupplierDetails supplierDetails = supplierRepository.save(supplier);

        if (supplierDetails.getFirst_name() != null){
            return supplierDetails.getSupplier_id();
        }
        return 0;
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

}
