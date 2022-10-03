package com.raf.example.HotelNotificationService.controller;

import com.raf.example.HotelNotificationService.dto.EmailNotificationDto;
import com.raf.example.HotelNotificationService.dto.SentEmailDto;
import com.raf.example.HotelNotificationService.security.CheckSecurity;
import com.raf.example.HotelNotificationService.security.SecurityAspect;
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
    private SecurityAspect securityAspect;

    public EmailController(EmailService emailService, SecurityAspect securityAspect) {
        this.emailService = emailService;
        this.securityAspect = securityAspect;
    }

    @GetMapping("/all")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<List<SentEmailDto>> getAllSentEmails(@RequestHeader("Authorization") String authorization) {
        return new ResponseEntity<>(emailService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/allByEmail/")
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_MANAGER","ROLE_CLIENT"})
    public ResponseEntity<List<SentEmailDto>> getAllByCurrentUserEmail(@RequestHeader("Authorization") String authorization) {

        String email = securityAspect.getUserEmail(authorization);
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
