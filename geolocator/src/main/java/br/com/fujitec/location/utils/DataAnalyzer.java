package br.com.fujitec.location.utils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import trilaceration.LocationArea;
import trilaceration.ScaleConverter;
import br.com.fujitec.simulagent.models.Agent;
import br.com.fujitec.simulagent.models.AnalyzedData;
import br.com.fujitec.simulagent.models.DetectedDevice;
import br.com.fujitec.simulagent.models.Device;
import br.com.fujitec.simulagent.models.Mobility;
import br.com.fujitec.simulagent.models.Sensor;
import br.com.fujitec.simulagent.ui.SimulationFrame;

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
    public static List<AnalyzedData> predictDevicesClasses(final List<Device> devices, final SimulationFrame simulationFrame) {
        final List<Agent> agents = getAgentsFromDeviceList(devices);
        final List<Sensor> sensors = getSensorsFromDeviceList(devices);
        
        final List<AnalyzedData> analyzedData = new ArrayList<AnalyzedData>();
        
        for (int index = 0; index < agents.size(); index++) {
            final Agent agent = agents.get(index);
            final List<Sensor> sensorsThatDetectedTheDevice = getSensorsThatDetectedTheDevice(agent, sensors);
            final Integer agentID = agent.getId();
            BufferedImage intersectionArea = new BufferedImage(ScaleConverter.width, ScaleConverter.height, BufferedImage.TYPE_INT_RGB);
            final Graphics intersectionGraphics = intersectionArea.getGraphics();
            intersectionGraphics.setColor(LocationArea.BASE_AREA_COLOR);
            intersectionGraphics.fillRect(0, 0, ScaleConverter.width, ScaleConverter.height);
            
            boolean isFirstSensorAnalysis = true;
            
            for (Sensor sensor : sensorsThatDetectedTheDevice) {
                final List<DetectedDevice> detectedDevices = sensor.getDetectedDevices().get(agentID);
                final boolean hasSensorDetectedDeviceMoreThanOnce = detectedDevices.size() > 1;
                final LocationArea locationArea = new LocationArea();
                int startIndex = 0;
                
                if (hasSensorDetectedDeviceMoreThanOnce && isFirstSensorAnalysis) {
                    final DetectedDevice firstDetectedDevice = detectedDevices.get(0);
                    final DetectedDevice secondDetectedDevice = detectedDevices.get(1);
                    intersectionArea = locationArea.calculateIntersectionArea(firstDetectedDevice.getSensorPositionAtDetection(), secondDetectedDevice.getSensorPositionAtDetection(), Sensor.RADIUS_IN_PIXELS);
                    startIndex = 2;
                    isFirstSensorAnalysis = false;
                }
                
                for (int currentIndex = startIndex; currentIndex < detectedDevices.size(); currentIndex++) {
                    final DetectedDevice currentDetectedDevice = detectedDevices.get(currentIndex);
                    intersectionArea = locationArea.calculateIntersectionArea(intersectionArea, currentDetectedDevice.getSensorPositionAtDetection(), Sensor.RADIUS_IN_PIXELS);
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

            final int analysisTime = (index+1) * 100 / agents.size();
            simulationFrame.updateAnalysisProgressBar(agents.size(), analysisTime);
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
