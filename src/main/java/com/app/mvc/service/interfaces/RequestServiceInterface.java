package com.app.mvc.service.interfaces;

import com.app.mvc.controller.request.Body;
import com.app.mvc.controller.response.ResponseRequest;
import com.app.mvc.service.exception.NotFoundException;

import java.util.ArrayList;

public interface RequestServiceInterface {

    int create(Body request, int idDevice);

    ArrayList<ResponseRequest> retrieveAll(int idDevice);

    ResponseRequest retrieve(int idRequest, int idDevice) throws NotFoundException;

    void delete(int idRequest, int idDevice) throws NotFoundException;
}
