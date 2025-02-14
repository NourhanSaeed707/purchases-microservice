package com.example.order_service.kafka;
import com.example.order_service.DTO.OrderConfirmation;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {
    private final KafkaTemplate<String, OrderConfirmation> kafkaTemplate;

    public void sendNotification(OrderConfirmation event) {
        kafkaTemplate.send("order-topic", event);
    }

}
