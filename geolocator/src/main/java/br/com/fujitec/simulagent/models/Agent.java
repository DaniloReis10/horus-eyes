package br.com.fujitec.simulagent.models;

import br.com.fujitec.location.facade.GeopositionFacade;
import br.com.fujitec.location.facade.IGeoPosition;
import br.com.fujitec.simulagent.interfaces.DetectableDevice;


/**
 * @author tiagoportela <tiagoporteladesouza@gmail.com>
 *
 */
public class Agent extends Device implements DetectableDevice{

    private IGeoPosition previousPosition;
    private boolean hasBeenDetected;
    
    public Agent(Integer agentID, Mobility mobility) {
        super(agentID, mobility);
        this.hasBeenDetected = false;
    }

    @Override
    public void move() {
        if (this.previousPosition == null) {
            GeopositionFacade.getInstance().notifyDetectors();
        }
        
        this.previousPosition = this.currentPosition;
        super.move();
        this.notifySensors();
    }
    
    @Override
    public double getDistanceFrom(DetectableDevice device) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void notifySensors() {
        if (hasMoved()) {
            GeopositionFacade.getInstance().notifyDetectors();
        }
    }

    @Override
    public boolean hasMoved() {
        final boolean isLatitudeDifferent = this.previousPosition != null && this.currentPosition.getLatitude() != this.previousPosition.getLatitude();
        final boolean isLongitudeDifferent = this.previousPosition != null && this.currentPosition.getLongitude() != this.previousPosition.getLongitude();
        final boolean hasMoved = isLatitudeDifferent || isLongitudeDifferent;
        
        return hasMoved;
    }

    @Override
    public boolean wasDetected() {
        return this.hasBeenDetected;
    }

    @Override
    public void detect() {
        this.hasBeenDetected = true;
    }

    @Override
    public void undetect() {
        this.hasBeenDetected = false;
    }
}
