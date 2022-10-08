package com.raf.example.HotelNotificationService.service;

import com.raf.example.HotelNotificationService.Repository.NotificationRepository;
import com.raf.example.HotelNotificationService.Repository.SentEmailRepository;
import com.raf.example.HotelNotificationService.domain.EmailNotificationType;
import com.raf.example.HotelNotificationService.domain.SentEmail;
import com.raf.example.HotelNotificationService.dto.EmailNotificationDto;
import com.raf.example.HotelNotificationService.dto.MessageDto;
import com.raf.example.HotelNotificationService.dto.SentEmailDto;
import com.raf.example.HotelNotificationService.emailmapper.EmailMapper;
import com.raf.example.HotelNotificationService.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmailService {
    private JavaMailSender mailSender;
    private NotificationRepository notificationRepository;
    private SentEmailRepository sentEmailRepository;
    private EmailMapper emailMapper;

    public EmailService(JavaMailSender mailSender, NotificationRepository notificationRepository, SentEmailRepository sentEmailRepository, EmailMapper emailMapper) {
        this.mailSender = mailSender;
        this.notificationRepository = notificationRepository;
        this.sentEmailRepository = sentEmailRepository;
        this.emailMapper = emailMapper;
    }

    public EmailNotificationDto sendMessage(MessageDto messageDto){

        EmailNotificationType en = notificationRepository.findEmailNotificationByType(messageDto.getType())
                .orElseThrow(() -> new NotFoundException(String.format("Notification type not found.")));
        SimpleMailMessage message = new SimpleMailMessage();
        String text = en.getText();
        text = text.replaceAll("%name", messageDto.getName());
        text = text.replaceAll("%lastname", messageDto.getLastname());
        text = text.replaceAll("%hotel", messageDto.getHotelName());
        text = text.replaceAll("%link", messageDto.getLink());
        text = text.replaceAll("%rezStart", messageDto.getRezStart());

        message.setTo(messageDto.getEmail());
        message.setSubject(en.getType());
        message.setText(text);
        mailSender.send(message);
        sentEmailRepository.save(new SentEmail(messageDto.getEmail(), text, en.getType(), Date.valueOf(LocalDate.now())));
        return emailMapper.entityToDtoType(en);
    }
    public List<SentEmailDto> findAll(){
        List<SentEmailDto> sentEmailDto = new ArrayList<>();
        List<SentEmail> all = sentEmailRepository.findAll();
        for(SentEmail s: all){
            sentEmailDto.add(emailMapper.entityToDto(s));
        }
        return sentEmailDto;
    }

    public Page<SentEmailDto> findAllByEmail(String email, Pageable pageable){
        List<SentEmail> all = sentEmailRepository.findAllByEmail(email);
        List<SentEmailDto> sentEmailDto = new ArrayList<>();
        for(SentEmail s : all){
            if(s.getEmail().equals(email))
                sentEmailDto.add(emailMapper.entityToDto(s));
        }
        return new PageImpl<>(sentEmailDto);
    }

    public void editNotificationType(EmailNotificationDto dto){
        EmailNotificationType emailNotification = notificationRepository.findById(dto.getId()).orElseThrow(() -> new NotFoundException(String
                .format("Notification type with id: %d not found.", dto.getId())));
        emailNotification.setType(dto.getType());
        emailNotification.setText(dto.getText());
        notificationRepository.save(emailNotification);
    }

    public void deleteType(Long id) {
        EmailNotificationType en = notificationRepository.findById(id).orElseThrow(() -> new NotFoundException(String
                .format("Notification type with id: %d not found.", id)));
        notificationRepository.deleteById(en.getId());
    }

    public Page<EmailNotificationDto> getAllNotificationTypes(Pageable pageable) {

        List<EmailNotificationDto> dto = new ArrayList<>();
        List<EmailNotificationType> all = notificationRepository.findAll();
        all.forEach( en ->{
            dto.add(emailMapper.entityToDtoType(en));
        });
        return notificationRepository.findAll(pageable).map(emailMapper::entityToDtoType);
    }
}
