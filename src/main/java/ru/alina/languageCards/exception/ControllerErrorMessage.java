package ru.alina.languageCards.exception;


import java.util.Date;


public class ControllerErrorMessage {
    private int status;
    private String message;
    private String description;
    private Date timestamp;

    public ControllerErrorMessage(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }

    public ControllerErrorMessage(int status, String message, String description, Date timestamp) {
        this.status = status;
        this.message = message;
        this.description = description;
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ControllerErrorMessage{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", description='" + description + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
