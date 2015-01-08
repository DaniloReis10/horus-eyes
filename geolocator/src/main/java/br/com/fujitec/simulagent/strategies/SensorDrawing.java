package br.com.fujitec.simulagent.strategies;

import java.awt.Color;
import java.awt.Graphics;

import br.com.fujitec.simulagent.models.Device;
import br.com.fujitec.simulagent.models.Mobility;
import br.com.fujitec.simulagent.models.Sensor;
import br.com.fujitec.simulagent.ui.SimulationController;
import trilaceration.ScaleConverter;


/**
 * @author tiagoportela <tiagoporteladesouza@gmail.com>
 *
 */
public class SensorDrawing extends DrawStrategy {
	private ScaleConverter  scale = SimulationController.getScaleInstance();

    @Override
    public void draw(Device device, Graphics graphics) {
        Color color = null;
        
        final boolean isDeviceMobilityFixed = device.getMobility().equals(Mobility.FIXED);
        final boolean isDeviceMobilityMobile = device.getMobility().equals(Mobility.MOBILE);
        
        if (isDeviceMobilityFixed) {
            color = Color.BLACK;
        } else if (isDeviceMobilityMobile) {
            color = Color.RED;
        }
        
        final int diameter = Sensor.RADIUS_IN_PIXELS * 2;
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillOval(scale.convertToX(device.getCurrentPosition()) - Sensor.RADIUS_IN_PIXELS, scale.convertToY(device.getCurrentPosition()) - Sensor.RADIUS_IN_PIXELS, diameter, diameter);

        final int halfDrawSize = DrawStrategy.DRAW_SIZE_IN_PIXELS / 2;
        graphics.setColor(color);
        graphics.fillRect(scale.convertToX(device.getCurrentPosition()) - halfDrawSize, scale.convertToY(device.getCurrentPosition()) - halfDrawSize, DrawStrategy.DRAW_SIZE_IN_PIXELS, DrawStrategy.DRAW_SIZE_IN_PIXELS);
        
        graphics.setColor(Color.BLACK);
        graphics.drawString(String.valueOf(device.getId()), scale.convertToX(device.getCurrentPosition()), scale.convertToY(device.getCurrentPosition()));
    }
}
