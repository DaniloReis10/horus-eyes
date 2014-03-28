package soundid;

import simulagent.ModelEnviroment;

public class SoundIdModel extends ModelEnviroment {
    public static final int TAG_TYPE   = 1;
    public static final int BASE_TYPE  = 2;
    public static final int COORD_TYPE = 3;
    public static final double DISTANCE_DETECTION = 10.0;
    public static final double TIME_TICK = 0.001;
    private static double      latitudeIni;
    private static double     longitudeIni;
    private static double      latitudeEnd;
    private static double     longitudeEnd;
    private static int               width;
    private static int              height;
    
    public static double getSoundSpeed(double temperature){
    	return 349.00;
    }
    public static void setLatitudeIni(double latitude){
    	latitudeIni = latitude;
    }

    public static void setLongitudeIni(double longitude){
    	longitudeIni = longitude;
    }

    public static void setLatitudeEnd(double latitude){
    	latitudeEnd = latitude;
    }

    public static void setLongitudeEnd(double longitude){
    	longitudeEnd = longitude;
    }
    public static double getLatitudeIni(){
    	return latitudeIni;
    }

    public static double getLongitudeIni(){
    	return longitudeIni;
    }

    public static double getLatitudeEnd(){
    	return latitudeEnd;
    }

    public static double getLongitudeEnd(){
    	return longitudeEnd;
    }
    
    public static void setWidth(int w){
    	width = w;
    }
    public static int getWidth(){
    	return width;
    }
    public static void setHeight(int h){
    	height = h;
    }
    public static int getHeight(){
    	return height;
    }
    
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}

}
