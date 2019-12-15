package com.github.boyvita.services.catalog.model;


import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "catalog", name = "product")
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
public class Product implements Serializable {
    @Id
    @Column(name="product_id")
    @GeneratedValue(strategy=GenerationType.TABLE)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "description", nullable = false)
    private String description;

    public Product(String name, Double cost, String description) {
        this.name = name;
        this.cost = cost;
        this.description = description;
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
