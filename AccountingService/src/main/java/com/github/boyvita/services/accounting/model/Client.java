package com.github.boyvita.services.accounting.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "accounting", name = "client")
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
public class Client implements Serializable {
    @Id
    @Column(name="client_id")
    @GeneratedValue(strategy=GenerationType.TABLE)
    private Long id;

    @Column(name = "name")
    private String name;

    public Client(String name) {
        this.name = name;
    }

    public Client() {
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

//    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
//    @JsonIgnoreProperties("client")
//    private List<Order> orders = new ArrayList<>();
}
