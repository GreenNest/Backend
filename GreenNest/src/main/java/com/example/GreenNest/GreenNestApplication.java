package com.example.GreenNest;

import com.example.GreenNest.request.OrderPlaceRequest;
import com.example.GreenNest.response.OrderPlaceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@SpringBootApplication
public class GreenNestApplication {

//	@Autowired
//	private OrderPlaceService service;

//	@PostMapping("/placeOrder")
//	public OrderPlaceResponse placeOrder(@RequestBody OrderPlaceRequest request){
//		return service.placeOrder(request);
//	}

	public static void main(String[] args) {
		SpringApplication.run(GreenNestApplication.class, args);
	}

}
