package com.app.mvc.service;

import com.app.mvc.controller.response.CommonRequest;
import com.app.mvc.dao.MysqlDAO;
import com.app.mvc.entity.Description;
import com.app.mvc.entity.Request;
import com.app.mvc.service.exception.NotFoundRequestException;
import com.app.mvc.service.interfaces.RequestServiceInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("RequestDevice")
public class RequestService implements RequestServiceInterface {

    @Autowired
    private EventService eventService;

    private static Logger logger = LogManager.getLogger(RequestService.class);

    @Override
    public void createRequest(CommonRequest request, int idDevice) {
        int idLevelDanger = MysqlDAO.getDAO().create(request.getLevelDanger());
        int idTypeRequest = MysqlDAO.getDAO().create(request.getTypeRequest());
        int idLocation = MysqlDAO.getDAO().create(request.getLocation());

        Request newRequest = new Request(idDevice, "1234567899");
        int idRequest = MysqlDAO.getDAO().create(newRequest);
        Description description = new Description(idTypeRequest, idLevelDanger, idLocation);
        description.setIdRequest(idRequest);
        int idDescription = MysqlDAO.getDAO().create(description);
    }

    @Override
    public ArrayList<Request> getAllRequestsById(int idDevice) {
        ArrayList<Request> data = MysqlDAO.getDAO().retrieveAllWithWhere(Request.class, new String[]{"idDeviceFK"},
                new String[]{String.valueOf(idDevice)});
        return data;
    }

    @Override
    public Request getRequestById(int idRequest, int idDevice) throws NotFoundRequestException {
        ArrayList<Request> data = MysqlDAO.getDAO().retrieveAllWithWhere(Request.class, new String[]{"idDeviceFK"},
                new String[]{String.valueOf(idDevice)});
        for (Request request : data) {
            if (request.getId() == idRequest) {
                return request;
            }
        }
        throw new NotFoundRequestException();
    }

    @Override
    public void deleteRequest(int idRequest, int idDevice) throws NotFoundRequestException {
        ArrayList<Request> data = MysqlDAO.getDAO().retrieveAllWithWhere(Request.class, new String[]{"idDeviceFK"},
                new String[]{String.valueOf(idDevice)});
        for (Request request : data) {
            if (request.getId() == idRequest) {
                MysqlDAO.getDAO().delete(request);
                break;
            }
        }
        throw new NotFoundRequestException();
    }
}
