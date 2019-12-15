package com.github.boyvita.services.payment.controller;

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
@RequestMapping("pay")
@ComponentScan(basePackages = "com.github.boyvita.services")
@EnableJpaRepositories(basePackages = "com.github.boyvita.services.repo")
@EntityScan(basePackages = "com.github.boyvita.services.model")
public class PaymentController {

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @PostMapping
    public Long payOrderById(@RequestBody Long orderId) {
        rabbitMQSender.send(orderId);
        return orderId;
    }

}
