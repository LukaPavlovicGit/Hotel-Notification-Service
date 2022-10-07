package com.raf.example.HotelNotificationService.domain;

import javax.persistence.*;

@Entity
public class EmailNotificationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String text;

    public EmailNotificationType(){}

    public EmailNotificationType(String type, String text) {
        this.type = type;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
