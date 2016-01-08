package com.app.mvc.controller;

import com.app.mvc.controller.response.CommonRequest;
import com.app.mvc.controller.response.JsonResponse;
import com.app.mvc.controller.response.Status;
import com.app.mvc.entity.Device;
import com.app.mvc.entity.Request;
import com.app.mvc.service.DeviceService;
import com.app.mvc.service.RequestService;
import com.app.mvc.service.exception.NotFoundRequestException;
import com.app.mvc.service.exception.VerifyExistsException;
import com.app.mvc.service.exception.VerifyLengthIdException;
import com.app.mvc.service.exception.VerifyLimitException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/v1")
public class RequestsController {

    private static Logger logger = LogManager.getLogger(RequestsController.class);

    @Autowired
    private RequestService requestService;

    @Autowired
    private DeviceService deviceService;

    @RequestMapping(value = "/requests", method = RequestMethod.POST)
    public ResponseEntity<JsonResponse> createRequest(@RequestBody CommonRequest request,
                                                      @RequestParam("systemId") String systemId) {
        logger.info("Method GET, URL: /requests. Parameter systemId = " + systemId + ",\n Request body: " + request);
        ResponseEntity<JsonResponse> responseEntity = verifyDevice(systemId);
        if (responseEntity != null) return responseEntity;

        Device device = deviceService.getDeviceBySystemID(systemId);
        logger.info("Device service returned object: " + device);
        requestService.createRequest(request, device.getId());

        return new ResponseEntity<>(new JsonResponse(new Status(201, Status.REQUEST_CREATED)),
                HttpStatus.CREATED);
    }

    @RequestMapping(value = "/requests/{id}", method = RequestMethod.GET)
    public ResponseEntity<JsonResponse> retrieveRequest(@PathVariable int id, @RequestParam("systemId") String systemId) {
        logger.info("URL: /requests/{id}, Accepted request with id: " + id);
        ResponseEntity<JsonResponse> errorResponse = verifyDevice(systemId);
        if (errorResponse != null) return errorResponse;

        Device device = deviceService.getDeviceBySystemID(systemId);
        logger.info("Device service returned object: " + device);

        try {
            Request data = requestService.getRequestById(id, device.getId());
            return new ResponseEntity<>(new JsonResponse(new Status(200, Status.REQUEST_OK), data), HttpStatus.OK);
        } catch (NotFoundRequestException e) {
            return new ResponseEntity<>(new JsonResponse(new Status(400, Status.REQUEST_NOT_FOUND)), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/requests", method = RequestMethod.GET)
    public ResponseEntity<JsonResponse> retrieveAllRequests(@RequestParam("systemId") String systemId) {
        logger.info("Retrieve all requests. Accepted request with systemId = " + systemId);
        ResponseEntity<JsonResponse> responseEntity = verifyDevice(systemId);
        if (responseEntity != null) return responseEntity;

        int idDevice = deviceService.getDeviceBySystemID(systemId).getId();
        ArrayList<Request> data = requestService.getAllRequestsById(idDevice);

        return new ResponseEntity<>(new JsonResponse(new Status(200, Status.REQUEST_OK), data), HttpStatus.OK);
    }

    @RequestMapping(value = "/requests/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<JsonResponse> deleteRequest(@PathVariable int id, @RequestParam("systemId") String systemId) {
        logger.info("Method GET, URL: /requests. Parameters systemId = " + systemId + ", id = " + id);
        ResponseEntity<JsonResponse> responseEntity = verifyDevice(systemId);
        if (responseEntity != null) return responseEntity;

        Device device = deviceService.getDeviceBySystemID(systemId);
        logger.info("Device service returned object: " + device);

        try {
            requestService.deleteRequest(id, device.getId());
            return new ResponseEntity<>(new JsonResponse(new Status(200, Status.REQUEST_OK)), HttpStatus.OK);
        } catch (NotFoundRequestException e) {
            return new ResponseEntity<>(new JsonResponse(new Status(400, Status.REQUEST_NOT_FOUND)), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/requests/{id}", method = RequestMethod.PUT)
    public ResponseEntity<JsonResponse> updateRequest(@PathVariable int id, @RequestParam("systemId") String systemId) {
        logger.info("Method GET, URL: /requests. Parameters systemId = " + systemId + ", id = " + id);
        ResponseEntity<JsonResponse> responseEntity = verifyDevice(systemId);
        if (responseEntity != null) return responseEntity;

        return null;
    }

    private ResponseEntity<JsonResponse> verifyDevice(String systemId) {
        try {
            deviceService.verify(systemId);
        } catch (VerifyLengthIdException e) {
            return new ResponseEntity<>(new JsonResponse(new Status(400, Status.INVALID_SYSTEM_ID)),
                    HttpStatus.BAD_REQUEST);
        } catch (VerifyLimitException e) {
            return new ResponseEntity<>(new JsonResponse(new Status(400, Status.REQUEST_LIMIT)),
                    HttpStatus.BAD_REQUEST);
        } catch (VerifyExistsException e) {
            deviceService.addDevice(systemId);
        }
        return null;
    }
}