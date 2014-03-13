package simulagent;

import java.util.Date;

import location.facade.IGeoPosition;
import location.geoengine.GeoPosition;


public class PositionPath extends GeoPosition {

	private int   stopTime;
	private int arriveTime;
	private int  leaveTime;

	public PositionPath(IGeoPosition position, int arriveTime,int stopTime) {
		super(position);
		this.stopTime   = stopTime;
		this.arriveTime = arriveTime;
	}

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
	public void print(){
		System.out.printf("Latitude     = %5.2f\n", this.getLatitude());
		System.out.printf("Longitude    = %5.2f\n", this.getLongitude());
		System.out.printf("Tempo Chegada = %d\n", this.arriveTime);
		System.out.printf("Tempo parado  = %d\n", this.stopTime);
		System.out.printf("Tempo Saida   = %d\n", this.leaveTime);
		System.out.printf("\n");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
