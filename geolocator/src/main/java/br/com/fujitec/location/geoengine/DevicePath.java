package br.com.fujitec.location.geoengine;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import br.com.fujitec.location.facade.IGeoPosition;

public class DevicePath {

	ArrayList<IGeoPosition>  positions;
	String                       color;
	String                      weight;
	
	public static final String BLACK  = "black"; 
	public static final String BROWN  = "brown";
	public static final String GREEN  = "green";
	public static final String PURPLE = "purple";
	public static final String YELLOW = "yellow";
	public static final String BLUE   = "blue";
	public static final String GRAY   = "gray";
	public static final String ORANGE = "orange";
	public static final String RED    = "red";
	public static final String WHITE  = "white";
	
	public DevicePath() {
		super();
		positions = new ArrayList<IGeoPosition>();
	}
	
	public void addPosition(IGeoPosition data){
		positions.add(data);
	}
	public String getGoogleMapsString(){
		Iterator<IGeoPosition> iterator;
		IGeoPosition           position;
		StringBuffer               path;
		
		path = new StringBuffer();
		path.append("&path=color:"+getColor()+"|weight:"+getWeight());
		iterator = positions.iterator();
		while(iterator.hasNext()){
			position = iterator.next();
			path.append("|"+String.valueOf(position.getLatitude())+","+String.valueOf(position.getLongitude()));
		}
		return path.toString();
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
 
	public String getWeight() {
		return weight;
	}
    /**
     * Seta a espessura em pixel da linha de path
     * @param weight
     */
	public void setWeight(String weight) {
		this.weight = weight;
	}
	/** 
	 * Retorna a ultima posicao informada da path
	 * @return
	 */
	public IGeoPosition getLastPosition(){
		int index;
		index = positions.size() - 1;
		return positions.get(index);
	}
	/**
	 * 
	 * @return
	 */
	public double getTotalDistance(){
		return getDistanceBetweenIndex(0,(positions.size() - 1));
	}
	/**
	 * Calcula a distancia entre dois pontos na path considerando a rota percorrida entre 
	 * estes dois pontos
	 * @param index0 indice do ponto inicial na rota
	 * @param index1 inidice do ponto final na rota
	 * @return distancia em metros
	 */
	public double getDistanceBetweenIndex(int index0,int index1){
		int                  i;
		double               d;
		IGeoPosition pos1,pos2;
		
		// Verifica casos limites 
		if ( (index0 < 0)||(index1 < 0)||
			 (index0 >= positions.size())||
			 (index1 >= positions.size())||
			 (index1 < index0)){
			return -1;
		}
		d = 0;
		// Calcula a distancia entre os pontos 
		for(i = index0; i <= index1 - 1; i++ ){
			pos1 = (IGeoPosition)positions.get(i);
			pos2 = (IGeoPosition)positions.get(i+1);
			d += DevicesController.calculateDistance(pos1, pos2);
		}
		return d;
	}
	/**
	 * Retorna a velocidade m�dia entre dois pontos na rota
	 * @param index0 indice do ponto inicial na rota
	 * @param index1 inidice do ponto final na rota
	 * @return velocidade m�dia em metros/segundos (m/s)
	 */
	public double getAverageSpeedBetweenIndex(int index0,int index1){
		double         d,speed;
		long     milliseconds1;
		long     milliseconds2;
		long              diff;
		long       diffSeconds;
		IGeoPosition pos1,pos2;
		Calendar     calendar1;
		Calendar     calendar2;
		
		// Verifica casos limites 
		if ( (index0 < 0)||(index1 < 0)||
			 (index0 >= positions.size())||
			 (index1 >= positions.size())||
			 (index1 < index0)){
			return -1;
		}
		// Pega os  pontos
		pos1 = (IGeoPosition)positions.get(index0);
		pos2 = (IGeoPosition)positions.get(index1);
		// Calcula a distancia percorrida
		d = getDistanceBetweenIndex(index0, index1);
		// calcula a diferen�a de tempo entre elas
		calendar1 = Calendar.getInstance();
		calendar1.setTime(pos1.getDate());
		calendar2 = Calendar.getInstance();
		calendar2.setTime(pos2.getDate());
		// Calcula o timestamp em milisegundos
		milliseconds1 = calendar1.getTimeInMillis();
		milliseconds2 = calendar2.getTimeInMillis();
		// calcula a diferen�a
		diff = milliseconds2 - milliseconds1;
		// converte para segundos
		diffSeconds = diff / 1000;		
		// calcula a velocidade m�dia
		speed = d / diffSeconds;
		
		return speed;
	}
	

}
