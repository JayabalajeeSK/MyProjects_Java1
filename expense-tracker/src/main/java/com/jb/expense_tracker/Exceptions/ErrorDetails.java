package com.jb.expense_tracker.Exceptions;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Error Details Response Object")
public class ErrorDetails {

    @Schema(description = "Error message that describes the problem")
    private String message;

    @Schema(description = "Error code representing the type of error")
    private String errorCode;

    @Schema(description = "Detailed information about the error")
    private String details;

    @Schema(description = "Timestamp of when the error occurred")
    private LocalDateTime timestamp;

    // Getters and Setters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    // Constructors
    public ErrorDetails() {
    }

    public ErrorDetails(LocalDateTime timestamp, String message, String details, String errorCode) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.errorCode = errorCode;
    }
}//