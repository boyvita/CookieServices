package com.github.boyvita.services.payment.controller;

import com.github.boyvita.services.model.Client;
import com.github.boyvita.services.model.Item;
import com.github.boyvita.services.model.Order;
import com.github.boyvita.services.exception.NoEntityException;
import com.github.boyvita.services.model.Product;
import com.github.boyvita.services.repo.ClientRepository;
import com.github.boyvita.services.repo.ItemRepository;
import com.github.boyvita.services.repo.OrderRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@RequestMapping("accounting")
@ComponentScan(basePackages = "com.github.boyvita.services")
@EnableJpaRepositories(basePackages = "com.github.boyvita.services.repo")
@EntityScan(basePackages = "com.github.boyvita.services.model")
public class PaymentController {

    private OrderRepository orderRepository;
    private ItemRepository itemRepository;
    private ClientRepository clientRepository;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    public PaymentController(OrderRepository orderRepository, ItemRepository itemRepository, ClientRepository clientRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.clientRepository = clientRepository;
    }

    @PostMapping("/pay")
    public Order payOrder() {
        // ID obtained after executing /catalog/pay
        Order unproxiedOrder = (Order) Hibernate.unproxy(orderRepository.getOne((long) 32768));
        rabbitMQSender.send(unproxiedOrder);
        return unproxiedOrder;
    }

}
