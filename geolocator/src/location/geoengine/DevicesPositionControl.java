package location.geoengine;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import location.facade.IGeoMobilePosition;
import location.facade.IGeoPosition;
import location.facade.IMobileDevice;

/**
 * Classe controladora do gerenciamento de posi��es
 * @author Danilo Reis
 *
 */
public class DevicesPositionControl {	
	private HashMap<Integer, IMobileDevice> map;	
	static int               freeDeviceId;
	static DevicesPositionControl manager;

	// Constantes
	public static final double EARTHRATIO = 6378160; // Raio da terra em metros

	
	static {
		freeDeviceId = 1;
	}

	static public synchronized int getNextFreeDeviceId() {
		return freeDeviceId++;
	}
	static public synchronized DevicesPositionControl getInstance(){
		if (manager == null)
		    manager = new DevicesPositionControl();
		return manager;
	}
	/**
	 *  Calcula a distancia em metros entre dois pontos dadas as coordenadas geograficas
	 *  destes pontos
	 * @param pos1 Interface das coordenadas geograficas do ponto 1
	 * @param pos2 Interface das coordenadas geograficas do ponto 2
	 * @return distancia em metros
	 */
	static public synchronized double calculateDistance(IGeoPosition pos1,IGeoPosition pos2){
		double dLat,dLon;
		double     a,c,d;
		double lat1,lat2;
		
		dLat = (pos2.getLatitude()-pos1.getLatitude());
		dLat = (dLat /90.00)* Math.PI/2 ;
		dLon = (pos2.getLongitude()-pos1.getLongitude());
		dLon = (dLon / 180.0) * Math.PI;
		lat1 = (pos1.getLatitude()/ 90.00)* Math.PI/2 ;
		lat2 = (pos2.getLatitude()/ 90.00)* Math.PI/2;

		a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
		c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		d =  DevicesPositionControl.EARTHRATIO * c;		
		return d;
	}
	
