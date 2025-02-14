package com.example.notification_service.repository;
import com.example.notification_service.model.Notifications;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notifications , String> {
}
