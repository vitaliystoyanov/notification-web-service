package com.app.mvc.service;

import com.app.mvc.dao.MysqlDAO;
import com.app.mvc.entity.Device;
import com.app.mvc.service.exception.VerifyExistsException;
import com.app.mvc.service.exception.VerifyLengthIdException;
import com.app.mvc.service.exception.VerifyLimitException;
import com.app.mvc.service.interfaces.DeviceServiceInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("DeviceService")
public class DeviceService implements DeviceServiceInterface {

    private static Logger logger = LogManager.getLogger(DeviceService.class);
    private static final int LENGTH_SYSTEM_ID = 32; //64

    @Override
    public boolean add(String systemID) {
        Device newDevice = new Device(systemID);
        int id = MysqlDAO.getDAO().create(newDevice);
        logger.info("New device were added with such properties: systemId = " + systemID + ", id =" + id);
        return id > 0;
    }

    @Override
    public Device getDeviceBySystemID(String systemID) {
        ArrayList<Device> devices = getDevices(systemID);
        for (Device device : devices) {
            if (device.getSystemID().equals(systemID)) {
                logger.info("Device founded in database with systemID = " + systemID);
                return device;
            }
        }
        return null;
    }

    @Override
    public void verify(String systemId) throws VerifyLengthIdException, VerifyLimitException, VerifyExistsException {
        if (!verifyLengthSystemId(systemId)) {
            throw new VerifyLengthIdException();
        } else if (!verifyRequestLimit(systemId)) {
            throw new VerifyLimitException();
        } else if (!exists(systemId)) {
            throw new VerifyExistsException();
        }
    }

    private boolean verifyLengthSystemId(String systemID) {
        return systemID.length() == LENGTH_SYSTEM_ID;
    }

    private boolean exists(String systemID) {
        ArrayList<Device> devices = getDevices(systemID);
        for (Device item : devices) {
            if (item.getSystemID().equals(systemID)) {
                logger.info("This device exists in database with systemId = " + systemID);
                return true;
            }
        }
        return false;
    }

    private boolean verifyRequestLimit(String systemId) {
        return true;
    }

    private ArrayList<Device> getDevices(String systemID) {
        return MysqlDAO.getDAO().retrieveAllWithWhere(Device.class, new String[]{"systemID"},
                new String[]{systemID});
    }
}
