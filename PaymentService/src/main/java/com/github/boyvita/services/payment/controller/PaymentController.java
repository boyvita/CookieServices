package com.github.boyvita.services.payment.controller;

import com.netflix.discovery.EurekaClient;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@RequestMapping//("pay")
@ComponentScan(basePackages = "com.github.boyvita.services")
public class PaymentController {

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

    @GetMapping
    public String greeting() {
        return "Hello from Payment Service!";
    }


    @PostMapping
    public Long payOrderById(@RequestBody Long orderId) {
        rabbitMQSender.send(orderId);
        return orderId;
    }

}
