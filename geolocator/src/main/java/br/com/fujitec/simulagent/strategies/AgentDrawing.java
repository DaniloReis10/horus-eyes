package br.com.fujitec.simulagent.strategies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import br.com.fujitec.simulagent.interfaces.DetectableDevice;
import br.com.fujitec.simulagent.models.Device;
import br.com.fujitec.simulagent.models.Mobility;
import br.com.fujitec.simulagent.ui.SimulationController;
import trilaceration.ScaleConverter;


/**
 * @author tiagoportela <tiagoporteladesouza@gmail.com>
 *
 */
public class AgentDrawing extends DrawStrategy {
	private ScaleConverter scale = SimulationController.getScaleInstance();

    @Override
    public void draw(Device device, Graphics graphics) {
        Color color = null;
        
        final DetectableDevice detectableDevice = (DetectableDevice) device;
        final boolean isDeviceMobilityFixed = device.getMobility().equals(Mobility.FIXED);
        final boolean isDeviceMobilityMobile = device.getMobility().equals(Mobility.MOBILE);
        
        if(detectableDevice.wasDetected()) {
            color = Color.YELLOW;
            detectableDevice.undetect();
        } else if (isDeviceMobilityFixed) {
            color = Color.ORANGE;
        } else if (isDeviceMobilityMobile) {
            color = Color.BLUE;
        }
        
        final int deviceCurrentX = scale.convertToX(device.getCurrentPosition());
        final int deviceCurrentY = scale.convertToY(device.getCurrentPosition());
        
        final int halfDrawSize = DrawStrategy.DRAW_SIZE_IN_PIXELS / 2;
        final int[] polygonXs = new int[]{deviceCurrentX, deviceCurrentX + halfDrawSize, deviceCurrentX - halfDrawSize};
        final int[] polygonYs = new int[]{deviceCurrentY - halfDrawSize, deviceCurrentY + halfDrawSize, deviceCurrentY + halfDrawSize};
        
        Polygon triangle = new Polygon(polygonXs, polygonYs, 3);
        graphics.setColor(color);
        graphics.fillPolygon(triangle);
        
        graphics.setColor(Color.BLACK);
        graphics.drawString(String.valueOf(device.getId()), deviceCurrentX, deviceCurrentY);
    }
}
