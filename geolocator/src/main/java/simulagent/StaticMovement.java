package simulagent;


/**
 * @author tiagoportela <tiagoporteladesouza@gmail.com>
 *
 */
public class StaticMovement extends MovementStrategy {

    @Override
    public void move(Device device) {
        final int deviceCurrentTime = device.getCurrentTime();
        device.setCurrentTime(deviceCurrentTime % PathFactory.TICKS_DAY);
        
        device.increaseCurrentTime();
    }
}
