package com.example.GreenNest.repository;

import com.example.GreenNest.model.Product;
import com.example.GreenNest.model.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Reviews, Long> {

//    @Query("SELECT * FROM Reviews r WHERE r.product.product_id = ?1")
//    List<String> getReviews(long x);

    List<Reviews> findByProduct(Product p);
}
