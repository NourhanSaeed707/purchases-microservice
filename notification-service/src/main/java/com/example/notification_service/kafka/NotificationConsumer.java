package com.example.notification_service.kafka;
import com.example.notification_service.DTO.*;
import com.example.notification_service.client.UserClient;
import com.example.notification_service.email.EmailService;
import com.example.notification_service.model.NotificationType;
import com.example.notification_service.model.Notifications;
import com.example.notification_service.repository.NotificationRepository;
import com.example.notification_service.service.NotificationService;
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
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;
    private final UserClient userClient;

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmation(OrderConfirmation orderConfirmation) throws MessagingException {
        System.out.println("insiiiide notificaaation consumer: " + orderConfirmation);
//        String token = notificationService.getTokenFromRequest();
        String token = orderConfirmation.getToken();
        System.out.println("tokeeeeeeen: " + token);
        notificationRepository.save(
                Notifications.builder()
                        .type(NotificationType.ORDER_CONFIRMATION)
                        .createdAt(LocalDateTime.now())
                        .orderId(orderConfirmation.getOrderId())
                        .userId(orderConfirmation.getUserId())
                        .build()
        );
        System.out.println("useeeeeeer id: " + orderConfirmation.getUserId());
        UserDTO userDTO = userClient.getUserById(orderConfirmation.getUserId(), "Bearer " + token);
        System.out.println("useeeeeeeer after return: " + userDTO);
        emailService.sendOrderConfirmationEmail(orderConfirmation, userDTO);
    }
}
