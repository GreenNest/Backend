package com.example.GreenNest.controller;

import com.example.GreenNest.model.Customer;
import com.example.GreenNest.model.OrderDetails;
import com.example.GreenNest.model.OrderItems;
import com.example.GreenNest.model.Product;
import com.example.GreenNest.repository.OrderItemRepository;
import com.example.GreenNest.response.ReportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class ReportController {

    @Autowired
    private OrderItemRepository orderItemRepository;

    //get report
    @GetMapping("/getReport/{product_name}")
    public List<ReportResponse> getReport(@PathVariable String product_name, @PathVariable Date from, @PathVariable Date to){
        List<ReportResponse> reportResponses = new ArrayList<ReportResponse>();
        List<OrderItems> orderItems = orderItemRepository.findAll();
        for(int i=0; i<orderItems.size(); i++){
            Product product = orderItems.get(i).getProduct();
            OrderDetails orderDetails = orderItems.get(i).getOrderDetails();
            System.out.println(orderDetails.getDate());
            if(product.getProduct_name().equals(product_name)
//                    && orderDetails.getDate().before(to) && orderDetails.getDate().after(from)
            ){
                ReportResponse reportResponse = new ReportResponse();
                reportResponse.setQuantity(orderItems.get(i).getQuantity());

                reportResponse.setAddress(orderDetails.getAddress());
                reportResponse.setCity(orderDetails.getCity());
                Customer customer = orderDetails.getCustomer();

                reportResponse.setFirst_name(customer.getFirst_name());
                reportResponse.setLast_name(customer.getLast_name());
                reportResponse.setMobile(customer.getMobile());

                reportResponses.add(reportResponse);
            }
        }
        return reportResponses;
    }
}
