package simulagent;

import java.awt.Color;
import java.awt.Graphics;

import trilaceration.ScaleConverter;


/**
 * @author tiagoportela <tiagoporteladesouza@gmail.com>
 *
 */
public class SensorDrawing extends DrawStrategy {

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
        graphics.fillOval(ScaleConverter.convertToX(device.getCurrentPosition()) - Sensor.RADIUS_IN_PIXELS, ScaleConverter.convertToY(device.getCurrentPosition()) - Sensor.RADIUS_IN_PIXELS, diameter, diameter);

        final int halfDrawSize = DrawStrategy.DRAW_SIZE_IN_PIXELS / 2;
        graphics.setColor(color);
        graphics.fillRect(ScaleConverter.convertToX(device.getCurrentPosition()) - halfDrawSize, ScaleConverter.convertToY(device.getCurrentPosition()) - halfDrawSize, DrawStrategy.DRAW_SIZE_IN_PIXELS, DrawStrategy.DRAW_SIZE_IN_PIXELS);
        
        graphics.setColor(Color.BLACK);
        graphics.drawString(String.valueOf(device.getId()), ScaleConverter.convertToX(device.getCurrentPosition()), ScaleConverter.convertToY(device.getCurrentPosition()));
    }
}
