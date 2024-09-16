package com.wizeline.maven.LearningJava.consumer;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.wizeline.maven.LearningJava.model.BankAccountDTO;

@Component
public class KafkaConsumer {

    @KafkaListener(id = "sampleGroup", topics = "useraccount-topic3", containerFactory = "jsonKafkaListenerContainerFactory")
    public void consumeMessage(ConsumerRecord<String, List<BankAccountDTO>> cr, @Payload BankAccountDTO account) {
        System.out.println("Received: " + account.getUserName());
    }
}