package com.app.mvc.controller;

import com.app.mvc.controller.response.Response;
import com.app.mvc.controller.response.ResponseFactory;
import com.app.mvc.controller.response.ResponseRequest;
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

    private static Logger logger = LogManager.getLogger(EventsController.class);
    private static final String PARAM_ID = "systemId";

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public ResponseEntity<Response> retrieveAll(@RequestParam(PARAM_ID) String systemId) {
        logger.info("Retrieve all events. Accepted request with systemId = " + systemId);

        ArrayList<ResponseRequest> data = eventService.getAll();
        return ResponseFactory.ok(data);
    }

    @RequestMapping(value = "/events/{id}", method = RequestMethod.GET)
    public ResponseEntity<Response> retrieve(@RequestParam(PARAM_ID) String systemId, @PathVariable int id) {
        logger.info("Retrieve all events. Accepted request with systemId = " + systemId);

        ResponseRequest data;
        try {
            data = eventService.get(id);
        } catch (NotFoundException e) {
            return ResponseFactory.badRequest(ResponseFactory.EVENT_NOT_FOUND);
        }
        return ResponseFactory.ok(data);
    }
}
