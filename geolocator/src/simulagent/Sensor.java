package simulagent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import location.facade.DetectableDevice;
import location.facade.GeopositionFacade;
import location.facade.IGeoPosition;
import sensordataset.DetectedDevice;


/**
 * @author tiagoportela <tiagoporteladesouza@gmail.com>
 *
 */
public class Sensor extends Device implements Detector {
    
    public static final int RADIUS = 10;
    private Map<Integer, List<DetectedDevice>> detectedDevices;
    private IGeoPosition previousPosition;
    
    public Sensor(Integer agentID, Mobility mobility) {
        super(agentID, mobility);
        this.detectedDevices = new HashMap<Integer, List<DetectedDevice>>();
    }
    
    @Override
    public void move() {
        this.previousPosition = this.currentPosition;
        super.move();
        
        final boolean hasMoved = !this.currentPosition.equals(this.previousPosition);
        
        if (hasMoved) {
            this.scanArea();
        }
    }

    @Override
    public void scanArea() {
        final List<DetectableDevice> detectedDevices = GeopositionFacade.getInstance().getDevicesAround(this, RADIUS);
        for (DetectableDevice device : detectedDevices) {
            final boolean hasMoved = device.hasMoved();
            
            if (hasMoved) {
                final Integer id = device.getId();
                final int time = this.getCurrentTime();
                final IGeoPosition position = device.getCurrentPosition();
                final DetectedDevice detectedDevice = new DetectedDevice(id, time, RADIUS, position);
                this.addDetectedDevice(detectedDevice);
            }
        }
    }

    @Override
    public void addDetectedDevice(DetectedDevice device) {
        List<DetectedDevice> detectedDevicesList = this.detectedDevices.get(device.getId());

        if(detectedDevicesList == null) {
            detectedDevicesList = new ArrayList<DetectedDevice>();
        }
        
        detectedDevicesList.add(device);
    }

    @Override
    public Map<Integer, List<DetectedDevice>> getDetectedDevices() {
        return this.detectedDevices;
    }
}