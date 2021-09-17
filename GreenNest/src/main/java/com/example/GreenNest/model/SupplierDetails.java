package com.example.GreenNest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "supplier_details")
public class SupplierDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int supplier_id;

    @Column(name = "first_name")
    String first_name;

    @Column(name = "last_name")
    String last_name;

    @Column(name = "address")
    String address;

    @Column(name = "email")
    String email;

    @Column(name = "mobile")
    int mobile;

    @Column(name = "account_status")
    int account_status;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(name = "supplier_category", joinColumns = @JoinColumn(referencedColumnName = "supplier_id"), inverseJoinColumns = @JoinColumn(referencedColumnName = "category_id"))
//    private List<Category> categories;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
//    @JsonIgnore
    @JoinTable(name = "supplier_category", joinColumns = @JoinColumn(referencedColumnName = "supplier_id", nullable = false),
            inverseJoinColumns = @JoinColumn(referencedColumnName = "category_id", nullable = false))
    Set<Category> categories = new HashSet<>();

    public SupplierDetails() {
    }

    public SupplierDetails(String first_name, String last_name, String address, String email, int mobile, int account_status) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
        this.account_status = account_status;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public int getAccount_status() { return account_status; }

    public void setAccount_status(int account_status) { this.account_status = account_status; }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

}
