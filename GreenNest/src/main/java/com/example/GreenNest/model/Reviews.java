package com.example.GreenNest.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reviews_rating")
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private long reviewsId;

    @Column(name = "review", columnDefinition = "TEXT")
    private String review;

    @Column(name = "rating")
    private int rating;

    @Column(name = "delete_status", columnDefinition = "integer default 1")
    private int deleteStatus;

    @Column(name = "data", columnDefinition = "DATE")
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    public Reviews() {
    }

    public Reviews(String review, int rating, int deleteStatus, Date date) {
        this.review = review;
        this.rating = rating;
        this.deleteStatus = deleteStatus;
        this.date = date;
    }

    public long getReviewsId() {
        return reviewsId;
    }

    public void setReviewsId(long reviewsId) {
        this.reviewsId = reviewsId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
