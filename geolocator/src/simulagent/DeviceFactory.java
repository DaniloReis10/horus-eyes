package simulagent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import location.geoengine.GeoPosition;
import trilaceration.ScaleConverter;

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

            final double latitude = ScaleConverter.latIni + Math.random() * (ScaleConverter.latEnd - ScaleConverter.latIni);
            final double longitude = ScaleConverter.longIni + Math.random() * (ScaleConverter.longEnd - ScaleConverter.longIni);
            final GeoPosition position = new GeoPosition(latitude, longitude);
            
            device.setCurrentPosition(position);
            devices.add(device);
        }
        
        return devices;
    }
    
    public static <T extends Device> List<Device> createMobileDevices(Class<T> deviceClass, int numberOfDevices) {
        final Random randomNumberGenerator = new Random();
        final List<Device> devices = new ArrayList<Device>();

        for (int i = 0; i < numberOfDevices; i++) {
            final short numberOfPositions = (short) (randomNumberGenerator.nextInt(10) + 1);
            final Path path = PathFactory.createPath(ScaleConverter.width, ScaleConverter.height, numberOfPositions);
            final Integer deviceID = ModelEnviroment.getInstance().generateDeviceId();
            
            final Device device = DeviceFactory.createDevice(deviceClass, deviceID, Mobility.MOBILE); 
            device.setPath(path);
            device.setCurrentPosition(device.getPath().getFirstPosition());
            
            devices.add(device);
        }
        
        return devices;
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
