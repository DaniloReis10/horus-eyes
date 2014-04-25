package soundid;

import java.util.ArrayList;
import java.util.Iterator;

public class TagAgent extends MobileAgent {
	private float                         temperature;
	private ArrayList<TimeMeasurePoint>  timeMeasures;
    private TimeMeasurePoint         timeMeasurePoint;
    private int                             measureId;
	
	public TagAgent(Integer agentID) {
	    super(agentID);
		this.timeMeasures = new ArrayList<TimeMeasurePoint> ();
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
	/**
	 * Seta o tempo de deteccao de uma base
	 * @param timeToDetect
	 */
	public void setBaseDetectionTime(double timeToDetect,int tick){
		TimeMeasure  measure;
		
		measure = new TimeMeasure(measureId,timeToDetect,tick);
		timeMeasurePoint.addMeasure(measure);
		measureId++;
		
	}
	@Override
	public void startListener() {
	    timeMeasurePoint = new TimeMeasurePoint();
	    measureId = 1;
	}
	@Override
	public void stopListener() {
		timeMeasures.add(timeMeasurePoint);
	}


	
	
	
	

}
