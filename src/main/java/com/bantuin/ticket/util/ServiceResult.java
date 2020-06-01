package com.bantuin.ticket.util;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ServiceResult<T> {

    private final boolean success;
    private final String message;
    private final T payload;

    public ServiceResult(boolean status, String message, T payload) {
        this.success = status;
        this.message = message;
        this.payload = payload;
    }

    public ServiceResult(boolean status, String message) {
        this.success = status;
        this.message = message;
        this.payload = null;
    }

    public ServiceResult(String message) {
        this.success = false;
        this.message = message;
        this.payload = null;
    }

    public String getMessage() {
        return message;
    }

    public T getPayload() {
        return payload;
    }

    public boolean payloadExist() {
        return getPayload() != null;
    }

    public boolean isSuccess() {
        return success;
    }

    @JsonIgnore
    public boolean isFailed() {
        return !success;
    }

    public boolean getStatus() {
        return this.success;
    }

    public static ServiceResult success() {
        return new ServiceResult(true, "success");
    }

    public static ServiceResult failed() {
        return new ServiceResult("failed");
    }

}
