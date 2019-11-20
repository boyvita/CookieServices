package com.github.boyvita.services.catalog.controller;

import com.github.boyvita.services.model.Item;
import com.github.boyvita.services.model.ItemStatus;
import com.github.boyvita.services.model.Order;
import com.github.boyvita.services.repo.ItemRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
@Component
@ComponentScan(basePackages = "com.github.boyvita.services")
//@EnableJpaRepositories(basePackages = "com.github.boyvita.services.repo")
@EntityScan(basePackages = "com.github.boyvita.services.model")
public class RabbitMQReceiver {

    @Autowired
    private RabbitTemplate amqpTemplateReceiver;

    @Value("${rabbit.rabbitmq.queueCatalog}")
    private String queue;

    @Value("${rabbit.rabbitmq.routingKeyCatalog}")
    private String routingkey;

    @Autowired
    private ItemRepository itemRepository;

    public RabbitMQReceiver(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @RabbitListener(queues = "${rabbit.rabbitmq.queueCatalog}")
    public void receivePaidOrder(Order order) {
        for(Item it: order.getItems()){
            it.setItemStatus(ItemStatus.PAID);
            itemRepository.save(it);
        }
        System.out.println("Received paid order= " + order + " and extracted items from itemRepo");
    }

}