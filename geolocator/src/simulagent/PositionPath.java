package simulagent;

import location.facade.IGeoPosition;
import location.geoengine.GeoPosition;

/**
 * Classe que armazena dos dados da parada em uma rota de um agente
 * @author DaniloReis
 *
 */
public class PositionPath extends GeoPosition {

	private int   stopTime;//Tempo da parada em minutos
	private int arriveTime;// Tempo da chegada no ponto em minutos desde 0:00
	private int  leaveTime;// Tempo da partida no ponto em minutos desde 0:00

	/**
	 * contrutor com campos
	 * @param position Coordenadas do ponto
	 * @param arriveTime tempo de chegada em minutos
	 * @param stopTime tempo de partida em minutos
	 */
	public PositionPath(IGeoPosition position, int arriveTime,int stopTime) {
		super(position);
		this.stopTime   = stopTime;
		this.arriveTime = arriveTime;
	}

	// Set e get dos atributos
	public int getArriveTime() {
		return arriveTime;
	}
	
	public void setArriveTime(int arriveTime) {
		this.arriveTime = arriveTime;
	}
	public int getStopTime() {
		return stopTime;
	}

	public void setStopTime(int stopTime) {
		this.stopTime = stopTime;
	}

	public int getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(int leaveDate) {
		this.leaveTime = leaveDate;
	}
	/**
	 * Metodo de impressao dos atributos da classe
	 */
	public void print(){
		System.out.printf("Latitude     = %f\n", this.getLatitude());
		System.out.printf("Longitude    = %f\n", this.getLongitude());
		System.out.printf("Tempo Chegada(s) = %d  (%d min %02d sec)\n", this.arriveTime,(this.arriveTime/60),(this.arriveTime%60));
		System.out.printf("Tempo parado(s)  = %d  (%d min %02d sec)\n", this.stopTime,(this.stopTime/60),(this.stopTime%60));
		System.out.printf("Tempo Saida(s0   = %d  (%d min %02d sec)\n", this.leaveTime,(this.leaveTime/60),(this.leaveTime%60));
		System.out.printf("\n");
	}

	
}
