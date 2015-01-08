package trilaceration;
 
import br.com.fujitec.location.facade.*;
import br.com.fujitec.location.geoengine.DevicesController;
import br.com.fujitec.location.geoengine.GeoPosition;

public class ScaleConverter {
	public static final double EARTHRATIO = 6378160; // Raio da terra em metros
    private double  latIni;
    private double  latEnd;
    private double longIni;
    private double longEnd;
    private int      width;
    private int     height;
	double      h1, h2, hm;
    double      w1, w2, wm;
    
    
    public ScaleConverter(double latIni, double latEnd, double longIni,
 			double longEnd, int width, int height) {
 		super();
 		this.latIni = latIni;
 		this.latEnd = latEnd;
 		this.longIni = longIni;
 		this.longEnd = longEnd;
 		this.width = width;
 		this.height = height;
    	
    	// Gera os pontos de referencia da area
    	final GeoPosition bottomLeftCorner = new GeoPosition (latIni,longIni);
    	final GeoPosition bottomRightCorner = new GeoPosition (latIni,longEnd);
    	final GeoPosition topLeftCorner = new GeoPosition (latEnd,longIni);
        final GeoPosition topRightCorner = new GeoPosition (latEnd,longEnd);
    	
        h1 = DevicesController.calculateDistanceMeters(topLeftCorner, bottomLeftCorner);
        h2 = DevicesController.calculateDistanceMeters(topRightCorner, bottomRightCorner);
        
        hm = (h1 + h2) / 2;
    	
        w1 = DevicesController.calculateDistanceMeters(bottomLeftCorner, bottomRightCorner);
        w2 = DevicesController.calculateDistanceMeters(topLeftCorner, topRightCorner);
        
        wm = (w1 + w2) / 2;
 	}
 	public double getLatIni() {
 		return latIni;
 	}
 	public void setLatIni(double latIni) {
 		this.latIni = latIni;
 	}
 	public double getLatEnd() {
 		return latEnd;
 	}
 	public void setLatEnd(double latEnd) {
 		this.latEnd = latEnd;
 	}
 	public double getLongIni() {
 		return longIni;
 	}
 	public void setLongIni(double longIni) {
 		this.longIni = longIni;
 	}
 	public double getLongEnd() {
 		return longEnd;
 	}
 	public void setLongEnd(double longEnd) {
 		this.longEnd = longEnd;
 	}
 	public int getWidth() {
 		return width;
 	}
 	public void setWidth(int width) {
 		this.width = width;
 	}
 	public int getHeight() {
 		return height;
 	}
 	public void setHeight(int height) {
 		this.height = height;
 	}
   
    /**
     * <p>This method receives an GeoPosition, extracts its Longitude and computes its equivalent in pixels</p> 
     * @param position
     * @return
     */
    public int convertToX(IGeoPosition position) {
         double deltaLong;
         int            x;
 
        if( (latIni < latEnd)&&(longIni < longEnd) &&(width > 0) &&(height > 0)){
    
             deltaLong = (position.getLongitude() - longIni)/(longEnd - longIni);
             x = (int)(deltaLong*width);
             return x;
        }
        return 0;
    }
    /**
     * 
     * @param position
     * @return
     */
    public int convertToY(IGeoPosition position) {
        double  deltaLat;
        int            y;

        if( (latIni < latEnd)&&(longIni < longEnd) &&(width > 0) &&(height > 0)){
            deltaLat = (position.getLatitude() - latIni)/(latEnd - latIni);
            y = (int)(height - deltaLat*height);
            return y;
        }
        return 0;
    }
    /**
     * 
     * @param x
     * @return
     */
    public double convertToLongitude(int x){
        double  deltaLong;
        double  longitude;

        if( (latIni < latEnd)&&(longIni < longEnd) &&(width > 0) &&(height > 0)){
            deltaLong = (longEnd - longIni)/width;
            longitude = (x) * deltaLong + longIni;        
            return longitude;
        }
        return 0;
    }
    /**
     * 
     * @param y
     * @return
     */
    public double convertToLatitude(int y){
        double deltaLat;
        double  latitude;
        if( (latIni < latEnd)&&(longIni < longEnd) &&(width > 0) &&(height > 0)){
            deltaLat = (latEnd - latIni)/height;
            latitude = (height - y) * deltaLat + latIni;        
            return latitude;
        }
        return 0;
    }
    
    public double convertToGrades(double distance, double latitude, double longitude){
    	double  rdistance;
        double glat, glong;
    	
        
        glat = (latEnd - latIni)*distance/hm;
        glong = (longEnd -longIni)*distance/wm;
        
        
   	    // calculo da distancia fazendo correcao pela latitude
        rdistance = (glat+glong)/2;

    	return rdistance;
    }
    
    public int convertDistanceXToPixels(double distance){
    	int   distancePixels;
    	
    	distancePixels = (int) Math.round((distance*width)/wm);
    	return distancePixels;
    }

    public int convertDistanceYToPixels(double distance){
    	int   distancePixels;
    	
    	distancePixels = (int) Math.round((distance*height)/hm);
    	return distancePixels;
    }    
    public Rpixel convertToPixel(double distance, double latitude, double longitude, int h_pixel, int w_pixel){

    	Rpixel rpixel;
        
        rpixel = new Rpixel( (distance/(wm/w_pixel)), (distance/(hm/h_pixel)) );

    	return rpixel;
    }
    public double convertPixelDistanceToMeters(long distancePixel){
    	double distance, dx,dy;
    	
    	dx = (distancePixel * wm)/width;
    	dy = (distancePixel * hm)/ height;
    	distance = (dx+dy)/2;
    	return distance;
    }
    public void print(){
    	System.out.printf("lat =%f  long = %f \n", latIni,longIni);
      	System.out.printf("lat =%f  long = %f \n", latEnd,longEnd);
      	System.out.printf("w = %d h=  %d\n", width,height);
    }
	public void printScalePixelCorrespondence() {
		// TODO Auto-generated method stub
		
	}

    
    /*
    public double convertToMeters(double distP, int h_pixel, int w_pixel){

    	double rMeters;
        
        rMeters = ( (distP*(wm/w_pixel)) + (distP*(hm/h_pixel)) )/2;

    	return rMeters;
    }*/
    
}