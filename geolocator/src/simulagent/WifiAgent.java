package simulagent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import location.geoengine.GeoPosition;

import trilaceration.Point;
import trilaceration.ScaleConverter;

public class WifiAgent implements IActionTick{
	private int                        type; 
	private GeoPosition     currentPosition;
	private int                 currentTime;
	private AgentPath                  path;

	public static final int  FIXED = 0;
	public static final int MOBILE = 1;

	public WifiAgent() {
		super();
		path = new AgentPath();
	}
	
	public AgentPath getPath() {
		return path;
	}

	public void setPath(AgentPath path) {
		this.path = path;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public GeoPosition getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(GeoPosition currentPosition) {
		if(currentPosition != null){
		   this.currentPosition = currentPosition;
		   //path.addPoint(currentPosition);
		}
	}

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
