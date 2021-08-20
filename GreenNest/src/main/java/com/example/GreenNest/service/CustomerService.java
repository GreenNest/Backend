package com.example.GreenNest.service;

import com.example.GreenNest.model.Customer;

import com.example.GreenNest.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService{


    @Autowired
    private CustomerRepository customerRepository;


    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

}
