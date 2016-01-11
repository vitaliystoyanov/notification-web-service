package com.app.mvc.controller.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseFactory {

    public static final String INVALID_SYSTEM_ID = "Invalid system id";
    public static final String REQUEST_LIMIT = "Exceeded limit of requests per hour";
    public static final String REQUEST_CREATED = "Request created";
    public static final String REQUEST_OK = "Success";
    public static final String REQUEST_NOT_FOUND = "Request not found";
    public static final String EVENT_NOT_FOUND = "Event not found";

    public static ResponseEntity<Response> ok() {
        return new ResponseEntity<>(new Response(new ResponseStatus(200, REQUEST_OK)),
                HttpStatus.OK);
    }

    public static ResponseEntity<Response> ok(Object data) {
        return new ResponseEntity<>(new Response(new ResponseStatus(200, REQUEST_OK), data),
                HttpStatus.OK);
    }

    public static ResponseEntity<Response> badRequest() {
        return new ResponseEntity<>(new Response(new ResponseStatus(400, REQUEST_NOT_FOUND)),
                HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<Response> badRequest(String customMessage) {
        return new ResponseEntity<>(new Response(new ResponseStatus(400, customMessage)),
                HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<Response> created() {
        return new ResponseEntity<>(new Response(new ResponseStatus(201, REQUEST_CREATED)),
                HttpStatus.CREATED);
    }
}
