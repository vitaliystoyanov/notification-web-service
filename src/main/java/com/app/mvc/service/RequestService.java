package com.app.mvc.service;

import com.app.mvc.controller.request.Body;
import com.app.mvc.controller.response.ResponseRequest;
import com.app.mvc.dao.MysqlDAO;
import com.app.mvc.entity.*;
import com.app.mvc.service.exception.NotFoundException;
import com.app.mvc.service.interfaces.RequestServiceInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("RequestDevice")
public class RequestService implements RequestServiceInterface {

    private static Logger logger = LogManager.getLogger(RequestService.class);

    // TODO Need implement throwing exception when can not create request, description
    @Override
    public int create(Body requestBody, int idDevice) {
        int idLevelDanger = MysqlDAO.getDAO().create(requestBody.getLevelDanger());
        int idTypeRequest = MysqlDAO.getDAO().create(requestBody.getTypeRequest());
        int idLocation = MysqlDAO.getDAO().create(requestBody.getLocation());
        logger.trace("Objects were created in db with such id: idLevelDanger=" + idLevelDanger
                + ", idTypeRequest" + idTypeRequest + ", idLocation=" + idLocation);

        Request newRequest = new Request(idDevice);
        int idRequestCreated = MysqlDAO.getDAO().create(newRequest);
        logger.trace("Request was created in db with such id = " + idRequestCreated);
        Description description = new Description(idTypeRequest, idLevelDanger, idLocation);
        description.setIdRequest(idRequestCreated);
        int idDescription = MysqlDAO.getDAO().create(description);
        logger.trace("Description was created in db with such id = " + idDescription);
        return idRequestCreated;
    }

    @Override
    public ArrayList<ResponseRequest> retrieveAll(int idDevice) {
        ArrayList<Request> data = getAllFromDB(idDevice);
        ArrayList<ResponseRequest> responseRequests = new ArrayList<>();
        for (Request request : data) {
            responseRequests.add(createResponse(request));
        }
        return responseRequests;
    }

    @Override
    public ResponseRequest retrieve(int idRequest, int idDevice) throws NotFoundException {
        ArrayList<Request> data = getAllFromDB(idDevice);
        for (Request request : data) {
            if (request.getId() == idRequest) {
                return createResponse(request);
            }
        }
        throw new NotFoundException();
    }

    @Override
    public void delete(int idRequest, int idDevice) throws NotFoundException {
        ArrayList<Request> data = getAllFromDB(idDevice);
        boolean flag = false;
        for (Request request : data) {
            if (request.getId() == idRequest) {
                MysqlDAO.getDAO().delete(request);
                flag = true;
                break;
            }
        }
        if (!flag) throw new NotFoundException();
    }

    // TODO Need refactoring
    private ArrayList<Request> getAllFromDB(int idDevice) {
        String[] valueWhere = {String.valueOf(idDevice)};
        String[] argWhere = {"idDeviceFK"};
        return MysqlDAO.getDAO().retrieveAllWithWhere(Request.class, argWhere, valueWhere);
    }

    private ResponseRequest createResponse(Request request) {
        Description description = MysqlDAO.getDAO().retrieve(Description.class, request.getId());
        ResponseRequest response = new ResponseRequest();
        response.setId(request.getId());
        response.setCreatedAt(String.valueOf(request.getCreatedAt()));
        response.setLocation((Location) MysqlDAO.getDAO().retrieve(Location.class, description.getIdLocation()));
        response.setType((TypeRequest) MysqlDAO.getDAO().retrieve(TypeRequest.class, description.getIdTypeRequest()));
        response.setDanger((LevelDanger) MysqlDAO.getDAO().retrieve(LevelDanger.class, description.getIdLevelDanger()));
        return response;
    }
}
