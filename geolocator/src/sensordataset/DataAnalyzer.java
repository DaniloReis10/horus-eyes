package sensordataset;

import java.util.ArrayList;
import java.util.List;

import simulagent.Device;
import simulagent.Sensor;

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
     */
    public static void predictDevicesClasses(final List<Device> devices, final List<Sensor> sensors) {
        for (Device device: devices) {
            final List<Sensor> sensorsThatDetectedTheDevice = getSensorsThatDetectedTheDevice(device, sensors);
            final Integer deviceID = device.getId();
            final int numberOfSensors = sensorsThatDetectedTheDevice.size();
            
            for(int sensor1 = 0; sensor1 < numberOfSensors; sensor1++) {
                final List<DetectedDevice> baseSensorDetectedDevicesInformation = sensorsThatDetectedTheDevice.get(sensor1).getDetectedDevices().get(deviceID);
                
                for(int sensor2 = sensor1 + 1; sensor2 < numberOfSensors; sensor2++) {
                    final List<DetectedDevice> comparedSensorDetectedDevicesInformation = sensorsThatDetectedTheDevice.get(sensor2).getDetectedDevices().get(deviceID);
                    
                    predictDeviceClass(baseSensorDetectedDevicesInformation, comparedSensorDetectedDevicesInformation);
                }
            }
        }
    }

    /**
     * <p>Uses trilaceration to determine if the detected device is fixed or mobile.</p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    private static void predictDeviceClass(List<DetectedDevice> detectedDeviceInformation1, List<DetectedDevice> detectedDeviceInformation2) {
        for (DetectedDevice detectedDevice1 : detectedDeviceInformation1) {
            for (DetectedDevice detectedDevice2 : detectedDeviceInformation2) {
                //TODO TRILACERATION
            }
        }
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
