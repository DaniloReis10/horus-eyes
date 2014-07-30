package br.com.fujitec.simulagent.strategies;

import br.com.fujitec.simulagent.factories.PathFactory;
import br.com.fujitec.simulagent.models.Device;


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
