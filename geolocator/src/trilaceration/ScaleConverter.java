package trilaceration;

import location.facade.*;
public class ScaleConverter {

	public static double  latIni;
	public static double  latEnd;
	public static double longIni;
	public static double longEnd;
	public static int      width;
	public static int     height;
	/**
	 * 
	 * @param position
	 * @return
	 */
	public static int convertToX(IGeoPosition position) {
         double deltaLat;
         int           x;
         
         deltaLat = (position.getLatitude() - latIni)/(latEnd - latIni);
         x = (int)(deltaLat*width);
         return x;
	}
    /**
     * 
     * @param position
     * @return
     */
	public static int convertToY(IGeoPosition position) {
        double deltaLong;
        int            y;
        
        deltaLong = (position.getLongitude() - longIni)/(longEnd - longIni);
        y = (int)(deltaLong*height);
        return y;
	}
	/**
	 * 
	 * @param x
	 * @return
	 */
	public static double convertToLatitude(int x){
        double  deltaLat;
        double  latitude;
        
        deltaLat = (latEnd - latIni)/width;
        latitude = x * deltaLat + latIni;		
		return latitude;
	}
	/**
	 * 
	 * @param y
	 * @return
	 */
	public static double convertToLongitude(int y){
        double deltaLong;
        double longitude;
        
        deltaLong = (longEnd - longIni)/width;
        longitude = y * deltaLong + longIni;		
		return longitude;
	}
	
}
