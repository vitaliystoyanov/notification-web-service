package com.app.mvc.service.interfaces;

import com.app.mvc.controller.response.CommonRequest;
import com.app.mvc.entity.Request;
import com.app.mvc.service.exception.NotFoundRequestException;

import java.util.ArrayList;

public interface RequestServiceInterface {

    void createRequest(CommonRequest request, int idDevice);

    ArrayList<Request> getAllRequestsById(int idDevice);

    Request getRequestById(int idRequest, int idDevice) throws NotFoundRequestException;

    void deleteRequest(int idRequest, int idDevice) throws NotFoundRequestException;
}
