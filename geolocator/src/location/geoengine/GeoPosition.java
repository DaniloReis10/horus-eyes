package location.geoengine;

import java.util.Date;

import location.facade.IGeoPosition;

public class GeoPosition implements IGeoPosition{
	Date         date;
	double    latitude;
	double   longitude;

	public GeoPosition(Date date, double latitude,double longitude) {
		super();
		this.date      = date;
		this.latitude  = latitude;
		this.longitude = longitude;
	}

	public GeoPosition(IGeoPosition position) {
		super();
		this.date      = position.getDate();
		this.latitude  = position.getLatitude();
		this.longitude = position.getLongitude();
	}
	
	public GeoPosition(double latitude, double longitude){
		super();
		this.latitude  = latitude;
		this.longitude = longitude;
	}
	
	@Override
	public void setLatitude(double latitude) {
		this.latitude = latitude;

	}

	@Override
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public double getLatitude() {
		return latitude;
	}

	@Override
	public double getLongitude() {
		return longitude;
	}
	@Override
	public void setDate(Date date) {
		this.date=date;
	}

	@Override
	public Date getDate() {
		return date;
	}
	

}
