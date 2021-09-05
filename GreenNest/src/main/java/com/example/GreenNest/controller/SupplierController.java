package com.example.GreenNest.controller;

import com.example.GreenNest.model.Category;
import com.example.GreenNest.model.SupplierDetails;
import com.example.GreenNest.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class SupplierController {

    @Autowired
    private SupplierRepository supplierRepository;

    //get all suppliers
    @GetMapping("/getSuppliers")
    public List<SupplierDetails> getSuppliers(){
        return supplierRepository.findAll();
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

    //add used categories
//    @GetMapping("/usedCategoryAdd/{id}")
//    public int saveSupplierCategories(@PathVariable List<String> categories){
//        SupplierDetails addCategory = new SupplierDetails();
//        Category category = new Category("pot");
//
//        for(int i=0; i<categories.size(); i++) {
//
//        }
//
//        return 1;
//    }
}
