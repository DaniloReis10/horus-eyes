package br.com.fujitec.simulagent.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.fujitec.location.facade.GeopositionFacade;
import br.com.fujitec.location.facade.IGeoPosition;
import br.com.fujitec.simulagent.interfaces.DetectableDevice;
import br.com.fujitec.simulagent.interfaces.Detector;


/**
 * @author tiagoportela <tiagoporteladesouza@gmail.com>
 *
 */
public class Sensor extends Device implements Detector {
    
    public static final int RADIUS_IN_METERS = 10;
    public static final int RADIUS_IN_PIXELS = 15;
    
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
        final List<DetectableDevice> detectedDevices = GeopositionFacade.getInstance().getDevicesAround(this, RADIUS_IN_METERS);
        for (DetectableDevice device : detectedDevices) {
            final boolean hasMoved = device.hasMoved();
            final boolean hasBeenDetected = this.detectedDevices.get(device.getId()) != null;
            
            if (hasMoved || !hasBeenDetected) {
                final Integer id = device.getId();
                final int time = this.getCurrentTime();
                final IGeoPosition sensorPositionAtDetection = this.currentPosition;
                final DetectedDevice detectedDevice = new DetectedDevice(id, time, RADIUS_IN_METERS, sensorPositionAtDetection);
                this.addDetectedDevice(detectedDevice);
                device.detect();
            } else if(hasBeenDetected) {
                device.detect();
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
        this.detectedDevices.put(device.getId(), detectedDevicesList);
    }

    @Override
    public Map<Integer, List<DetectedDevice>> getDetectedDevices() {
        return this.detectedDevices;
    }
}