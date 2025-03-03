package com.example.notification_service.model;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "notifications")
public class Notifications {
    @Id
    private String id;
    private NotificationType type;
    private LocalDateTime createdAt;
    @NotNull
    private Long userId;
    @NotNull
    private Long orderId;
}
