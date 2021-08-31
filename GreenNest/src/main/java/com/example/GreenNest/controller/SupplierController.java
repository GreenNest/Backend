package com.example.GreenNest.controller;

import com.example.GreenNest.model.SupplierDetails;
import com.example.GreenNest.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class SupplierController {

    @Autowired
    private SupplierRepository supplierRepository;

    @PostMapping("/addSupplier")
    public int saveSupplier(@RequestBody SupplierDetails supplier){
        SupplierDetails supplierDetails = supplierRepository.save(supplier);

        if (supplierDetails.getFirst_name() != null){
            return 1;
        }
        return 0;
    }
}
