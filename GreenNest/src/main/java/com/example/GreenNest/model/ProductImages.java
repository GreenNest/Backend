package com.example.GreenNest.model;

import javax.persistence.*;

@Entity
@Table(name = "product_images")
public class ProductImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    long image_id;
    @Column(name = "imae_content")
    byte[] image_content;
//    @ManyToOne(fetch = FetchType.EAGER, optional = false)
//    @JoinColumn(name = "images_id")
//    Product product;

    public ProductImages() {
    }

    public ProductImages(byte[] image_content) {
        this.image_content = image_content;
    }

    public long getImage_id() {
        return image_id;
    }

    public void setImage_id(long image_id) {
        this.image_id = image_id;
    }

    public byte[] getImage_content() {
        return image_content;
    }

    public void setImage_content(byte[] image_content) {
        this.image_content = image_content;
    }
}
