package br.com.fujitec.simulagent.strategies;

import java.awt.Graphics;

import br.com.fujitec.simulagent.models.Device;

/**
 * @author tiagoportela <tiagoporteladesouza@gmail.com>
 *
 */
public abstract class DrawStrategy {
    
    protected static final int DRAW_SIZE_IN_PIXELS = 10;
    
    /**
     * <p>Defines the algorithm for device movements</p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    public abstract void draw(Device device, Graphics graphics);
    
    public static DrawStrategy createDrawStrategy(Class<? extends Device> deviceClass) {
        DrawStrategy drawStrategy = null;
        
        if (deviceClass.getSimpleName().equalsIgnoreCase("Agent")) {
            drawStrategy = new AgentDrawing();
        } else if (deviceClass.getSimpleName().equalsIgnoreCase("Sensor")) {
            drawStrategy = new SensorDrawing();
        }
        
        return drawStrategy;
    }
}
