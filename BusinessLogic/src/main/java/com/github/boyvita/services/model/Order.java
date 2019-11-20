package com.github.boyvita.services.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "accounting", name = "order")
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
public class Order implements Serializable {
    @Id
    @Column(name="order_id")
    @GeneratedValue(strategy=GenerationType.TABLE)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("order")
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable=false)
//    @JsonIgnoreProperties("clients")
    private Client client;

    public Order(Client client) {
        this.client = client;
        this.orderStatus = OrderStatus.COLLECTING;
    }

    public Order() {
    }

    public void addItem(Product product) {
        Item item = new Item(product);
        items.add(item);
        item.setOrder(this);
    }

    public void addItem(Item item) {
        items.add(item);
        item.setOrder(this);
    }

    public void removeItem(Item item) {
        items.remove(item);
        item.setOrder(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<Item> getItems() {
        return items;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
