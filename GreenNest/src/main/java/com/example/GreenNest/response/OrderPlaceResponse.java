package com.example.GreenNest.response;

import com.example.GreenNest.model.OrderInfo;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlaceResponse {

    private String status;
    private double totalFare;
    private String pnrNo;
    private OrderInfo orderInfo;
}
