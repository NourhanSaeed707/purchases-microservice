package com.example.notification_service.kafka;

import com.example.notification_service.DTO.OrderConfirm;
import com.example.notification_service.email.EmailService;
import com.example.notification_service.model.Notifications;
import com.example.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @KafkaListener(topics = "order-confirmed-topic" , groupId = "notification-group")
    public void consume(OrderConfirm orderConfirm) {
        Notifications notification = Notifications.builder()
                .userId(orderConfirm.getUserId())
                .orderId(orderConfirm.getOrderId())
                .createdAt(orderConfirm.getCreatedAt())
                .subject("Order Confirm")
                .message("Your order #" + orderConfirm.getOrderId() + " has been confirmed!")
                .build();
        notificationRepository.save(notification);
        emailService.sendEmail(orderConfirm.getOrderId(), notification.getSubject(), notification.getMessage());
    }

}
