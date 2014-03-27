package location.facade;



public interface IGeoMobilePosition extends IGeoPosition{
	
	public void     setSpeed(double speed);
	public void setDirection(double angle);
	public double               getSpeed();
	public double           getDirection();
	public abstract void   startListener();
	public abstract void    stopListener();
}
