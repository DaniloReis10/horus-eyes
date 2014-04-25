package simulagent;

import location.facade.DetectableDevice;
import location.facade.GeopositionFacade;
import location.facade.IGeoPosition;


/**
 * @author tiagoportela <tiagoporteladesouza@gmail.com>
 *
 */
public class Agent extends Device implements DetectableDevice{

    private IGeoPosition previousPosition;
    
    public Agent(Integer agentID, Mobility mobility) {
        super(agentID, mobility);
    }

    @Override
    public void move() {
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
        final boolean isLatitudeDifferent = this.currentPosition.getLatitude() != this.previousPosition.getLatitude();
        final boolean isLongitudeDifferent = this.currentPosition.getLongitude() != this.previousPosition.getLongitude();
        final boolean hasMoved = isLatitudeDifferent || isLongitudeDifferent;
        
        return hasMoved;
    }
}
