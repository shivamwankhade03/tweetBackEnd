package com.tweetApp.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ErrorMessage {

    @JsonProperty("Status")
    private String status=null;

    @JsonProperty("Message")
    private String message=null;

    public ErrorMessage(String status,String message){
        this.status=status;
        this.message=message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorMessage that = (ErrorMessage) o;
        return Objects.equals(status, that.status) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, message);
    }
}
