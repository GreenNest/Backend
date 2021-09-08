package com.example.GreenNest.request;

import com.example.GreenNest.model.OrderInfo;
import com.example.GreenNest.model.PaymentInfo;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlaceRequest {

    private OrderInfo orderInfo;

    private PaymentInfo paymentInfo;
}
