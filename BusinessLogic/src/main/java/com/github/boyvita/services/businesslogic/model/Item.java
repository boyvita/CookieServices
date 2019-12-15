package com.github.boyvita.services.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "catalog", name = "item")
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
public class Item implements Serializable  {
    @Id
    @Column(name="item_id")
    @GeneratedValue(strategy=GenerationType.TABLE)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private ItemStatus itemStatus;


    @Column(name="order_id")
    private Long orderId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable=false)
    private Product product;

    public Item(Product product) {
        this.product = product;
        this.itemStatus = ItemStatus.PAID;
    }

    public Item() {
        this.itemStatus = ItemStatus.PAID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemStatus getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(ItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
