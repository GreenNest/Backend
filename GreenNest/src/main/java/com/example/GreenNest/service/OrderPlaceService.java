package com.example.GreenNest.service;

import com.example.GreenNest.model.OrderInfo;
import com.example.GreenNest.model.PaymentInfo;
import com.example.GreenNest.repository.OrderInfoRepository;
import com.example.GreenNest.repository.PaymentInfoRepository;
import com.example.GreenNest.request.OrderPlaceRequest;
import com.example.GreenNest.response.OrderPlaceResponse;
import com.example.GreenNest.utils.PaymentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderPlaceService {

    @Autowired
    private OrderInfoRepository orderInfoRepository;
    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    public OrderPlaceResponse placeOrder(OrderPlaceRequest request){
//        OrderPlaceResponse response=null;

        OrderInfo orderInfo=request.getOrderInfo();
        orderInfo=orderInfoRepository.save(orderInfo);

        PaymentInfo paymentInfo= request.getPaymentInfo();

        PaymentUtils.validateCreditLimit(paymentInfo.getAccountNo(), orderInfo.getFare());

        paymentInfo.setOrderId((orderInfo.getOId()));
        paymentInfo.setAmount(orderInfo.getFare());
        paymentInfoRepository.save(paymentInfo);
        return new OrderPlaceResponse("Success", orderInfo.getFare(), UUID.randomUUID().toString().split("-")[0],orderInfo);
    }
}
