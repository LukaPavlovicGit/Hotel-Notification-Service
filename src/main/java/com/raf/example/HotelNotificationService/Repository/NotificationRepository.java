package com.raf.example.HotelNotificationService.Repository;

import com.raf.example.HotelNotificationService.domain.EmailNotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<EmailNotificationType, Long> {
    List<EmailNotificationType> findAll();
    Optional<EmailNotificationType> findEmailNotificationByType(String type);
}
