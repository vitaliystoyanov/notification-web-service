package com.app.mvc.service.interfaces;

import com.app.mvc.controller.response.ResponseRequest;
import com.app.mvc.service.exception.NotFoundException;

import java.util.ArrayList;

public interface EventServiceInterface {

    int add(int idRequest);

    ArrayList<ResponseRequest> getAll();

    ResponseRequest get(int id) throws NotFoundException;
}
