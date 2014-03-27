package soundid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import trilaceration.ElectronicFence;

import location.facade.IMobileDevice;
import location.listeners.ProximityListener;

/**
 * Esta classe implementa a a������o relacionada a uma mina quando um outro 
 * dispositivo se aproxima
 * @author Rafael
 *
 */
public class ProximityTagListener extends ProximityListener{
	List<ElectronicFence>     fencesList;
    ArrayList<TimeMeasure>  detections;
	
	public ProximityTagListener() {
		super();
		detections = new ArrayList<TimeMeasure>();
	}
	

	@Override
	public void action(IMobileDevice device1, IMobileDevice device2,double distance) {
		 double    timeToDetect;
		 double     temperature;
		 BaseAgent         base;
		 TagAgent           tag;
		 
		 this.device1 = device1;
		 this.device2 = device2;
		 
		 // verifica se o device violou alguma cerca eletronica
		 if(isOutOfFences(device1)==false){
			 //Gera alarme de violação de cerca eletronica
		 }
		 // caso o dispositivo 2 seja uma base
		 // Verifica se o dispositivo estar dentro do alcance de detecção sonica se
		 // estiver adiciona na lista de tempos a deteccao da estacao
		 if((device1.getType()==SoundIdModel.TAG_TYPE)  && // tag
		    (device2.getType()==SoundIdModel.BASE_TYPE) && // Estacao base
			(distance <= SoundIdModel.DISTANCE_DETECTION)){// Dentro da distancia de detecção
			 temperature = 0;
			 timeToDetect = distance/SoundIdModel.getSoundSpeed(temperature);
		     base = (BaseAgent)device2;
		     tag  = (TagAgent)device1;
			 tag.setBaseDetectionTime(timeToDetect,base.getPulseTick());
		 }
	}
	/**
	 * 
	 * @param device
	 */
	private void actionVisibleArea(IMobileDevice device){
	}
	/**
	 * 
	 * @param device
	 */
	private void actionCollisionArea(IMobileDevice device){
	}
	/**
	 * 
	 * @param fences
	 */
	public void setFenceList(List<ElectronicFence> fences){
		// seta a lista das cercas eletronicas associadas
		fencesList = fences;
		
	}
	private boolean isOutOfFences(IMobileDevice device){
		Iterator<ElectronicFence> iterator;
		ElectronicFence              fence;
		
		iterator = fencesList.iterator();
		
		while(iterator.hasNext()){
			fence = iterator.next();
			// busca se esta fora de alguma cerca eletronica
			if(fence.isInsideFence(device.getGeoPosition())==false)
				return false;
		}
		return true;
		
	}
}
