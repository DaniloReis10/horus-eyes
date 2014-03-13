package location.geoengine;

import java.util.Date;
//import java.util.List;
//import location.facade.IMobileDevice;

public class GeoPositionControlTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String []               params;
		//String                    resp;
		DevicesPositionControl control;
		GeoMobilePosition           position;
		double                latitude;
		double               longitude;
		
		params = new String [10];
		
		params[0] ="Danilo";
		params[1] ="1";
		params[2] ="1";
		params[3] ="-3.733137";
		params[4] ="-38.497939";
		
        //Integer myId = new Integer(1);
        
        
		params[0] ="Fabio";
		params[1] ="1";
		params[2] ="1";
        params[3] ="-3.733286";
        params[4] ="-38.497295";


		params[0] ="Viktor";
		params[1] ="1";
		params[2] ="2";
        params[3] ="-3.733415";
        params[4] ="-38.497811";

        
        latitude  = Double.parseDouble("-3.733415");
        longitude = Double.parseDouble("-38.497810");
        Date now  = new Date();
        position  = new GeoMobilePosition(now, latitude, longitude);
        control   = DevicesPositionControl.getInstance();
        control.updateDevicePosition(new Integer(1), position);
        
        //List<IMobileDevice> list = control.getDevicesByGroup(1);
        
        // Busca todos inimigos
        //List<IMobileDevice> inimys = control.getDevicesByGroup(2);
        // busca o dispositivo pelo Id
        //IMobileDevice device= control.searchMobileDeviceById(myId);
        // volta a lista dos inimigos visiveis
        //list = control.getVisibleDeviceList(device, inimys);
        
        

	}

}
