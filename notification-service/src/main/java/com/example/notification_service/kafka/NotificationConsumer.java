package com.example.notification_service.kafka;
import com.example.notification_service.DTO.*;
import com.example.notification_service.client.UserClient;
import com.example.notification_service.email.EmailService;
import com.example.notification_service.model.NotificationType;
import com.example.notification_service.model.Notifications;
import com.example.notification_service.repository.NotificationRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;
    private final UserClient userClient;

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmation(OrderConfirmation orderConfirmation) throws MessagingException {
        System.out.println("insiiiide notificaaation consumer");
        notificationRepository.save(
                Notifications.builder()
                        .type(NotificationType.ORDER_CONFIRMATION)
                        .createdAt(LocalDateTime.now())
                        .orderId(orderConfirmation.getOrderId())
                        .userId(orderConfirmation.getUserId())
                        .build()
        );
        UserDTO userDTO = userClient.getUserById(orderConfirmation.getUserId());
        emailService.sendOrderConfirmationEmail(orderConfirmation, userDTO);

    }

}
