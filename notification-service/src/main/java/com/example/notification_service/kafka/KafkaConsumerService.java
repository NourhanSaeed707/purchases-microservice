package com.example.notification_service.kafka;

import com.example.notification_service.email.EmailService;
import com.example.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

}
