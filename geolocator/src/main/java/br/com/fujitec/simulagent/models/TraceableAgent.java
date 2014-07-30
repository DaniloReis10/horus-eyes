package br.com.fujitec.simulagent.models;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import br.com.fujitec.location.facade.IGeoPosition;
import trilaceration.ScaleConverter;


/**
 * @author tiagoportela <tiagoporteladesouza@gmail.com>
 *
 */
public class TraceableAgent extends Device {

    private IGeoPosition previousPosition;
    private List<IGeoPosition> route;
    
    public TraceableAgent(final Integer agentID, final Mobility mobility) {
        super(agentID, mobility);
        this.route = new ArrayList<IGeoPosition>();
    }

    @Override
    public void move() {
        if(hasMoved()) {
             this.route.add(this.currentPosition);
        }
        
        this.previousPosition = this.currentPosition;
        super.move();
    }
    
    @Override
    public void draw(Graphics graphics) {
        Color color = Color.BLUE;
        graphics.setColor(color);
        
        for(int position = 0; position < this.route.size() - 1; position++) {
            final int nextPosition = position + 1;
            final IGeoPosition firstPosition = this.route.get(position);
            final IGeoPosition secondPosition = this.route.get(nextPosition);
            
            final int x1 = ScaleConverter.convertToX(firstPosition);
            final int y1 = ScaleConverter.convertToY(firstPosition);
            final int x2 = ScaleConverter.convertToX(secondPosition);
            final int y2 = ScaleConverter.convertToY(secondPosition);
            
            graphics.drawLine(x1, y1, x2, y2);
        }
        
        graphics.fillRect(ScaleConverter.convertToX(currentPosition) - 3, ScaleConverter.convertToY(currentPosition) - 3, 6, 6);
    }
    
    public boolean hasMoved() {
        final boolean isFirstTime = this.previousPosition == null;
        final boolean isLatitudeDifferent = this.previousPosition != null && this.currentPosition.getLatitude() != this.previousPosition.getLatitude();
        final boolean isLongitudeDifferent = this.previousPosition != null && this.currentPosition.getLongitude() != this.previousPosition.getLongitude();
        final boolean hasMoved = isFirstTime || isLatitudeDifferent || isLongitudeDifferent;
        
        return hasMoved;
    }
}
