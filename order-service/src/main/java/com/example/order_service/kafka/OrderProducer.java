package com.example.order_service.kafka;
import com.example.order_service.DTO.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void sendNotification(OrderEvent event) {
        kafkaTemplate.send("order-topic", event);
    }

}
