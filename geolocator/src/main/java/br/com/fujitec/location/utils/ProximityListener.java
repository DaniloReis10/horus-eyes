package br.com.fujitec.location.utils;

import br.com.fujitec.location.facade.IMobileDevice;
import br.com.fujitec.location.facade.IProximityListener;
import br.com.fujitec.location.geoengine.DevicesController;

public abstract class ProximityListener implements IProximityListener {
	protected IMobileDevice device1;
	protected IMobileDevice device2;
	protected String msg;	
	
	protected DevicesController control = DevicesController.getInstance();
	
	public void logMessage(String msg){
	}
}
