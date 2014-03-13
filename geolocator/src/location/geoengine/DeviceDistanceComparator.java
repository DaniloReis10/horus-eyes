package location.geoengine;

import java.util.Comparator;

import location.facade.IMobileDevice;


public class DeviceDistanceComparator  implements Comparator<Object>{
   
    public int compare(Object dev1, Object dev2){
    	double distance1 = ((IMobileDevice)dev1).getLastDistance();        
        double distance2 = ((IMobileDevice)dev2).getLastDistance();
       
        if(distance1 > distance2)
            return 1;
        else if(distance1 < distance2)
            return -1;
        else
            return 0;        	
    }

}
