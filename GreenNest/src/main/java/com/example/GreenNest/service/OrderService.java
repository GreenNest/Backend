package com.example.GreenNest.service;

import com.example.GreenNest.model.*;
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
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
//    @Autowired
//    private PaymentInfoRepository paymentInfoRepository;

//    @Transactional
//            (readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public void addOrder(OrderDetails orderDetails) throws IOException {
    OrderDetails order = new OrderDetails();
    orderDetails.setFirst_name(orderDetails.getFirst_name());
    orderDetails.setLast_name(orderDetails.getLast_name());
    orderDetails.setEmail(orderDetails.getEmail());
    orderDetails.setOrder_type(orderDetails.getOrder_type());
    orderDetails.setTotal_price(orderDetails.getTotal_price());
    orderDetails.setDate(orderDetails.getDate());
    orderDetails.setAddress(orderDetails.getAddress());
    orderDetails.setState(orderDetails.getState());
    orderDetails.setCity(orderDetails.getCity());
    orderDetails.setPostal_code(orderDetails.getPostal_code());
    orderDetails.setMobile(orderDetails.getMobile());
    orderDetails.setOrder_status(orderDetails.getOrder_status());
    orderDetails.setDelete_status(orderDetails.getDelete_status());
    orderDetails.setEmployee_id(orderDetails.getEmployee_id());
    orderDetails.setDelivery_id(orderDetails.getDelivery_id());
    orderDetailsRepository.save(order);


    orderDetailsRepository.save(order);
}
}
