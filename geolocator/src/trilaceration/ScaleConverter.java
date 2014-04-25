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
    
}