package com.app.mvc.controller;

import com.app.mvc.controller.response.Response;
import com.app.mvc.controller.response.ResponseData;
import com.app.mvc.controller.response.ResponseFactory;
import com.app.mvc.service.EventService;
import com.app.mvc.service.exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/v1")
public class EventsController {

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public ResponseEntity<Response> retrieveAll() {

        ArrayList<ResponseData> data = eventService.getAll();
        return ResponseFactory.ok(data);
    }

    @RequestMapping(value = "/events/{id}", method = RequestMethod.GET)
    public ResponseEntity<Response> retrieve( @PathVariable int id) {

        ResponseData data;
        try {
            data = eventService.get(id);
        } catch (NotFoundException e) {
            return ResponseFactory.badRequest(ResponseFactory.EVENT_NOT_FOUND);
        }
        return ResponseFactory.ok(data);
    }
}
