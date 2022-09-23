package com.raf.example.HotelNotificationService.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

/**
 * This class represents error response.
 */
public class ErrorDetails {

    @JsonProperty("error_code")
    private ErrorCode errorCode;
    @JsonProperty("error_message")
    private String errorMessage;
    private Instant timestamp;

    public ErrorDetails(){}
    public ErrorDetails(ErrorCode errorCode, String errorMessage, Instant timestamp) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.timestamp = timestamp;
    }

}
