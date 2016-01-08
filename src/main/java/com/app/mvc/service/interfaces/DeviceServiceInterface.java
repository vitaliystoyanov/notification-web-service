package com.app.mvc.service.interfaces;

import com.app.mvc.entity.Device;
import com.app.mvc.service.exception.VerifyExistsException;
import com.app.mvc.service.exception.VerifyLengthIdException;
import com.app.mvc.service.exception.VerifyLimitException;

public interface DeviceServiceInterface {

    void verify(String systemId) throws VerifyLengthIdException, VerifyLimitException, VerifyExistsException;

    Device getDeviceBySystemID(String systemID);

    boolean addDevice(String systemID);

}
