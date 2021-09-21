package com.example.GreenNest.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "order_details")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long order_id;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "email")
    private String email;

    @Column(name = "order_type")
    private String order_type;

    @Column(name = "total_price")
    private double total_price;

    @Column(name = "date", columnDefinition = "DATE")
    private Date date;

    @Column(name = "address")
    private String address;

    @Column(name = "state")
    private String state;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private int postal_code;

    @Column(name = "mobile")
    private int mobile;

    @Column(name = "order_status", columnDefinition = "varchar(255) default 'Pending'")
    private String order_status;

    @Column(name = "delete_status", columnDefinition = "integer default 0")
    private int delete_status;

    @Column(name = "employee_id", columnDefinition = "varchar(255) default '0'")
    private String employee_id;

    @Column(name = "delivery_id", columnDefinition = "varchar(255) default '0'")
    private String delivery_id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Customer customer;

//    @OneToMany( mappedBy = "order_id", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private Set<OrderItems> orderItems;

    public OrderDetails() {
    }

    public OrderDetails(long order_id, String first_name, String last_name, String email, String order_type, double total_price, Date date, String address, String state, String city, int postal_code, int mobile, String order_status, int delete_status, String employee_id, String delivery_id, Customer customer) {
        this.order_id = order_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.order_type = order_type;
        this.total_price = total_price;
        this.date = date;
        this.address = address;
        this.state = state;
        this.city = city;
        this.postal_code = postal_code;
        this.mobile = mobile;
        this.order_status = order_status;
        this.delete_status = delete_status;
        this.employee_id = employee_id;
        this.delivery_id = delivery_id;
        this.customer = customer;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(int postal_code) {
        this.postal_code = postal_code;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public int getDelete_status() {
        return delete_status;
    }

    public void setDelete_status(int delete_status) {
        this.delete_status = delete_status;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(String delivery_id) {
        this.delivery_id = delivery_id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}