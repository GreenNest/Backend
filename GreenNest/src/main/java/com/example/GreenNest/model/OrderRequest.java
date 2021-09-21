package com.example.GreenNest.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_request")
public class OrderRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private long id;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;


//    @Column(name = "accept_description",  nullable = false, columnDefinition = "varchar(255) default 'We can supply your order'")
//    private String accept_description;


    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "moderatorDelete", columnDefinition = "integer default 0")
    private int  moderatorDelete;

    @Column(name = "customerDelete", columnDefinition = "integer default 0")
    private int customerDelete;

    @Column(name = "moderatorAccept", columnDefinition = "integer default 0")
    private int  moderatorAccept;

    @Column(name = "customerAccept", columnDefinition = "integer default 0")
    private int customerAccept;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public OrderRequest() {
    }

    public OrderRequest(String description, int quantity, String productName, int moderatorDelete, int customerDelete, int moderatorAccept, int customerAccept) {
        this.description = description;;
        this.quantity = quantity;
        this.productName = productName;
        this.moderatorDelete = moderatorDelete;
        this.customerDelete = customerDelete;
        this.moderatorAccept = moderatorAccept;
        this.customerAccept = customerAccept;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getModeratorDelete() {
        return moderatorDelete;
    }

    public void setModeratorDelete(int moderatorDelete) {
        this.moderatorDelete = moderatorDelete;
    }

    public int getCustomerDelete() {
        return customerDelete;
    }

    public void setCustomerDelete(int customerDelete) {
        this.customerDelete = customerDelete;
    }

    public int getModeratorAccept() {
        return moderatorAccept;
    }

    public void setModeratorAccept(int moderatorAccept) {
        this.moderatorAccept = moderatorAccept;
    }

    public int getCustomerAccept() {
        return customerAccept;
    }

    public void setCustomerAccept(int customerAccept) {
        this.customerAccept = customerAccept;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
