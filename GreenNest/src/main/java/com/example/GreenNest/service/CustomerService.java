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


//    @Autowired
//    private PasswordEncoder bcryptEncoder;


    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

//    public Customer addCustomer(Customer customer){
//        Customer customer1 = new Customer();
//        customer1.setFirst_name(customer.getFirst_name());
//        customer1.setLast_name(customer.getLast_name());
//        customer1.setEmail(customer.getEmail());
//        customer1.setPassword(bcryptEncoder.encode(customer.getPassword()));
//        customer1.setMobile(customer.getMobile());
//        customer1.setRole(customer.getRole());
//        System.out.println(customer1.getPassword());
//
//        System.out.println(customer1.getPassword());
//
//        customerRepository.save(customer1);
//
////        User user = new User();
////        user.setUsername(customer.getEmail());
////        user.setPassword(customer1.getPassword());
////        user.setRole(customer.getRole());
////        userRepository.save(user);
////
////        System.out.println(user.getUsername());
////        System.out.println(user.getPassword());
//
//        return customer1;
//    }





}
