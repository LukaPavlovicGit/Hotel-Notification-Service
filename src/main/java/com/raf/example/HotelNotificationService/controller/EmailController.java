package com.raf.example.HotelNotificationService.controller;

import com.raf.example.HotelNotificationService.dto.EmailNotificationDto;
import com.raf.example.HotelNotificationService.dto.SentEmailDto;
import com.raf.example.HotelNotificationService.security.CheckSecurity;
import com.raf.example.HotelNotificationService.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class EmailController {

    private EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/all")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<List<SentEmailDto>> getAllSentEmails(@RequestHeader("Authorization") String authorization) {
        return new ResponseEntity<>(emailService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/all/{email}")
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_MANAGER","ROLE_CLIENT"})
    public ResponseEntity<List<SentEmailDto>> getAllSentEmailsByEmail(@RequestHeader("Authorization") String authorization,
                                                                      @PathVariable("email") String email) {
        return new ResponseEntity<>(emailService.findAllByEmail(email), HttpStatus.OK);
    }
    /*@GetMapping("/filter/{email}/{type}/{date}")
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_MANAGER","ROLE_CLIENT"})
    public ResponseEntity<SentEmailListDto> getAllEmailsFiltered(@RequestHeader("Authorization") String authorization,@PathVariable("email") String email
            ,@PathVariable("type") String type, @PathVariable("date") String date) {
        return new ResponseEntity<SentEmailListDto>(emailService.filterEmails(email, type, date), HttpStatus.OK);
    }*/
    @GetMapping("/all/type")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<List<EmailNotificationDto>> getAllNotificationTypes(@RequestHeader("Authorization") String authorization){
        return new ResponseEntity<>(emailService.getAllNotificationTypes(),HttpStatus.OK);
    }
    @PostMapping("/update")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Void> changeNotificationType(@RequestHeader("Authorization") String authorization,
                                                       @RequestBody @Valid EmailNotificationDto dto){
        emailService.editNotificationType(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/type/{typeId}")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Void> deleteNotificationType(@RequestHeader("Authorization") String authorization,
                                                       @PathVariable("typeId") Long id){
        emailService.deleteType(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
