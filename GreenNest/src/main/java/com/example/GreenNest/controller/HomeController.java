package com.example.GreenNest.controller;

import com.example.GreenNest.model.Customer;
import com.example.GreenNest.model.UserProfile;
import com.example.GreenNest.repository.CustomerRepository;
import com.example.GreenNest.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class HomeController {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
  private PasswordEncoder bcryptEncoder;

    @GetMapping("/customer")
    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

    //add customer
    @PostMapping(value = "/customer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Customer insertCustomer(@RequestBody Customer customer){
        customer.getProfile().setPassword(bcryptEncoder.encode(customer.getProfile().getPassword()));
        System.out.println(customer.getProfile().getPassword());
         return customerRepository.save(customer) ;
    }
    @PostMapping(value = "/customer/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserProfile loginCustomer(@RequestBody UserProfile userProfile){
        System.out.println(userProfile.getEmail());
        return userProfileRepository.findByEmail(userProfile.getEmail());
    }



}
