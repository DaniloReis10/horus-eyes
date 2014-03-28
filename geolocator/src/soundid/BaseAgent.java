package soundid;

import simulagent.RFAgent;

public class BaseAgent extends RFAgent {

	private int      pulseTick;
	private int     periodTick;
	private double temperature;
	
	public int getPulseTick() {
		return pulseTick;
	}
	public void setPulseTick(int pulseTick) {
		this.pulseTick = pulseTick;
	}
	public int getPeriodTick() {
		return periodTick;
	}
	public void setPeriodTick(int periodTick) {
		this.periodTick = periodTick;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
}
