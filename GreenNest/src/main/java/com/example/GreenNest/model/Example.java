package com.example.GreenNest.model;


import javax.persistence.*;

@Entity
@Table(name = "example")
public class Example {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "gggg")
    String gggg;

    public Example() {
    }

    public Example(String gggg) {
        this.gggg = gggg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGggg() {
        return gggg;
    }

    public void setGggg(String gggg) {
        this.gggg = gggg;
    }
}
