package soundid;

import location.facade.IGeoPosition;
import location.facade.IMobileDevice;
import location.facade.IProximityListener;
import location.facade.IVisibilityListener;
import location.geoengine.DevicePath;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import simulagent.Device;
import simulagent.Mobility;

/**
 * Classe do agente que possui um interface de rede sem fio
 * 
 * @author DaniloReis
 * 
 */
public class MobileAgent extends Device implements IMobileDevice {
    /**
     * Construtor com inicialização do ID
     * @param integer 
     */
    public MobileAgent(Integer agentID) {
        super(agentID, Mobility.MOBILE);
    }

    @Override
    public void setGeoPosition(IGeoPosition position) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public IGeoPosition getGeoPosition() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double getDistanceFrom(IMobileDevice device) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getLastDistance() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Integer getType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setType(Integer type) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDistanceOn(double dist) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setGroup(Integer group) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Integer getGroup() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double getDistanceOn() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setDevicePath(DevicePath path) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public DevicePath getDevicePath() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setProximityListener(IProximityListener listener) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public IProximityListener getProximityListener() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IVisibilityListener getVisibilityListener() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toXML() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void toXML(Element devices, Document doc) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void fromXML(Element item) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void startListener() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stopListener() {
        // TODO Auto-generated method stub
        
    }
}
