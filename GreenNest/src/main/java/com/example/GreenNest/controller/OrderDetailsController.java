package com.example.GreenNest.controller;

import com.example.GreenNest.exception.ResourceNotFoundException;
import com.example.GreenNest.model.*;
import com.example.GreenNest.repository.EmployeeRepository;
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

    @Autowired
    private EmployeeRepository employeeRepository;

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

    //get processing order by delivery person
    @GetMapping("/getProcessingOrderDetails/{nic}")
    public List<DPOrderResponse> getOrderDetails(@PathVariable String nic){
        List<OrderDetails> orderDetails = orderDetailsRepository.findAll();
        List<DPOrderResponse> dpOrderResponses = new ArrayList<DPOrderResponse>();

        for(int i=0; i<orderDetails.size(); i++){
            System.out.println(orderDetails.get(i).getDelivery_id());

            if((orderDetails.get(i).getDelivery_id().contains(nic))
                    && (orderDetails.get(i).getOrder_status().contains("Processing"))){

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
                System.out.println(orderDetails.get(i).getOrder_id());
                dpOrderResponses.add(dpOrderResponse);
            }
        }

        return dpOrderResponses;
    }

//    @GetMapping("/getDeliveredOrderDetails/{nic}")
//    public List<DPOrderResponse> getDeliveredOrderDetails(@PathVariable String nic){
//        List<OrderDetails> orderDetails = orderDetailsRepository.findAll();
//        List<DPOrderResponse> dpOrderResponses = new ArrayList<DPOrderResponse>();
//
//        for(int i=0; i<orderDetails.size(); i++){
//            if(orderDetails.get(i).getDelivery_id().equals(nic)
//                    && orderDetails.get(i).getOrder_status().equals("Delivered")){
//                DPOrderResponse dpOrderResponse = new DPOrderResponse();
//                dpOrderResponse.setOrder_id(orderDetails.get(i).getOrder_id());
//                dpOrderResponse.setOrder_type(orderDetails.get(i).getOrder_type());
//                dpOrderResponse.setAddress(orderDetails.get(i).getAddress());
//                dpOrderResponse.setCity(orderDetails.get(i).getCity());
//                dpOrderResponse.setMobile(orderDetails.get(i).getMobile());
//                dpOrderResponse.setTotal_price(orderDetails.get(i).getTotal_price());
//
//                Customer customer = orderDetails.get(i).getCustomer();
//                dpOrderResponse.setFirst_name(customer.getFirst_name());
//                dpOrderResponse.setLast_name(customer.getLast_name());
//
//                dpOrderResponses.add(dpOrderResponse);
//            }
//        }
//
//        return dpOrderResponses;
//    }

    //get Handover order by delivery person
    @GetMapping("/getHandoverOrderDetails/{nic}")
    public List<DPOrderResponse> getHandoverOrderDetails(@PathVariable String nic){
        List<OrderDetails> orderDetails = orderDetailsRepository.findAll();
        List<DPOrderResponse> dpOrderResponses = new ArrayList<DPOrderResponse>();

        for(int i=0; i<orderDetails.size(); i++){
            if(orderDetails.get(i).getDelivery_id().equals(nic)
                    && orderDetails.get(i).getOrder_status().equals("Handover")){
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

    @GetMapping("/getDeliveredOrderDetails/{nic}")
    public List<DPOrderResponse> getDeliveredOrderDetails(@PathVariable String nic){
        List<OrderDetails> orderDetails = orderDetailsRepository.findAll();
        List<DPOrderResponse> dpOrderResponses = new ArrayList<DPOrderResponse>();

        for(int i=0; i<orderDetails.size(); i++){
            if(orderDetails.get(i).getDelivery_id().equals(nic)
                    && orderDetails.get(i).getOrder_status().equals("Delivered")){
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

    //assign delivered
    @PutMapping("/assignDelivered/{id}")
    public boolean assignDelivered(@PathVariable long id){
        OrderDetails orderDetails = orderDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist"));

        if(orderDetails.getOrder_type().equals("online")){
            orderDetails.setOrder_status("Delivered");
        }else{
            orderDetails.setOrder_status("Handover");
        }

        orderDetailsRepository.save(orderDetails);
        return true;
    }

    //delivery person
    @GetMapping("/findDPerson/{nic}")
    public String deliveryP(@PathVariable String nic){
        Employee employee = employeeRepository.findById(nic)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist"));

        String name = (employee.getFirst_name() + " " + employee.getLast_name());

        return name;
    }
}
