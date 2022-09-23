package com.raf.example.HotelNotificationService.Repository;

import com.raf.example.HotelNotificationService.domain.SentEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SentEmailRepository extends JpaRepository<SentEmail, Long> {
    List<SentEmail> findAllByEmail(String email);
}
