package com.example.GreenNest.model;

import javax.persistence.*;

@Entity
@Table(name = "complain")
public class Complain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complain_id")
    private long complainId;

    @Column(name = "complain", columnDefinition = "TEXT")
    private String complain;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "delete_status", columnDefinition = "integer default 0")
    private int deleteStatus;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "order_id")
    private OrderDetails orderDetails;

    public Complain() {
    }

    public Complain(long complainId, String complain, int deleteStatus) {
        this.complainId = complainId;
        this.complain = complain;
        this.deleteStatus = deleteStatus;
    }

    public long getComplainId() {
        return complainId;
    }

    public void setComplainId(long complainId) {
        this.complainId = complainId;
    }

    public String getComplain() {
        return complain;
    }

    public void setComplain(String complain) {
        this.complain = complain;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }
}
