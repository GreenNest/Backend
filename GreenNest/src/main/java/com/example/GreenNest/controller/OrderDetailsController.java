package com.example.GreenNest.controller;

import com.example.GreenNest.exception.ResourceNotFoundException;
import com.example.GreenNest.model.Customer;
import com.example.GreenNest.model.OrderDetails;
import com.example.GreenNest.model.OrderItems;
import com.example.GreenNest.model.Product;
import com.example.GreenNest.repository.OrderDetailsRepository;
import com.example.GreenNest.repository.OrderItemRepository;
import com.example.GreenNest.response.DPOrderResponse;
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
                    && orderDetails.get(i).getOrder_type().equals("online")) {
                filterOrders.add(orderDetails.get(i));
            }
        }
        return filterOrders;
    }

    //get cash on delivery
    @GetMapping("/getCashOnDelivery")
    public List<OrderDetails> getCashOnDelivery(){
        List<OrderDetails> orderDetails = orderDetailsRepository.findAll();
        List<OrderDetails> filterOrders = new ArrayList<OrderDetails>();
        for(int i=0; i<orderDetails.size(); i++) {
            if(orderDetails.get(i).getDelete_status() == 0
                    && orderDetails.get(i).getOrder_type().equals("cash on delivery")) {
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
                    int quantity = orderItems.get(i).getQuantity();
                Product product = orderItems.get(i).getProduct();
                    orderItemResponse.setProduct_id(product.getProduct_id());
                    orderItemResponse.setProduct_name(product.getProduct_name());
                    double price = product.getPrice();
                    orderItemResponse.setItem_price(price*quantity);
                orderItemResponses.add(orderItemResponse);
            }
        }
        return orderItemResponses;
    }

    //assign delivery person to order
    @GetMapping("/assignDPerson/{order_id}/{nic}/{eid}")
    public int assignDPerson(@PathVariable long order_id, @PathVariable String nic, @PathVariable String eid){
        System.out.println(nic);
        OrderDetails orderDetails = orderDetailsRepository.findById(order_id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist"));
        orderDetails.setDelivery_id(nic);
        orderDetails.setOrder_status("Processing");
        orderDetails.setEmployee_id(eid);
        orderDetailsRepository.save(orderDetails);
        return 1;
    }

    //get order by delivery person
    @GetMapping("/getOrderDetails/{nic}")
    public List<DPOrderResponse> getOrderDetails(@PathVariable String nic){
        List<OrderDetails> orderDetails = orderDetailsRepository.findAll();
        List<DPOrderResponse> dpOrderResponses = new ArrayList<DPOrderResponse>();

        for(int i=0; i<orderDetails.size(); i++){
            if(orderDetails.get(i).getDelivery_id().equals(nic)
                    && orderDetails.get(i).getOrder_status().equals("Processing")){
                DPOrderResponse dpOrderResponse = new DPOrderResponse();
                dpOrderResponse.setOrder_id(orderDetails.get(i).getOrder_id());
                dpOrderResponse.setOrder_type(orderDetails.get(i).getOrder_type());
                dpOrderResponse.setAddress(orderDetails.get(i).getAddress());
                dpOrderResponse.setCity(orderDetails.get(i).getCity());
                dpOrderResponse.setMobile(orderDetails.get(i).getMobile());
                dpOrderResponse.setTotal_price(orderDetails.get(i).getTotal_price());

                Customer customer = orderDetails.get(i).getCustomer();
                dpOrderResponse.setFirst_name(customer.getFirst_name());
                dpOrderResponse.setLast_name(customer.getLast_name());

                dpOrderResponses.add(dpOrderResponse);
            }
        }

        return dpOrderResponses;
    }
}
