package br.com.fujitec.soundid;

import java.awt.Color;
import java.awt.Graphics;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import br.com.fujitec.location.facade.IGeoPosition;
import br.com.fujitec.location.facade.IMobileDevice;
import br.com.fujitec.location.facade.IProximityListener;
import br.com.fujitec.location.facade.IVisibilityListener;
import br.com.fujitec.location.geoengine.DevicePath;
import br.com.fujitec.location.geoengine.GeoPosition;
import br.com.fujitec.simulagent.factories.PathFactory;
import br.com.fujitec.simulagent.interfaces.IActionTick;
import br.com.fujitec.simulagent.models.Mobility;
import trilaceration.ScaleConverter;

public class RFAgent implements IMobileDevice, IActionTick {
    protected Mobility mobility;           // Tipo do agente Moével ou fixo
    protected GeoPosition currentPosition;      // Posicao corrente
    protected int currentTime;                  // Tempo corrente em relacao 0:00 em minutos no dia

    protected Integer id;
    
    public RFAgent(Integer agentID) {
        this.mobility = Mobility.FIXED;
        this.id = agentID;
    }

    /**
     * 
     */
    public RFAgent() {}

    public GeoPosition getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(IGeoPosition currentPosition) {
        if (currentPosition != null) {
            this.currentPosition = (GeoPosition) currentPosition;
        }
    }

    @Override
    public Integer getId() {
        return id;
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
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public void move() {
        currentTime = (currentTime) % PathFactory.TICKS_DAY;
        currentTime++;
    }

    @Override
    public void startListener() {

    }

    @Override
    public void stopListener() {
    }

    public Mobility getMobility() {
        return mobility;
    }

    /**
     * <p>Draws a rect representing fixed agents</p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.RED); // COR ESPECÍFICA PARA REPRESENTAR AGENTE FIXO
        graphics.fillRect(ScaleConverter.convertToX(currentPosition) - 5, ScaleConverter.convertToY(currentPosition) - 5, 10, 10);
    }
}
