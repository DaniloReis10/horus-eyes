package trilaceration;

import br.com.fujitec.location.facade.*;
import br.com.fujitec.location.geoengine.DevicesController;
import br.com.fujitec.location.geoengine.GeoPosition;
public class ScaleConverter {

    public static final double EARTH_RADIUS = 6378160; // Raio da terra em metros
    public static double  latIni;
    public static double  latEnd;
    public static double longIni;
    public static double longEnd;
    public static int      width;
    public static int     height;
    
    /**
     * <p>This method receives an GeoPosition, extracts its Longitude and computes its equivalent in pixels</p> 
     * @param position
     * @return
     */
    public static int convertToX(IGeoPosition position) {
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
    public static int convertToY(IGeoPosition position) {
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
    public static double convertToLongitude(int x){
        double  deltaLong;
        double  longitude;

        if( (latIni < latEnd)&&(longIni < longEnd) &&(width > 0) &&(height > 0)){
            deltaLong = (longEnd - longIni)/width;
            longitude = x * deltaLong + longIni;        
            return longitude;
        }
        return 0;
    }
    /**
     * 
     * @param y
     * @return
     */
    public static double convertToLatitude(int y){
        double deltaLat;
        double  latitude;
        if( (latIni < latEnd)&&(longIni < longEnd) &&(width > 0) &&(height > 0)){
            deltaLat = (latEnd - latIni)/height;
            latitude = (height - y) * deltaLat + latIni;        
            return latitude;
        }
        return 0;
    }
    
    public static double convertToGrades(double distance, double latitude, double longitude){
    	double  rdistance;
    	
    	// Gera os pontos de referencia da area
    	final GeoPosition topLeftCorner = new GeoPosition (latIni,longIni);
    	final GeoPosition topRightCorner = new GeoPosition (latIni,longEnd);
    	final GeoPosition bottomLeftCorner = new GeoPosition (latEnd,longIni);
        final GeoPosition bottomRightCorner = new GeoPosition (latEnd,longEnd);
    	
    	final double topDistance = DevicesController.calculateDistance(topLeftCorner, topRightCorner);
   	    final double bottomDistance = DevicesController.calculateDistance(bottomLeftCorner, bottomRightCorner);

   	    // calculo da distancia fazendo correcao pela latitude
    	rdistance = topDistance + ((latitude - latIni) / (latEnd - latIni)) * (bottomDistance - topDistance);

    	return rdistance;
    }
    
    public static void printScalePixelCorrespondence() {
        
        ScaleConverter.width  = 978;
        ScaleConverter.height = 670;
        
        System.out.println("EARTH RADIUS  = 6378160 metros");
        System.out.println("SCREEN WIDTH  = 978");
        System.out.println("SCREEN HEIGHT = 670");
        
        System.out.println("==================================");
        
        final double[] scales = {1, 0.5, 0.25, 0.1, 0.06, 0.05, 0.025, 0.01, 0.006, 0.00332, 0.003};
        
        for (int scale = 0; scale < scales.length; scale++) {
            
            ScaleConverter.latEnd = scales[scale];
            ScaleConverter.longEnd = scales[scale];
            
            final double firstLongitude = ScaleConverter.convertToLongitude(0);
            final double firstLatitude = ScaleConverter.convertToLatitude(0);
            final double secondLongitude = ScaleConverter.convertToLongitude(10);
            final double secondLatitude = ScaleConverter.convertToLatitude(10);
            final IGeoPosition firstPosition = new GeoPosition(firstLatitude, firstLongitude);
            final IGeoPosition secondPosition = new GeoPosition(secondLatitude, secondLongitude);
            
            final double distance = DevicesController.calculateDistance(firstPosition, secondPosition);
            System.out.println("First: (" + firstLongitude + ", " + firstLatitude + "), Second: (" + secondLongitude + ", " + secondLatitude + ")");
            System.out.println("Scale :" + scales[scale] + " | Distance: " + distance);
        }
    }
}