package com.example.GreenNest.repository;

import com.example.GreenNest.model.Category;
import com.example.GreenNest.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByFeatured(boolean x);
    List<Product> findProductsByCategoriesContains(Category category);

    @Query("UPDATE Product p SET p.quantity = ?1 WHERE p.product_id = ?2")
    public void  updateStock(int amount, long id);

}
