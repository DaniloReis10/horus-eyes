package location.listeners;

import location.facade.IMobileDevice;

/**
 * Esta classe implementa a a������o relacionada a uma mina quando um outro 
 * dispositivo se aproxima
 * @author Rafael
 *
 */
public class ProximityBarricadaListener extends ProximityListener{

	@Override
	public void action(IMobileDevice device1, IMobileDevice device2,double distance) {
		 this.device1 = device1;
		 this.device2 = device2;
/*		 
		 //A barricada serve para os dois times
		 if ((distance > Barricada.COLISION_DISTANCE)&&(distance < Barricada.VIEW_DISTANCE)){
			 actionVisibleArea(device2);
		 }
		 else{
			 if (distance <Barricada.COLISION_DISTANCE){
				 actionCollisionArea(device2);
			 }
		 }*/
	}
	/**
	 * 
	 * @param device
	 */
	private void actionVisibleArea(IMobileDevice device){
		 // Dar o tratamento para cada tipo de objeto do time inimigo
		 switch(device.getType()){
/*		    // Outro Jogador
		 case Player.SOLDIER:
			 msg = "Barricada sendo avistada por um soldado\n";
			 break;
		 case Player.DOCTOR:
			 msg = "Barricada sendo avistada por um curandeiro\n";
			 break;
		 case Player.ENGINEER:
			 msg = "Barricada sendo avistada por um pedreiro\n";
			 break;
		 case Player.SPY:
			 msg = "Barricada sendo avistada por um dedo duro\n";
			 break;
		 case Mina.MINA:
			 msg = "Barricada bem pertim de outra mina\n";
			 break;
		 case Barricada.BARRICADA:
			 msg = "Barricada bem pertim de uma barricada\n";
			 break;*/
		 default: 
			// msg = "Barricada bem pertim de um Objeto n�o identificado\n";
			 break;		 
		 }
		 
		//Adiciona a mensagem ao gameStory
		 //logMessage(msg);
	}
	/**
	 * 
	 * @param device
	 */
	private void actionCollisionArea(IMobileDevice device){
		 // Dar o tratamento para cada tipo de device do time inimigo
/*		 switch(device.getType()){
		 // Outro device
		 case Player.SOLDIER:
			 msg = "Barricada encostando em um soldado\n";
			 break;
		 case Player.DOCTOR:
			 msg = "Barricada encostando em um curandeiro\n";
			 break;
		 case Player.ENGINEER:
			 msg = "Barricada encostando em um pedreiro\n";
			 break;
		 case Player.SPY:
			 msg = "Barricada encostando em um dedo duro\n";
			 break;
		 case Mina.MINA:
			 msg = "Barricada encostando em uma mina\n";
			 break;
		 case Barricada.BARRICADA:
			 msg = "Barricada colocada em riba de outra barricada\n";
			 break;
		 default: 
			 msg = "Barricada encostando em um objeto n�o identificado\n";
			 break;		  
		 }
		 
		 //Adiciona a mensagem ao gameStory
		 logMessage(msg);*/
	}
}
