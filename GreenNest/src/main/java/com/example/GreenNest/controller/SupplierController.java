package com.example.GreenNest.controller;

import com.example.GreenNest.exception.ResourceNotFoundException;
import com.example.GreenNest.model.Category;
import com.example.GreenNest.model.SupplierDetails;
import com.example.GreenNest.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        List<SupplierDetails> supplierList = supplierRepository.findAll();
        List<SupplierDetails> filterList = new ArrayList<SupplierDetails>();

        for(int i=0; i<supplierList.size(); i++ ){
            int status = supplierList.get(i).getAccount_status();
            if(status == 0){
                filterList.add(supplierList.get(i));
            }
        }
        return filterList;
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

    //delete suppliers
    @PutMapping("/deleteSupplier/{id}")
    public int deleteSupplier(@PathVariable int id){
        SupplierDetails supplierDetails = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist"));

        supplierDetails.setAccount_status(1);
        SupplierDetails deleteSupplier = supplierRepository.save(supplierDetails);

        if(deleteSupplier.getAccount_status() == 1){
            return  1;
        }
        return 0;
    }
}
