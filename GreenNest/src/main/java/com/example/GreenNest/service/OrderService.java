package com.example.GreenNest.service;

import com.example.GreenNest.model.*;
import com.example.GreenNest.repository.CustomerRepository;
import com.example.GreenNest.repository.OrderDetailsRepository;
import com.example.GreenNest.repository.OrderInfoRepository;
import com.example.GreenNest.repository.PaymentInfoRepository;
import com.example.GreenNest.request.OrderPlaceRequest;
import com.example.GreenNest.request.ProductDetails;
import com.example.GreenNest.response.OrderPlaceResponse;
import com.example.GreenNest.utils.PaymentUtils;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private CustomerRepository customerRepository;


 public OrderDetails addOrder(OrderDetails orderDetails) {
    OrderDetails order = new OrderDetails();
    Optional<Customer> customer = customerRepository.findById(orderDetails.getCustomer().getCustomer_id());
    //System.out.println(orderDetails.getCustomer().getCustomer_id());
    order.setOrder_type(orderDetails.getOrder_type());
    order.setTotal_price(orderDetails.getTotal_price());
    order.setDate(orderDetails.getDate());
    order.setAddress(orderDetails.getAddress());
    order.setState(orderDetails.getState());
    order.setCity(orderDetails.getCity());
    order.setPostal_code(orderDetails.getPostal_code());
    order.setMobile(orderDetails.getMobile());
    order.setOrder_status("Pending");
    order.setCustomer(customer.get());
    OrderDetails orderDetails1 = orderDetailsRepository.save(order);
    System.out.println(orderDetails1.getOrder_id());
    return orderDetails1;

}
}
