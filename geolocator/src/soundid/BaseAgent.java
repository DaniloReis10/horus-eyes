package soundid;

import simulagent.RFAgent;

public class BaseAgent extends RFAgent {
	private int  pulseTick;
	private int periodTick;
	
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

}
