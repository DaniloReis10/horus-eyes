package soundid;

import java.util.Date;

public class BaseDetection {
	private int        baseId;
    private int  detectedTick;
    private Date     dateTime;
	
	public BaseDetection() {
	   this.baseId = 0;
	   this.detectedTick = 0;
	   this.dateTime = new Date();
	}
	public int getBaseId() {
		return baseId;
	}

	public void setBaseId(int baseId) {
		this.baseId = baseId;
	}

	public int getDetectedTick() {
		return detectedTick;
	}

	public void setDetectedTick(int detectedTick) {
		this.detectedTick = detectedTick;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	

}
