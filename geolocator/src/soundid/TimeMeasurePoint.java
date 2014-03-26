package soundid;

import java.util.ArrayList;
import java.util.Iterator;

public class TimeMeasurePoint {
	private ArrayList<TimeMeasure>  timeMeasures;
	
	public TimeMeasurePoint() {
		timeMeasures= new ArrayList<TimeMeasure>();
		
	}
	/**
	 * acrescenta uma medida na lista de medidas de tempo
	 * @param measure
	 */
	public void addMeasure(TimeMeasure measure){
		if(measure != null){
			timeMeasures.add(measure);
		}
	}
	/**
	 * Limpa a lista de medidas
	 */
	public void clearMeasures(){
		
	}
	/**
	 * Retorna o iterator da lista de medidas
	 * @return
	 */
	public Iterator<TimeMeasure> getIterator(){
		return timeMeasures.iterator();
	}

}
