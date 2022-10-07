package com.raf.example.HotelNotificationService.runner;

import com.raf.example.HotelNotificationService.Repository.NotificationRepository;
import com.raf.example.HotelNotificationService.domain.EmailNotificationType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"default"})
@Component
public class DataRunner implements CommandLineRunner {

    private NotificationRepository notificationRepository;

    public DataRunner(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        EmailNotificationType en1 = new EmailNotificationType("account_activation",
                "Hello %name %lastname, click the link to activate your account: %link!");
        EmailNotificationType en2 = new EmailNotificationType("change_password",
                "Hello %name %lastname, your token is: %link!");
        EmailNotificationType en3 = new EmailNotificationType("reservation_successful",
                "Hello %name %lastname, a reservation in %hotel was a success!");
        EmailNotificationType en4 = new EmailNotificationType("reservation_cancellation",
                "Hello %name %lastname, a reservation in %hotel was canceled successfully!");
        EmailNotificationType en5 = new EmailNotificationType("reservation_reminder",
                "Hello %name %lastname, your reservation in %hotel is on %rezStart!");

        notificationRepository.save(en1);
        notificationRepository.save(en2);
        notificationRepository.save(en3);
        notificationRepository.save(en4);
        notificationRepository.save(en5);
    }
}
