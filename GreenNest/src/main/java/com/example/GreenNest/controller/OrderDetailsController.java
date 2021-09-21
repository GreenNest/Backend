package com.example.GreenNest.controller;

import com.example.GreenNest.exception.ResourceNotFoundException;
import com.example.GreenNest.model.OrderDetails;
import com.example.GreenNest.model.OrderItems;
import com.example.GreenNest.model.Product;
import com.example.GreenNest.repository.OrderDetailsRepository;
import com.example.GreenNest.repository.OrderItemRepository;
import com.example.GreenNest.response.OrderItemResponse;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderDetailsController {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    //get online payments
    @GetMapping("/getOnlinePayments")
    public List<OrderDetails> getOnlinePayment(){
        List<OrderDetails> orderDetails = orderDetailsRepository.findAll();
        List<OrderDetails> filterOrders = new ArrayList<OrderDetails>();
        for(int i=0; i<orderDetails.size(); i++) {
            if(orderDetails.get(i).getDelete_status() == 0
                    && orderDetails.get(i).getOrder_type().equals("onlinepayment")) {
                filterOrders.add(orderDetails.get(i));
            }
        }
        return filterOrders;
    }

    //get cashon delivery
    @GetMapping("/getCashonDelivery")
    public List<OrderDetails> getCashonDelivery(){
        List<OrderDetails> orderDetails = orderDetailsRepository.findAll();
        List<OrderDetails> filterOrders = new ArrayList<OrderDetails>();
        for(int i=0; i<orderDetails.size(); i++) {
            if(orderDetails.get(i).getDelete_status() == 0
                    && orderDetails.get(i).getOrder_type().equals("cashondelivery")) {
                filterOrders.add(orderDetails.get(i));
            }
        }
        return filterOrders;
    }

    //get items by order_id
    @GetMapping("/getOrderItems/{id}")
    public List<OrderItemResponse> getOrderItems(@PathVariable long id) {
        List<OrderItems> orderItems = orderItemRepository.findAll();
        List<OrderItemResponse> orderItemResponses = new ArrayList<OrderItemResponse>();
        for(int i=0; i<orderItems.size(); i++) {
            OrderDetails OD = orderItems.get(i).getOrderDetails();
            if(OD.getOrder_id() == id){
                OrderItemResponse orderItemResponse = new OrderItemResponse();
                    orderItemResponse.setItem_id(orderItems.get(i).getItem_id());
                OrderDetails orderDetails = orderItems.get(i).getOrderDetails();
                    orderItemResponse.setOrder_id(orderDetails.getOrder_id());
                    orderItemResponse.setQuantity(orderItems.get(i).getQuantity());
                Product product = orderItems.get(i).getProduct();
                    orderItemResponse.setProduct_id(product.getProduct_id());
                    orderItemResponse.setProduct_name(product.getProduct_name());
                orderItemResponses.add(orderItemResponse);
            }
        }
        return orderItemResponses;
    }

    //assign delivery person to order
    @GetMapping("/assignDPerson/{order_id}/{nic}")
    public int assignDPerson(@PathVariable long order_id, @PathVariable String nic){
        System.out.println(nic);
        OrderDetails orderDetails = orderDetailsRepository.findById(order_id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist"));
        orderDetails.setDelivery_id(nic);
        orderDetailsRepository.save(orderDetails);
        return 1;
    }
}
