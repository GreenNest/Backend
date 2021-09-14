package com.example.GreenNest.repository;

import com.example.GreenNest.model.OrderDetails;
import com.example.GreenNest.model.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItems,Long> {

    List<OrderItems> findByOrderDetails(OrderDetails orderDetails);

}
