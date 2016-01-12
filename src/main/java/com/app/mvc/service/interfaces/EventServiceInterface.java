package com.app.mvc.service.interfaces;

import com.app.mvc.controller.response.ResponseData;
import com.app.mvc.service.exception.NotFoundException;

import java.util.ArrayList;

public interface EventServiceInterface {

    int add(int idRequest);

    ArrayList<ResponseData> getAll();

    ResponseData get(int id) throws NotFoundException;
}
