package com.app.mvc.controller;

import com.app.mvc.dao.MysqlDAO;
import com.app.mvc.entity.Device;
import com.app.mvc.entity.Event;
import com.app.mvc.entity.LevelDanger;
import com.app.mvc.entity.Request;

import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {
        try {
//            Device testDevice0 = new Device("f848hg568", "Android");
//            MysqlDAO.getDAO().create(testDevice0);

//            Device deviceTest = MysqlDAO.getDAO().retrieve(Device.class, 4);
//            System.out.println(deviceTest.toString());
//
//            deviceTest.setCounterRequest(76);
//            deviceTest.setOs("IOS 9.7");
//            MysqlDAO.getDAO().update(deviceTest);
//            System.out.println(MysqlDAO.getDAO().retrieve(Device.class, 4).toString());

//            LevelDanger levelDanger = new LevelDanger("low", 4);
//            MysqlDAO.getDAO().create(levelDanger);
//            System.out.println(MysqlDAO.getDAO().retrieve(LevelDanger.class, 3).toString());

//            Location location = new Location(100,45.43554,45.4646);
//            MysqlDAO.getDAO().create(location);
//            System.out.println(MysqlDAO.getDAO().retrieve(Location.class, 1).toString());

//            TypeRequest typeRequest = new TypeRequest("steal");
//            MysqlDAO.getDAO().create(typeRequest);
//            System.out.println(MysqlDAO.getDAO().retrieve(TypeRequest.class, 1).toString());

            testCreateWithReturnID();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void testRequest() throws ClassNotFoundException {
        Request request = new Request(1, "12345675894");
        MysqlDAO.getDAO().create(request);
        Request request1 = MysqlDAO.getDAO().retrieve(Request.class, 1);
        request1.setCreatedAt("112222222");
        MysqlDAO.getDAO().update(request1);

    }

    public static void testEvent() throws ClassNotFoundException {
        Event event = new Event(5);
        MysqlDAO.getDAO().create(event);
        System.out.println(MysqlDAO.getDAO().retrieve(Event.class, 1).toString());

    }

    public static void testArrayListDAO() throws ClassNotFoundException {
        ArrayList<LevelDanger> levelArrayList = MysqlDAO.getDAO().retrieveAll(LevelDanger.class);
        System.out.println("levelArrayList size:" + levelArrayList.size());
        for (LevelDanger element : levelArrayList) {
            System.out.println(element.toString());
        }
    }

    public static void testCreateWithReturnID() throws ClassNotFoundException {
        Device testDevice0 = new Device("f848hg568");
        MysqlDAO.getDAO().create(testDevice0);
    }
}
