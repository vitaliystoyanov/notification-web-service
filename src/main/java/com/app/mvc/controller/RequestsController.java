package com.app.mvc.controller;

import com.app.mvc.controller.request.Body;
import com.app.mvc.controller.response.Response;
import com.app.mvc.controller.response.ResponseFactory;
import com.app.mvc.controller.response.ResponseRequest;
import com.app.mvc.entity.Device;
import com.app.mvc.service.DeviceService;
import com.app.mvc.service.EventService;
import com.app.mvc.service.RequestService;
import com.app.mvc.service.exception.NotFoundException;
import com.app.mvc.service.exception.VerifyExistsException;
import com.app.mvc.service.exception.VerifyLengthIdException;
import com.app.mvc.service.exception.VerifyLimitException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/v1")
public class RequestsController {

    private static Logger logger = LogManager.getLogger(RequestsController.class);
    private static final String PARAM_ID = "systemId";

    @Autowired
    private EventService eventService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private DeviceService deviceService;

    @RequestMapping(value = "/requests", method = RequestMethod.POST)
    public ResponseEntity<Response> createRequest(@RequestBody Body requestBody, @RequestParam(PARAM_ID) String systemId) {
        logger.info("Method GET, URL: /requests. Parameter systemId = " + systemId + ",\n Request body: " + requestBody);

        try {
            deviceService.verify(systemId);
        } catch (VerifyExistsException e) {
            deviceService.add(systemId);
        } catch (VerifyLimitException e) {
            return ResponseFactory.badRequest(ResponseFactory.REQUEST_LIMIT);
        } catch (VerifyLengthIdException e) {
            return ResponseFactory.badRequest(ResponseFactory.INVALID_SYSTEM_ID);
        }

        Device device = deviceService.getDeviceBySystemID(systemId);
        logger.trace("Device founded: " + device);
        int idCreatedRequest = requestService.create(requestBody, device.getId());
        eventService.add(idCreatedRequest);

        return ResponseFactory.created();
    }

    @RequestMapping(value = "/requests/{id}", method = RequestMethod.GET)
    public ResponseEntity<Response> retrieveRequest(@PathVariable int id, @RequestParam(PARAM_ID) String systemId) {
        logger.info("URL: /requests/{id}, Accepted request with id: " + id);

        ResponseRequest data = null;
        try {
            deviceService.verify(systemId);
            Device device = deviceService.getDeviceBySystemID(systemId);
            data = requestService.retrieve(id, device.getId());
        } catch (NotFoundException e) {
            return ResponseFactory.badRequest(ResponseFactory.REQUEST_NOT_FOUND);
        } catch (VerifyExistsException e) {
            deviceService.add(systemId);
        } catch (VerifyLimitException e) {
            return ResponseFactory.badRequest(ResponseFactory.REQUEST_LIMIT);
        } catch (VerifyLengthIdException e) {
            return ResponseFactory.badRequest(ResponseFactory.INVALID_SYSTEM_ID);
        }
        return ResponseFactory.ok(data);
    }

    @RequestMapping(value = "/requests", method = RequestMethod.GET)
    public ResponseEntity<Response> retrieveAllRequests(@RequestParam(PARAM_ID) String systemId) {
        logger.info("Retrieve all requests. Accepted request with systemId = " + systemId);

        try {
            deviceService.verify(systemId);
        } catch (VerifyExistsException e) {
            deviceService.add(systemId);
        } catch (VerifyLimitException e) {
            return ResponseFactory.badRequest(ResponseFactory.REQUEST_LIMIT);
        } catch (VerifyLengthIdException e) {
            return ResponseFactory.badRequest(ResponseFactory.INVALID_SYSTEM_ID);
        }
        int idDevice = deviceService.getDeviceBySystemID(systemId).getId();
        ArrayList<ResponseRequest> data = requestService.retrieveAll(idDevice);

        return ResponseFactory.ok(data);
    }

    @RequestMapping(value = "/requests/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Response> deleteRequest(@PathVariable int id, @RequestParam(PARAM_ID) String systemId) {
        logger.info("Method GET, URL: /requests. Parameters systemId = " + systemId + ", id = " + id);

        try {
            deviceService.verify(systemId);
            Device device = deviceService.getDeviceBySystemID(systemId);
            requestService.delete(id, device.getId());
        } catch (NotFoundException e) {
            return ResponseFactory.badRequest();
        } catch (VerifyExistsException e) {
            // TODO Need some fix this problem
            deviceService.add(systemId);
        } catch (VerifyLimitException e) {
            return ResponseFactory.badRequest(ResponseFactory.REQUEST_LIMIT);
        } catch (VerifyLengthIdException e) {
            return ResponseFactory.badRequest(ResponseFactory.INVALID_SYSTEM_ID);
        }
        return ResponseFactory.ok();
    }
}