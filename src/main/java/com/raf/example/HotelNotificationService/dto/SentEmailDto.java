package com.raf.example.HotelNotificationService.dto;

public class SentEmailDto {
    private Long id;
    private String email;
    private String text;
    private String dateSent;
    private String type;

    public SentEmailDto(Long id, String email, String text, String dateSent, String type) {
        this.id = id;
        this.email = email;
        this.text = text;
        this.dateSent = dateSent;
        this.type = type;
    }

    public SentEmailDto() {
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

    public String getDateSent() {
        return dateSent;
    }

    public void setDateSent(String dateSent) {
        this.dateSent = dateSent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
