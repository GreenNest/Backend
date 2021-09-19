package com.example.GreenNest.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
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

    @Lob
    @Column(name = "image1",columnDefinition = "LONGBLOB")
    byte[] image1;

    @Lob
    @Column(name = "image2",columnDefinition = "LONGBLOB")
    byte[] image2;

    @Lob
    @Column(name = "image3",columnDefinition = "LONGBLOB")
    byte[] image3;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "ProductCategory", joinColumns = @JoinColumn(referencedColumnName = "product_id", nullable = false),
    inverseJoinColumns = @JoinColumn(referencedColumnName = "category_id", nullable = false))
    Set<Category> categories = new HashSet<>();


    public Product() {
    }

    public Product(String product_name, String description, double price, int quantity, boolean featured, int reorder_level, int product_status, byte[] content, byte[] image1, byte[] image2, byte[] image3) {
        this.product_name = product_name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.featured = featured;
        this.reorder_level = reorder_level;
        this.product_status = product_status;
        this.content = content;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
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

    public byte[] getImage1() {
        return image1;
    }

    public void setImage1(byte[] image1) {
        this.image1 = image1;
    }

    public byte[] getImage2() {
        return image2;
    }

    public void setImage2(byte[] image2) {
        this.image2 = image2;
    }

    public byte[] getImage3() {
        return image3;
    }

    public void setImage3(byte[] image3) {
        this.image3 = image3;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
