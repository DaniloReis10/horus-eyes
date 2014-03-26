package simulagent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import location.facade.IGeoPosition;
import location.facade.IMobileDevice;
import location.facade.IProximityListener;
import location.facade.IVisibilityListener;
import location.geoengine.DevicePath;
import location.geoengine.GeoPosition;

import trilaceration.Point;
import trilaceration.ScaleConverter;

/**
 * Classe do agente que possui um interface de rede sem fio
 * @author DaniloReis
 *
 */
public class MobileAgent extends RFAgent{
	private AgentPath                  path;// Rota do agente no dia
	private boolean                   moved; 
	private GeoPosition         oldPosition;


	/**
	 * Construtor generico
	 */
	public MobileAgent() {
		super();
		path     = new AgentPath();
		moved    = false;
		mobility = RFAgent.MOBILE;
		
	}
	// Metodos set e gets
	public AgentPath getPath() {
		return path;
	}

	public void setPath(AgentPath path) {
		this.path = path;
	}
	
	public int getMobility() {
		return mobility;
	}

	public void setMobility(int mobility) {
		this.mobility = mobility;
	}

	@Override
	public void clearImage(BufferedImage image) {
		Graphics  g;

	//	if( moved){
			g = image.getGraphics();
			g.setColor(Color.WHITE);
			g.fillOval(ScaleConverter.convertToX(currentPosition)-5, ScaleConverter.convertToY(currentPosition)-5, 10, 10);	
	//		moved = false;
	//	}
	}

	@Override
	public void drawImage(BufferedImage image, Color color) {
		Graphics  g;

		//if(moved){
			g = image.getGraphics();
			g.setColor(Color.MAGENTA);
			g.fillOval(ScaleConverter.convertToX(currentPosition)-5, ScaleConverter.convertToY(currentPosition)-5, 10, 10);		
			//moved = false;
		//}
	}

	@Override
	public void moveImage() {
		currentTime = (currentTime) % PathFactory.TICKS_DAY;
	    oldPosition = currentPosition;
		currentPosition = (GeoPosition) path.getPositionAtTime(currentTime);
		currentTime++;
//		if ((currentPosition.getLatitude()!= oldPosition.getLatitude())||(currentPosition.getLongitude()!= oldPosition.getLongitude())){
//		    oldPosition = currentPosition;
//		    moved = true;
//		}
	}
	


}
