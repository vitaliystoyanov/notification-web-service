package com.app.mvc.service.interfaces;

import com.app.mvc.controller.request.Body;
import com.app.mvc.controller.response.ResponseData;
import com.app.mvc.service.exception.NotFoundException;

import java.util.ArrayList;

public interface RequestServiceInterface {

    int create(Body request, int idDevice);

    ArrayList<ResponseData> retrieveAll(int idDevice);

    ResponseData retrieve(int idRequest, int idDevice) throws NotFoundException;

    void delete(int idRequest, int idDevice) throws NotFoundException;
}
