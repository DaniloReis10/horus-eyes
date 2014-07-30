package br.com.fujitec.location.facade;

import java.util.Date;

public interface IGeoPosition {
	
	public void setLatitude(double latitude);
	public void setLongitude(double longitude);	
	public double getLatitude();
	public double getLongitude();
	public void setDate(Date date);
	public Date getDate();
}
