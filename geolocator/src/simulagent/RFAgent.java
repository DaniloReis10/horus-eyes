package simulagent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import trilaceration.ScaleConverter;

import location.facade.IGeoPosition;
import location.facade.IMobileDevice;
import location.facade.IProximityListener;
import location.facade.IVisibilityListener;
import location.geoengine.DevicePath;
import location.geoengine.GeoPosition;

public class RFAgent implements IMobileDevice,IActionTick {
	protected int                    mobility;// Tipo do agente Moével ou fixo 
	protected GeoPosition     currentPosition;// Posicao corrente
	protected int                 currentTime;// Tempo corrente em relacao 0:00 em minutos no dia
    private boolean                    drawed;
	
	public static final int  FIXED = 0;// Tipo de agente Fixo
	public static final int MOBILE = 1;// Tipo de agente Móvel
	
	
	public RFAgent() {
		// TODO Auto-generated constructor stub
		drawed   = false;
		mobility = FIXED;
	}

	public GeoPosition getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(IGeoPosition currentPosition) {
		if(currentPosition != null){
		   this.currentPosition = (GeoPosition) currentPosition;
		}
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub

	}
	/**
	 * Interface de animaçao do agente
	 */
	@Override
	public void clearImage(BufferedImage image) {
		Graphics  g;

		//if( !drawed){
			g = image.getGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(ScaleConverter.convertToX(currentPosition)-5, ScaleConverter.convertToY(currentPosition)-5, 10, 10);		
		//	drawed = true;
		//}
	}

	@Override
	public void drawImage(BufferedImage image, Color color) {
		Graphics  g;

		//if( !drawed){
			g = image.getGraphics();
			g.setColor(Color.GREEN);
			g.fillRect(ScaleConverter.convertToX(currentPosition)-5, ScaleConverter.convertToY(currentPosition)-5, 10, 10);		
			//drawed = true;
		//}
	}

	@Override
	public void moveImage() {
		currentTime = (currentTime) % PathFactory.TICKS_DAY;
		currentTime++;
	}

	@Override
	public void startListener() {

	}

	@Override
	public void stopListener() {
	}

}
