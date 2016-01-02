package com.app.mvc.ui;

import com.app.mvc.dao.MysqlDAO;
import com.app.mvc.domain.entity.Device;

public class Test {

    public static void main(String[] args) {
        try {
//            Device testDevice0 = new Device("f848hg568", "Android");
//            MysqlDAO.getDAO().create(testDevice0);

            Device deviceTest = MysqlDAO.getDAO().retrieve(Device.class, 4);
            System.out.println(deviceTest.toString());

            deviceTest.setCounterRequest(76);
            deviceTest.setOs("IOS 9.7");
            MysqlDAO.getDAO().update(deviceTest);
            System.out.println(MysqlDAO.getDAO().retrieve(Device.class, 4).toString());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
