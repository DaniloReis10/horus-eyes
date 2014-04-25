package simulagent;

import location.geoengine.GeoPosition;

/**
 * @author tiagoportela <tiagoporteladesouza@gmail.com>
 *
 */
public class MobileMovement extends MovementStrategy {

    @Override
    public void move(Device device) {
        final int deviceCurrentTime = device.getCurrentTime();
        device.setCurrentTime(deviceCurrentTime % PathFactory.TICKS_DAY);
        
        final GeoPosition deviceCurrentPosition = (GeoPosition) device.getPath().getPositionAtTime(deviceCurrentTime); 
        device.setCurrentPosition(deviceCurrentPosition);
        
        device.increaseCurrentTime();
    }
}
