package br.com.fujitec.simulagent.factories;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.fujitec.location.facade.IGeoPosition;
import br.com.fujitec.location.geoengine.GeoPosition;
import br.com.fujitec.simulagent.models.Device;
import br.com.fujitec.simulagent.models.Mobility;
import br.com.fujitec.simulagent.models.ModelEnviroment;
import br.com.fujitec.simulagent.models.Path;
import br.com.fujitec.simulagent.models.Sensor;
import trilaceration.ScaleConverter;
import trilaceration.SensorPosition;

/**
 * @author tiagoportela <tiagoporteladesouza@gmail.com>
 *
 */
public class DeviceFactory {
    
    public static <T extends Device> List<Device> createStaticDevices(Class<T> deviceClass, int numberOfDevices) {
        final List<Device> devices = new ArrayList<Device>();
        
        for (int i = 0; i < numberOfDevices; i++) {
            final Integer deviceID = ModelEnviroment.getInstance().generateDeviceId();
            final Device device = DeviceFactory.createDevice(deviceClass, deviceID, Mobility.FIXED);

            final IGeoPosition position = createPosition(device);
            
            device.setCurrentPosition(position);
            devices.add(device);
        }
        
        return devices;
    }
    
    private static IGeoPosition createPosition(Device device) {
        final double latitude = ScaleConverter.latIni + Math.random() * (ScaleConverter.latEnd - ScaleConverter.latIni);
        final double longitude = ScaleConverter.longIni + Math.random() * (ScaleConverter.longEnd - ScaleConverter.longIni);
 
        if (device instanceof Sensor) {
            return new SensorPosition(latitude, longitude, Sensor.RADIUS_IN_METERS);
        } else {
            return new GeoPosition(latitude, longitude);
        }
    }
    
    public static <T extends Device> List<Device> createMobileDevices(Class<T> deviceClass, int numberOfDevices) {
        final List<Device> devices = new ArrayList<Device>();

        for (int i = 0; i < numberOfDevices; i++) {
            final Path path = createPath(deviceClass);
            final Integer deviceID = ModelEnviroment.getInstance().generateDeviceId();
            
            final Device device = DeviceFactory.createDevice(deviceClass, deviceID, Mobility.MOBILE); 
            device.setPath(path);
            device.setCurrentPosition(device.getPath().getFirstPosition());
            
            devices.add(device);
        }
        
        return devices;
    }

    
    private static <T> Path createPath(Class<T> deviceClass) {
        final short numberOfPositions;
        
        if (deviceClass.getSimpleName().equalsIgnoreCase("TraceableAgent")) {
            numberOfPositions = 3;
        } else {
            final Random randomNumberGenerator = new Random();
            numberOfPositions = (short) (randomNumberGenerator.nextInt(10) + 10);
        }
        
        if (deviceClass.getSimpleName().equalsIgnoreCase("Sensor")) {
            return PathFactory.createSensorPath(ScaleConverter.width, ScaleConverter.height, numberOfPositions);
        } else {
            return PathFactory.createAgentPath(ScaleConverter.width, ScaleConverter.height, numberOfPositions);
        }
    }
    
    private static <T extends Device> Device createDevice(Class<T> deviceClass, Integer deviceID, Mobility mobility) {
        Device device = null;
        
        try {
            final Constructor<T> constructor = deviceClass.getConstructor(Integer.class, Mobility.class);
            device = constructor.newInstance(deviceID, mobility);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        
        return device;
    }
}
