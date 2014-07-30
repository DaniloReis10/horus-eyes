package br.com.fujitec.simulagent.strategies;

import br.com.fujitec.simulagent.models.Device;
import br.com.fujitec.simulagent.models.Mobility;

/**
 * @author tiagoportela <tiagoporteladesouza@gmail.com>
 *
 */
public abstract class MovementStrategy {

    /**
     * <p>Defines the algorithm for device movements</p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    public abstract void move(Device device);
    
    public static MovementStrategy createMovementStrategy(Mobility mobility) {
        MovementStrategy movementStrategy = null;
        
        if (mobility.equals(Mobility.FIXED)) {
            movementStrategy = new StaticMovement();
        } else if (mobility.equals(Mobility.MOBILE)) {
            movementStrategy = new MobileMovement();
        }
        
        return movementStrategy;
    }
}
