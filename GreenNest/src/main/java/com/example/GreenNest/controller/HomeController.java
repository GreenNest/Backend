package com.example.GreenNest.controller;

import com.example.GreenNest.model.Customer;
import com.example.GreenNest.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class HomeController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customer")
    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

    //add customer
    @PostMapping(value = "/customer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Customer insertCustomer(@RequestBody Customer customer){
         return customerRepository.save(customer);
    }


}
