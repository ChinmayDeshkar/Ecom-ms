package com.order.service.impl;

import com.order.model.Order;
import com.order.model.Topics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaServiceImpl {

    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;

    public boolean sendNewOrder(Order order){
        kafkaTemplate.send(Topics.orderPlaced,order);
        return true;
    }
}
