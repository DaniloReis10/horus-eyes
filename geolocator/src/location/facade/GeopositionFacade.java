package location.facade;

import java.util.List;

import location.geoengine.DevicePath;
import location.geoengine.DevicesController;
import simulagent.Device;

public class GeopositionFacade implements IGeoPositionControl {

    private static GeopositionFacade instance;

    public static GeopositionFacade getInstance() {
        if (instance == null) {
            instance = new GeopositionFacade();
        }
        return instance;
    }

    public GeopositionFacade() {
        super();
    }

    @Override
    public boolean addDevicesToTrack(List<Device> devices) {
        for (Device device : devices) {
           this.addDeviceToTrack(device);
        }
        return true;
    }

    @Override
    public boolean addDeviceToTrack(Device device) {
        DevicesController.getInstance().addDevice(device);
        return true;
    }

    @Override
    public Device findDeviceById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DevicePath getDevicePath(Device device) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DetectableDevice> getDevicesAround(Device device, int radius) {
        return DevicesController.getInstance().getDevicesAround(device, radius);
    }

    /**
     * <p></p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    public void notifyDetectors() {
        DevicesController.getInstance().notifyDetectors();
    }

    /**
     * <p></p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    public IMobileDevice findMobileDeviceById(int id) {
        // TODO Auto-generated method stub
        return null;
    }
}