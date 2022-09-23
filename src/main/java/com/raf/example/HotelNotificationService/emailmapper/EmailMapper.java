package com.raf.example.HotelNotificationService.emailmapper;

import com.raf.example.HotelNotificationService.domain.EmailNotification;
import com.raf.example.HotelNotificationService.domain.SentEmail;
import com.raf.example.HotelNotificationService.dto.EmailNotificationDto;
import com.raf.example.HotelNotificationService.dto.SentEmailDto;
import org.springframework.stereotype.Component;

@Component
public class EmailMapper {

    public SentEmailDto entityToDto(SentEmail entity){
        SentEmailDto dto = new SentEmailDto();
        dto.setEmail(entity.getEmail());
        dto.setDateSent(entity.getDateSent().toString());
        dto.setId(entity.getId());
        dto.setText(entity.getText());
        dto.setType(entity.getType());

        return dto;
    }

    public EmailNotificationDto entityToDtoType(EmailNotification entity){
        EmailNotificationDto dto = new EmailNotificationDto();
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setText(entity.getText());

        return dto;
    }

}
