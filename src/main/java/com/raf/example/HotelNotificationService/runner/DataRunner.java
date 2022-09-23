package com.raf.example.HotelNotificationService.runner;

import com.raf.example.HotelNotificationService.Repository.NotificationRepository;
import com.raf.example.HotelNotificationService.domain.EmailNotification;
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
        EmailNotification en1 = new EmailNotification("account_activation",
                "Hello %name %lastname, click the link to activate your account: %link!");
        EmailNotification en2 = new EmailNotification("change_password",
                "Hello %name %lastname, your token is: %link!");
        EmailNotification en3 = new EmailNotification("reservation_successful",
                "Hello %name %lastname, a reservation in %hotel was a success!");
        EmailNotification en4 = new EmailNotification("reservation_cancellation",
                "Hello %name %lastname, a reservation in %hotel was canceled successfully!");
        EmailNotification en5 = new EmailNotification("reservation_reminder",
                "Hello %name %lastname, your reservation in %hotel is on %rezStart!");

        notificationRepository.save(en1);
        notificationRepository.save(en2);
        notificationRepository.save(en3);
        notificationRepository.save(en4);
        notificationRepository.save(en5);
    }
}
