package com.example.GreenNest.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    long product_id;
    @Column(name = "product_name")
    String product_name;
    @Column(name = "description")
    String description;
    @Column(name = "price")
    double price;
    @Column(name = "qauatity")
    int quantity;
    @Column(name = "featured")
    boolean featured;
    @Column(name = "reorder_level")
    int reorder_level;
    @Column(name = "product_status", columnDefinition = "integer default 1")
    int product_status;

    @Lob
    @Column(name = "content",columnDefinition = "LONGBLOB")
    byte[] content;

    @OneToMany( mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<ProductImages> productImages;

    public Product() {
    }

    public Product(long product_id, String product_name, String description, double price, int quantity, boolean featured, int reorder_level, int product_status, byte[] content, Set<ProductImages> productImages) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.featured = featured;
        this.reorder_level = reorder_level;
        this.product_status = product_status;
        this.content = content;
        this.productImages = productImages;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public int getReorder_level() {
        return reorder_level;
    }

    public void setReorder_level(int reorder_level) {
        this.reorder_level = reorder_level;
    }

    public int getProduct_status() {
        return product_status;
    }

    public void setProduct_status(int product_status) {
        this.product_status = product_status;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public Set<ProductImages> getProductImages() {
        return productImages;
    }

    public void setProductImages(Set<ProductImages> productImages) {
        this.productImages = productImages;
    }
}
