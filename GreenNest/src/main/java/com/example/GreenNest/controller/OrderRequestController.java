package com.example.GreenNest.controller;

import com.example.GreenNest.exception.ResourceNotFoundException;
import com.example.GreenNest.model.OrderRequest;
import com.example.GreenNest.model.Product;
import com.example.GreenNest.model.SupplierDetails;
import com.example.GreenNest.repository.OrderRequestRepository;
import com.example.GreenNest.repository.ProductRepository;
import com.example.GreenNest.response.ProductResponse;
import com.example.GreenNest.response.ResponseHandle;
import com.example.GreenNest.response.SupplierByCategoryResponse;
import com.example.GreenNest.service.OrderRequestService;
import com.example.GreenNest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderRequestController {

    @Autowired
    private OrderRequestRepository orderRequestRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRequestService orderRequestService;

    //get all order requests
    @GetMapping("/getOrderRequests")
    public List<OrderRequest> getOrderRequests() {
        List<OrderRequest> orderRequests = orderRequestRepository.findAll();
        List<OrderRequest> filterOrderRequest = new ArrayList<OrderRequest>();
        for(OrderRequest o: orderRequests) {
            if(o.getModeratorDelete() == 0){
                filterOrderRequest.add(o);
            }
        }

        return filterOrderRequest;
    }

    //get request product suppliers
    @GetMapping("/suppliersByRequest/{productName}")
    public ResponseEntity<Object> getRequestSuppliers(@PathVariable String productName) {
        try {
            long product_id = productRepository.requestProduct(productName);
            ProductResponse productResponse = productService.getSingleProduct(product_id);
            ArrayList<SupplierByCategoryResponse> supplierByCategoryResponses = orderRequestService.getRequestSuppliers(productResponse.getCategories());
            return ResponseHandle.response("successfully get the supplier.", HttpStatus.OK, supplierByCategoryResponses);
        }catch (Exception e){
            return ResponseHandle.response(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //Accept request by moderator
    @PutMapping("/checkedRequest/{request_id}")
    public int checkedRequest(@PathVariable long request_id){
        OrderRequest orderRequest = orderRequestRepository.findById(request_id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not exist"));
        orderRequest.setModeratorAccept(1);
        orderRequestRepository.save(orderRequest);

        return 1;
    }

    //Decline request by moderator
    @GetMapping("/declineRequest/{request_id}")
    public int declineRequest(@PathVariable long request_id){
        OrderRequest orderRequest = orderRequestRepository.findById(request_id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not exist"));
        orderRequest.setModeratorDelete(1);
        orderRequestRepository.save(orderRequest);

        return 1;
    }
}
