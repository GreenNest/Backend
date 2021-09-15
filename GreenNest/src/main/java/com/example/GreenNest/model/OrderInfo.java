package com.example.GreenNest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Table;

import javax.persistence.*;

//@Data
//@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Table(name = "Order_Info")
public class OrderInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oId;
    private String cName;
    private String email;
    private String streetAddress;
    private String city;
    private String province;
    private Integer postalCode;
    private double fare;

    public Long getOId() {
        return oId;
    }

    public void setOId(Long oId) {
        this.oId = oId;
    }

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public OrderInfo(Long oId, String cName, String email, String streetAddress, String city, String province, Integer postalCode, double fare) {
        this.oId = oId;
        this.cName = cName;
        this.email = email;
        this.streetAddress = streetAddress;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.fare = fare;
    }
}
