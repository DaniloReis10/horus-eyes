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
public class WirelessAgent extends MobileAgent implements IActionTick{
	private int                    mobility;// Tipo do agente Moével ou fixo 
	private GeoPosition     currentPosition;// Posicao corrente
	private int                 currentTime;// Tempo corrente em relacao 0:00 em minutos no dia
	private AgentPath                  path;// Rota do agente no dia

	public static final int  FIXED = 0;// Tipo de agente Fixo
	public static final int MOBILE = 1;// Tipo de agente Móvel

	/**
	 * Construtor generico
	 */
	public WirelessAgent() {
		super();
		path = new AgentPath();
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

	public GeoPosition getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(GeoPosition currentPosition) {
		if(currentPosition != null){
		   this.currentPosition = currentPosition;
		}
	}

	/**
	 * Interface de animaçao do agente
	 */
	@Override
	public void clearImage(BufferedImage image) {
		Graphics  g;

		g = image.getGraphics();
		g.setColor(Color.WHITE);
		g.fillOval(ScaleConverter.convertToX(currentPosition)-5, ScaleConverter.convertToY(currentPosition)-5, 10, 10);		
	}

	@Override
	public void drawImage(BufferedImage image, Color color) {
		Graphics  g;

		g = image.getGraphics();
		g.setColor(color);
		g.fillOval(ScaleConverter.convertToX(currentPosition)-5, ScaleConverter.convertToY(currentPosition)-5, 10, 10);		
	}

	@Override
	public void moveImage() {
		currentTime = (currentTime++) % PathFactory.TICKS_DAY;
		currentPosition = (GeoPosition) path.getPositionAtTime(currentTime);
	}
}