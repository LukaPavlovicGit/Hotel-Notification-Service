package com.raf.example.HotelNotificationService.listener;

import com.raf.example.HotelNotificationService.dto.MessageDto;
import com.raf.example.HotelNotificationService.listener.helper.MessageHelper;
import com.raf.example.HotelNotificationService.service.EmailService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class EmailListener {

    private MessageHelper messageHelper;
    private EmailService emailService;

    public EmailListener(MessageHelper messageHelper, EmailService emailService) {
        this.messageHelper = messageHelper;
        this.emailService = emailService;
    }

    @JmsListener(destination = "send_email_queue", concurrency = "5-10")
    public void addOrder(Message message) throws JMSException {
        MessageDto messageToSend = messageHelper.getMessage(message, MessageDto.class);
        emailService.sendMessage(messageToSend);
        //emailService.sendMessage(sendMessage.getEmail(), "Account activation", matchesDto.toString());
    }
}
