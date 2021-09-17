package com.example.GreenNest.service;

import com.example.GreenNest.model.OrderDetails;
import com.example.GreenNest.model.OrderItems;
import com.example.GreenNest.repository.OrderItemRepository;
import com.example.GreenNest.response.COResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class COService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public ArrayList<COResponse> createCOResponses (List<OrderDetails> orderDetails){
        ArrayList<COResponse> coResponses = new ArrayList<COResponse>();
        for(OrderDetails o : orderDetails){
            List<OrderItems> orderItems = orderItemRepository.findByOrderDetails(o);
            if(o.getDelete_status() == 0){
                COResponse coResponse = new COResponse();
                coResponse.setCost(o.getTotal_price());
                coResponse.setDate(o.getDate());
                coResponse.setId(o.getOrder_id());
                coResponse.setItems(orderItems.size());
                coResponses.add(coResponse);
            }
        }
        return coResponses;
    }
}