	public DevicesPositionControl() {
		super();
		map = new HashMap<Integer, IMobileDevice>();
	}
	/**
	 * Adiciona um novo dispositivo movel a ser rastreado
	 * @param device
	 * @return
	 */
	public synchronized boolean  addDevice(IMobileDevice device){
		// verifica se o dispositivo ja esta na tabela
		map.put(device.getId(), device);
		return true;
	}
	/**
	 * Remove um dispositivo movel a ser rastreado
	 * @param device
	 * @return
	 */
	public synchronized boolean  removeDevice(IMobileDevice device){
		// verifica se o dispositivo ja esta na tabela
		map.remove(device.getId());
		return true;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public synchronized IMobileDevice searchMobileDeviceById(Integer id){
		return map.get(id);
	}
	/**
	 * Atualiza a posicao de um um dispositivo movel 
	 * @param device
	 * @return
	 */
	public synchronized boolean  updateDevicePosition(Integer deviceId,IGeoMobilePosition position){
		//GeoMobilePosition pos;
		IMobileDevice  device;
		
		device = map.get(deviceId);
		if ( device != null){
			// coloca a atualiza a posicao corrente no dispositivo
			device.setGeoPosition(position);
/*			
			GameStoryControl storyControl = GameStoryControl.getInstance();
			Message message = new Message("Soldado atualizou a posi��o", device);
			storyControl.addMessage(message);
			
			//Reset no status de escondido do player
			if(device instanceof Player){			
				((Player)device).setEscondido(false);
			}*/
			// executa os Listener de proximidade
			performProximityListeners(device);
		}
		return true;
	}
	/**
	 * 
	 * @param device
	 */
	public synchronized void performProximityListeners(IMobileDevice device){
		Iterator<IMobileDevice>       iterator;
		IMobileDevice                      dev;
		ArrayList<IMobileDevice>          list;
		double                        distance;

		// cria uma lista com todos os dispositivos
		list      = new ArrayList<IMobileDevice>(map.values());
		iterator = list.iterator();
		device.startListener();
		// percorre a lista de dispositivos registrados
		while(iterator.hasNext()){
			dev      = iterator.next();
			// caso o dispositivo nao seja ele proprio
			if( !(dev.getId().equals(device.getId())) ){
				// calcula a distancia do dispositivo especificado
				distance = calculateDistance(device.getGeoPosition(), dev.getGeoPosition());
				// executa os listener dos objetos
				if (dev.getProximityListener() != null){
					dev.getProximityListener().action(dev, device, distance);
				}
				if (device.getProximityListener() != null){
					device.getProximityListener().action(device, dev, distance);
				}
			}
		}
		device.stopListener();
	}
	/**
	 * 
	 * @param device
	 * @return
	 */
	public  synchronized List<IMobileDevice> getVisibleDeviceList(IMobileDevice device,List<IMobileDevice> devices){
		Iterator<IMobileDevice>       iterator;
		IMobileDevice                      dev;
		ArrayList<IMobileDevice>          list;
		double                        distance;

		// cria uma lista com todos os dispositivos
		list      = new ArrayList<IMobileDevice>();
		iterator = devices.iterator();
		// percorre a lista de dispositivos registrados
		while(iterator.hasNext()){
			dev      = iterator.next();
			// caso o dispositivo nao seja ele proprio
			if( (dev != null) &&(!(dev.getId().equals(device.getId()))) ){
				// calcula a distancia do dispositivo especificado
				distance = calculateDistance(device.getGeoPosition(), dev.getGeoPosition());
				// executa os listener dos objetos
				if (dev.getVisibilityListener().isVisible(device, dev, distance)){
					list.add(dev);
				}
			}
		}
		return list;
	}
	/**
	 * Retorna uma lista de dispositivos moveis registrados
	 * @return
	 */
	public synchronized List <IMobileDevice> getDevices(){
		ArrayList<IMobileDevice>  list;

		list = new ArrayList<IMobileDevice>(map.values());
		return list;		
		
	}
	/**
	 * Retorna uma lista de dispositivos moveis registrados e pertencentes a um grupo
	 * @param groupId identificador do grupo
	 * @return lista dos dispositivos de um grupo
	 */
	public synchronized List <IMobileDevice> getDevicesByGroup(int groupId){
		List<IMobileDevice>          groupList;   
		Iterator<IMobileDevice>       iterator;
		IMobileDevice                      dev;
		ArrayList<IMobileDevice>          list;

		groupList = new ArrayList<IMobileDevice>();
		list      = new ArrayList<IMobileDevice>(map.values());
		iterator = list.iterator();
		// calcula a distancia do dispositivo especificado
		while(iterator.hasNext()){
			dev = iterator.next();
			// se pertence ao grupo a ser buscado adiciona na lista
			if( dev.getGroup().intValue() == groupId){
				groupList.add(dev);
			}
		}
		return groupList;		
	}
	/**
	 * 
	 * @param device
	 * @return
	 */
	public  synchronized List<IMobileDevice> getDevicesSortedByDistance(IMobileDevice device){
		List<IMobileDevice>       distanceList;   
		Iterator<IMobileDevice>       iterator;
		IMobileDevice                      dev;
		
		distanceList  = getDevices();
		iterator = distanceList.iterator();
		// calcula a distancia do dispositivo especificado
		while(iterator.hasNext()){
			dev = iterator.next();
			dev.getDistanceFrom(device);
		}
		// Ordena a lista pela distancia ao dispositivo
		Collections.sort(distanceList,new DeviceDistanceComparator()); 		
		return distanceList;
	}
	/**
	 * Fun�ao solicita do googlemaps uma imagem com o centro da imagem localizado nas
	 * latitude e longitude especificados. A imagem gerada coloca uma icone no local das
	 * coordenadas o programador pode determinar o n�vel de zoom , a icone que ser� mostrada
	 * e um path. Estes dois ultimos parametros podem serem deixados em branco quando n�o se 
	 * desejar mostrar a path e que a icone do marcador seja a default do google maps.  
	 * @param latitude - latitude do ponto a ser mostrado na imagem
	 * @param longitude - longitude do ponto a ser mostrado na imagem 
	 * @param imageName - Nome do arquivo de imagem que ser� mostrado
	 * @param zoom - N�vel de zoom entre 0 (o mais baixo, no qual todo o mundo pode ser visto 
	 *        em um s� mapa) e 21+ (chega at� constru��es individuais) s�o poss�veis na 
	 *        visualiza��o padr�o dos mapas.
	 * @param custonIconUrl -
	 * @param path
	 * @throws IOException 
	 */
	public  synchronized void getGoogleMapMap(String latitude,String longitude,String imageName,String zoom,String custonIconUrl,DevicePath path ) throws IOException{
		String urlName;
		urlName = "http://maps.google.com/maps/api/staticmap?center="+latitude+","+longitude+"&zoom="+zoom+"&size=400x400&format=jpg-baseline&sensor=true&maptype=roadmap&mobile=true";
		if( (custonIconUrl==null)||(custonIconUrl.equals(""))){
			urlName += "&markers=color:blue|label:S|"+latitude+","+longitude;
		}
		else{
			urlName+="&markers=icon:"+custonIconUrl+"|"+latitude+","+longitude;
		}
		if( custonIconUrl!=null){
			urlName += path.getGoogleMapsString();
		}
		saveImageFromUrl(urlName,imageName);
		
	}
	/**
	 * 
	 * @param imageUrl
	 * @param destinationFile
	 * @throws IOException
	 */
	public  synchronized void saveImageFromUrl(String imageUrl, String destinationFile) throws IOException {
		URL         url;
		InputStream  is;
		OutputStream os;
		byte[]        b;
		int      length;
		
		url = new URL(imageUrl);
		is = url.openStream();
		os = new FileOutputStream(destinationFile);

		b = new byte[4096];

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	}	
	
    public static void main(String args[]) {
    	DevicesPositionControl control;
    	DevicePath                path;
    	GeoMobilePosition                pos;
    	
    	control = DevicesPositionControl.getInstance();
    	try {
    		path = new DevicePath();
    		path.setColor(DevicePath.BLUE);
    		path.setWeight("8");
    		pos = new GeoMobilePosition(new Date(), 0, 0,(float)(40.755423),(float)(-73.982397));
    		path.addPosition(pos);
    		pos = new GeoMobilePosition(new Date(), 0, 0,(float)(40.755523),(float)(-73.984397));
    		path.addPosition(pos);
    		pos = new GeoMobilePosition(new Date(), 0, 0,(float)(40.755623),(float)(-73.984397));
    		path.addPosition(pos);
    		pos = new GeoMobilePosition(new Date(), 0, 0,(float)(40.755723),(float)(-73.985397));
    		path.addPosition(pos);
    		pos = new GeoMobilePosition(new Date(), 0, 0,(float)(40.755823),(float)(-73.986397));
    		path.addPosition(pos);
    		
			control.getGoogleMapMap("40.755823","-73.986397","image2.jpg","15","http://tinyurl.com/d7pedea",path);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }



}
