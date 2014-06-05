package sensordataset;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import simulagent.Agent;
import simulagent.Device;
import simulagent.Mobility;
import simulagent.Sensor;
import trilaceration.LocationArea2;
import trilaceration.ScaleConverter;

/**
 * @author tiagoportela <tiagoporteladesouza@gmail.com>
 *
 */
public class DataAnalyzer {
    
    /**
     * <p>Compares all DetectedDevice information collected by the sensors, to infere if the DetectedDevice has mobile or fixed Mobility.</p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return 
     * @return
     */
    public static List<AnalyzedData> predictDevicesClasses(final List<Device> devices) {
        final List<Agent> agents = getAgentsFromDeviceList(devices);
        final List<Sensor> sensors = getSensorsFromDeviceList(devices);
        
        final List<AnalyzedData> analyzedData = new ArrayList<AnalyzedData>();
        
        for (Agent agent: agents) {
            final List<Sensor> sensorsThatDetectedTheDevice = getSensorsThatDetectedTheDevice(agent, sensors);
            final Integer agentID = agent.getId();
            BufferedImage intersectionArea = new BufferedImage(ScaleConverter.width, ScaleConverter.height, BufferedImage.TYPE_INT_RGB);
            final Graphics intersectionGraphics = intersectionArea.getGraphics();
            intersectionGraphics.setColor(LocationArea2.BASE_AREA_COLOR);
            intersectionGraphics.fillRect(0, 0, ScaleConverter.width, ScaleConverter.height);
            
            for (Sensor sensor : sensorsThatDetectedTheDevice) {
                final List<DetectedDevice> detectedDevices = sensor.getDetectedDevices().get(agentID);
                final boolean hasDetectedDeviceMoreThanOnce = detectedDevices.size() > 1;
                final LocationArea2 locationArea = new LocationArea2();
                int startIndex = 0;
                
                if (hasDetectedDeviceMoreThanOnce) {
                    final DetectedDevice firstDetectedDevice = detectedDevices.get(0);
                    final DetectedDevice secondDetectedDevice = detectedDevices.get(1);
                    intersectionArea = locationArea.calculateIntersectionArea(firstDetectedDevice.getSensorPositionAtDetection(), secondDetectedDevice.getSensorPositionAtDetection(), Sensor.RADIUS);
                    startIndex = 2;
                }
                
                for (int currentIndex = startIndex; currentIndex < detectedDevices.size(); currentIndex++) {
                    final DetectedDevice currentDetectedDevice = detectedDevices.get(currentIndex);
                    intersectionArea = locationArea.calculateIntersectionArea(intersectionArea, currentDetectedDevice.getSensorPositionAtDetection(), Sensor.RADIUS);
                }
            }
            
            final boolean hasIntersection = intersectionArea != null;
            final Mobility predictedMobility;
            
            if (!hasIntersection) {
                predictedMobility = Mobility.MOBILE;
            } else {
                final boolean agentWasntDetected = sensorsThatDetectedTheDevice.size() == 0; 
                
                if(agentWasntDetected) {
                    predictedMobility = Mobility.UNDEFINED;
                } else {
                    predictedMobility = Mobility.FIXED;
                }
            }
            
            final AnalyzedData analyzedDevice = new AnalyzedData(Agent.class, agent.getMobility(), predictedMobility);
            analyzedData.add(analyzedDevice);
        }
        
        return analyzedData;
    }

    /**
     * <p></p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    private static List<Sensor> getSensorsFromDeviceList(List<Device> devices) {
        final List<Sensor> sensors = new ArrayList<Sensor>();
        
        for (Device device: devices) {
            if(device instanceof Sensor) {
                sensors.add((Sensor)device);
            }
        }
        
        return sensors;
    }

    /**
     * <p></p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    private static List<Agent> getAgentsFromDeviceList(List<Device> devices) {
        final List<Agent> agents = new ArrayList<Agent>();
        
        for (Device device: devices) {
            if(device instanceof Agent) {
                agents.add((Agent)device);
            }
        }
        
        return agents;
    }

    /**
     * <p>Gathers all sensors that have detected the device passed as argument</p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param sensors 
     * @param integer 
     * @param
     * @return
     */
    private static List<Sensor> getSensorsThatDetectedTheDevice(final Device device, final List<Sensor> sensors) {
        final List<Sensor> sensorsThatDetectedTheDevice = new ArrayList<Sensor>();
        final Integer deviceID = device.getId();
        
        for (Sensor sensor : sensors) {
            final boolean hasSensorDetectedTheDevice = sensor.getDetectedDevices().get(deviceID) != null; 
           
            if(hasSensorDetectedTheDevice) {
                sensorsThatDetectedTheDevice.add(sensor);
            }
        }
        
        return sensorsThatDetectedTheDevice;
    }
}
