package com.example.GreenNest.service;

import com.example.GreenNest.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class CustomerService{


    @Autowired
    private CustomerRepository customerRepository;

    //password hash


}
