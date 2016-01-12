package com.app.mvc.service;

import com.app.mvc.controller.response.ResponseData;
import com.app.mvc.dao.MysqlDAO;
import com.app.mvc.entity.*;
import com.app.mvc.service.exception.NotFoundException;
import com.app.mvc.service.interfaces.EventServiceInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("EventService")
public class EventService implements EventServiceInterface {

    private static Logger logger = LogManager.getLogger(EventService.class);

    @Override
    public int add(int idRequest) {
        Event event = new Event(idRequest);
        logger.info("Event added in database with id=" + idRequest + " Event created: " + event);
        return MysqlDAO.getDAO().create(event);
    }

    @Override
    public ArrayList<ResponseData> getAll() {
        logger.info("Get all events");
        ArrayList<Event> data = MysqlDAO.getDAO().retrieveAll(Event.class);
        ArrayList<ResponseData> responseDatas = new ArrayList<>();
        for (Event event : data) {
            responseDatas.add(createResponse(event));
        }
        return responseDatas;
    }

    @Override
    public ResponseData get(int id) throws NotFoundException {
        logger.info("Get event with id=" + id);
        Event event = MysqlDAO.getDAO().retrieve(Event.class, id);
        if (event.getId() == 0) {
            logger.info("Not found event with id=" + id);
            throw new NotFoundException();
        }
        return createResponse(event);
    }

    private ResponseData createResponse(Event event) {
        Request request = MysqlDAO.getDAO().retrieve(Request.class, event.getIdRequest());
        Description description = MysqlDAO.getDAO().retrieve(Description.class, request.getId());
        ResponseData response = new ResponseData();
        response.setId(request.getId());
        response.setCreatedAt(String.valueOf(request.getCreatedAt()));
        response.setLocation((Location) MysqlDAO.getDAO().retrieve(Location.class, description.getIdLocation()));
        response.setType((TypeRequest) MysqlDAO.getDAO().retrieve(TypeRequest.class, description.getIdTypeRequest()));
        response.setDanger((LevelDanger) MysqlDAO.getDAO().retrieve(LevelDanger.class, description.getIdLevelDanger()));
        return response;
    }
}
