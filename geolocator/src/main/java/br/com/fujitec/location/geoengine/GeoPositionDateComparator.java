package br.com.fujitec.location.geoengine;

import java.util.Comparator;
import java.util.Date;

import br.com.fujitec.location.facade.IGeoPosition;

public class GeoPositionDateComparator  implements Comparator<IGeoPosition>{
   
    public int compare(IGeoPosition dev1, IGeoPosition dev2){
    	Date date1 = ((IGeoPosition)dev1).getDate();        
        Date date2 = ((IGeoPosition)dev2).getDate();
       
        return date1.compareTo(date2);
    }

}
