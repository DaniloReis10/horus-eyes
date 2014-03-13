package simulagent;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import location.facade.IGeoPosition;
import location.geoengine.GeoPosition;

import trilaceration.ScaleConverter;

public class PathFactory {
	
    public static final int TICKS_DAY = 24 * 60;
    
	public PathFactory() {
		super();
	}
    /**
    * 
    * @param width
    * @param height
    * @param n
    * @return
    */
	public AgentPath create(int width,int height,int n){
		ArrayList<PositionPath>     points;
		int i,dayTime,timePoint,timeWindow;
		double          latitude,longitude;
		int                      posX,posY;
		int                      startTime;
		int                       timeTrip;
		PositionPath                 point;
		GeoPosition               geoPoint;
		
		points = new ArrayList<PositionPath>();
		dayTime    = TICKS_DAY;
		timeWindow = dayTime / n;
		startTime  = 0;
		for( i = 0; i < n; i++){
			timePoint = (int) (timeWindow * Math.random());
			posX = (int) (width * Math.random());
			posY = (int) (height * Math.random());
			latitude = ScaleConverter.convertToLatitude(posX);
			longitude = ScaleConverter.convertToLongitude(posY);
			geoPoint = new GeoPosition(new Date(),latitude,longitude);
			timeTrip = (int)(Math.random() * 60);
			if( i != (n-1)){
				point = new PositionPath(geoPoint,startTime,timePoint);
				point.setLeaveTime(startTime+timePoint);
				dayTime -= (timePoint + timeTrip);
				startTime = startTime + timePoint + timeTrip; 
			}
			else{
				point = new PositionPath(geoPoint,startTime,dayTime);
				point.setLeaveTime(startTime+dayTime);
			}
			points.add(point);
		}
		return new AgentPath(points);
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PathFactory               factory;
		ArrayList<PositionPath>      data;
		Iterator<PositionPath>   iterator;
		PositionPath             position;
		AgentPath                    path;
		int                             i;
		IGeoPosition                  pos;
		
		factory = new PathFactory();
		// seta escalas
		ScaleConverter.height = 200;
		ScaleConverter.width  = 200;
		ScaleConverter.latIni = 0;
		ScaleConverter.latEnd = 2.0;
		ScaleConverter.longIni = 0;
		ScaleConverter.longEnd = 2.0;
		
		path = factory.create(200, 200, 3);
		data = path.getDataPath();
		iterator = data.iterator();
		while(iterator.hasNext()){
			position = iterator.next();
			position.print();
		}
		    
		path = new AgentPath();
		path.setDataPath(data);
		for( i = 0; i < 1440; i++){
			pos = path.getPositionAtTime(i);
			System.out.printf("lat = %f long = %f time = %d \n", pos.getLatitude(),pos.getLongitude(),i);
		}

	}

}
