package com.example.GreenNest.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    Long category_id;

    @Column(name = "categoryName")
    String categoryName;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<SupplierDetails> supplierDetails = new HashSet<>();

    public Category() {
    }

    public Category(String category_name) {
        this.categoryName = category_name;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return categoryName;
    }

    public void setCategory_name(String category_name) {
        this.categoryName = category_name;
    }

    public Set<SupplierDetails> getSupplierDetails() {
        return supplierDetails;
    }

    public void setSupplierDetails(Set<SupplierDetails> supplierDetails) {
        this.supplierDetails = supplierDetails;
    }
}
