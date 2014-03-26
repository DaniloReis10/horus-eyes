package soundid;

import java.util.ArrayList;
import java.util.Iterator;

import simulagent.MobileAgent;

public class TagAgent extends MobileAgent {
	private float                         temperature;
	private ArrayList<TimeMeasurePoint>  timeMeasures;

	public TagAgent() {
		super();
		timeMeasures = new ArrayList<TimeMeasurePoint> ();
	}	
	
	/**
	 * Retorna a temperatura medida pela TAG
	 * @return
	 */
	public float getTemperature() {
		return temperature;
	}
    /**
     * Seta a temperatura medida pelo sensor
     * @param temperature
     */
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}
	/**
	 * Adiciona as medidas de um ponto de medida
	 * @param measure
	 */
	public void addTimeMeasurePoint(TimeMeasurePoint measure){
		if(measure != null){
			timeMeasures.add(measure);
		}
	}
	public Iterator<TimeMeasurePoint> getIteratorMeasures(){
		return timeMeasures.iterator();
	}

	
	
	
	

}
