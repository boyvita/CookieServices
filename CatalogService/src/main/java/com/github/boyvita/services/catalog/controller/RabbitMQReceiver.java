package com.github.boyvita.services.catalog.controller;

import com.github.boyvita.services.catalog.model.Item;
import com.github.boyvita.services.catalog.model.ItemStatus;
import com.github.boyvita.services.catalog.repo.ItemRepository;
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
@ComponentScan(basePackages = "com.github.boyvita.services.catalog")
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
    public void receivePaidOrder(Long orderId) {
        for (Item it: itemRepository.findAll()){
            if (it.getOrderId() == orderId) {
                it.setItemStatus(ItemStatus.COOKING);
                itemRepository.save(it);
            }
        }
        System.out.println("Received paid order id = " + orderId + " and extracted items from itemRepo");
    }

}