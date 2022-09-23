package com.raf.example.HotelNotificationService.Repository;

import com.raf.example.HotelNotificationService.domain.EmailNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<EmailNotification, Long> {
    List<EmailNotification> findAll();
    Optional<EmailNotification> findEmailNotificationByType(String type);
}
