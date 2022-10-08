package com.raf.example.HotelNotificationService.controller;

import com.raf.example.HotelNotificationService.dto.EmailNotificationDto;
import com.raf.example.HotelNotificationService.dto.SentEmailDto;
import com.raf.example.HotelNotificationService.security.CheckSecurity;
import com.raf.example.HotelNotificationService.security.SecurityAspect;
import com.raf.example.HotelNotificationService.service.EmailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
    @GetMapping("/allByEmail")
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_MANAGER","ROLE_CLIENT"})
    public ResponseEntity<Page<SentEmailDto>> getAllByCurrentUserEmail(@RequestHeader("Authorization") String authorization,
                                                                       @ApiIgnore Pageable pageable) {

        String email = securityAspect.getUserEmail(authorization);
        return new ResponseEntity<>(emailService.findAllByEmail(email,pageable), HttpStatus.OK);
    }

    @GetMapping("/all/type")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Page<EmailNotificationDto>> getAllNotificationTypes(@RequestHeader("Authorization") String authorization,
                                                                              @ApiIgnore Pageable pageable){
        return new ResponseEntity<>(emailService.getAllNotificationTypes(pageable),HttpStatus.OK);
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
