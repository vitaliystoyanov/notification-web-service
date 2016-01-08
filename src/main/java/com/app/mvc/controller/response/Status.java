package com.app.mvc.controller.response;

public class Status {

    public static final String INVALID_SYSTEM_ID = "Invalid system id";
    public static final String REQUEST_LIMIT = "Exceeded limit of requests per hour";
    public static final String REQUEST_CREATED = "Request created";
    public static final String REQUEST_OK = "Success";
    public static final String REQUEST_NOT_FOUND = "Request not found";

    private int code;
    private String message;

    public Status(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Status{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
