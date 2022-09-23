package com.raf.example.HotelNotificationService.domain;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class SentEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String text;
    private String type;
    private Date dateSent;

    public SentEmail() {
    }

    public SentEmail(String email, String text, String type, Date dateSent) {
        this.email = email;
        this.text = text;
        this.type = type;
        this.dateSent = dateSent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }
}
