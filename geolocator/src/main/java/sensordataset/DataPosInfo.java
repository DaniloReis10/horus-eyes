package sensordataset;

import trilaceration.Point;

public class DataPosInfo {
	private int            netId;
    private Point sensorPosition;
    private double         ratio;
    private int             time;
 
	public DataPosInfo(int netId, Point sensorPosition, double ratio, int time) {
		super();
		this.netId = netId;
		this.sensorPosition = sensorPosition;
		this.ratio = ratio;
		this.time = time;
	}
	public int getNetId() {
		return netId;
	}
	public void setNetId(int netId) {
		this.netId = netId;
	}
	public Point getSensorPosition() {
		return sensorPosition;
	}
	public void setSensorPosition(Point sensorPosition) {
		this.sensorPosition = sensorPosition;
	}
	public double getRatio() {
		return ratio;
	}
	public void setRatio(double ratio) {
		this.ratio = ratio;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}


}
