package com.example.GreenNest.repository;

import com.example.GreenNest.model.Category;
import com.example.GreenNest.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByFeatured(boolean x);
}
