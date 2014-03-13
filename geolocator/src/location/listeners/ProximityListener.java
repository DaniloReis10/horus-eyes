package location.listeners;

import location.facade.IMobileDevice;
import location.facade.IProximityListener;
import location.geoengine.DevicesPositionControl;

public abstract class ProximityListener implements IProximityListener {
	protected IMobileDevice device1;
	protected IMobileDevice device2;
	protected String msg;	
	
	protected DevicesPositionControl control = DevicesPositionControl.getInstance();
	
	public void logMessage(String msg){
	}
}
