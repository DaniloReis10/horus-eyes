package location.geoengine;

import java.util.Date;

import location.facade.IGeoMobilePosition;


public class GeoMobilePosition implements IGeoMobilePosition {
	Date         date;
	double       speed;
	double   direction;
	double    latitude;
	double   longitude;

	public GeoMobilePosition(Date date, double speed, double direction, double latitude,double longitude) {
		super();
		this.date      = date;
		this.speed     = speed;
		this.direction = direction;
		this.latitude  = latitude;
		this.longitude = longitude;
	}
	public GeoMobilePosition(IGeoMobilePosition position) {
		super();
		this.date      = position.getDate();
		this.speed     = position.getSpeed();
		this.direction = position.getDirection();
		this.latitude  = position.getLatitude();
		this.longitude = position.getLongitude();
	}
	
	
	public GeoMobilePosition(Date now, double latitude, double longitude) {
		super();
		this.date      = now;
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
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	@Override
	public void setDirection(double angle) {
		this.direction = angle;
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
	public double getSpeed() {
		return speed;
	}

	@Override
	public double getDirection() {
		return direction;
	}

	@Override
	public void setDate(Date date) {
		this.date=date;
	}

	@Override
	public Date getDate() {
		return date;
	}
	@Override
	public void startListener() {
	
	}
	@Override
	public void stopListener() {
	}

}
