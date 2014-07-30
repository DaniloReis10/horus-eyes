package br.com.fujitec.location.facade;


public interface IVisibilityListener {
	public boolean isVisible(IMobileDevice device1,IMobileDevice device2,double distance);
}
