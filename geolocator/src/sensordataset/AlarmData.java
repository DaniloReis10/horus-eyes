package sensordataset;

import java.util.Date;

public class AlarmData {
	private int    deviceId;
	private int  deviceType;
	private int   alarmType;
	private Date createDate;
	private Date actionDate;
	private int      status;
	
	public static final int PENDING = 0;
	public static final int HANDLED = 1;
	
	public AlarmData() {
		super();
		this.status = AlarmData.PENDING;
	}

	public AlarmData(int deviceId, int deviceType, int alarmType, Date date) {
		super();
		this.deviceId = deviceId;
		this.deviceType = deviceType;
		this.alarmType = alarmType;
		this.createDate = date;
		this.status = AlarmData.PENDING;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}	
	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	
	public int getDeviceType() {
		return deviceType;
	}
	
	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}
	
	public int getAlarmType() {
		return alarmType;
	}
	
	public void setAlarmType(int alarmType) {
		this.alarmType = alarmType;
	}
	
	public Date getDate() {
		return createDate;
	}
	
	public void setDate(Date date) {
		this.createDate = date;
	}
}
