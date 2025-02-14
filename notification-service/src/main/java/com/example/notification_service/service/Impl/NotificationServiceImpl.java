package com.example.notification_service.service.Impl;
import com.example.notification_service.service.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public String getTokenFromRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            String token = attributes.getRequest().getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                return token.substring(7); // Remove "Bearer " prefix
            }
        }
        return null;
    }
}
