package location.listeners.app;

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

	@Override
	public void action(IMobileDevice device1, IMobileDevice device2,double distance) {
		 this.device1 = device1;
		 this.device2 = device2;
		 
		 // verifica se o device violou alguma cerca eletronica
		 if(isOutOfFences(device1)==false){
			 // TODO gera alarme de violação de cerca eletronica
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
