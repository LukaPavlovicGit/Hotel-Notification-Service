package com.raf.example.HotelNotificationService.dto;

public class EmailNotificationDto {
    private Long id;
    private String type;
    private String text;

    public EmailNotificationDto(Long id, String type, String text) {
        this.id = id;
        this.type = type;
        this.text = text;
    }

    public EmailNotificationDto() {
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
