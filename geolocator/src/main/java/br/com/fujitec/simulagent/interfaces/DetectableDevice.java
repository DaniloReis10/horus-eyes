package br.com.fujitec.simulagent.interfaces;

import br.com.fujitec.location.facade.IGeoPosition;

public interface DetectableDevice {
    
    /**
     * Retorna o identificador do dispositivo
     * 
     * @return
     */
    public abstract Integer getId();

    /**
     * Retorna a interface da posição corrente
     * 
     * @return
     */
    public abstract IGeoPosition getCurrentPosition();

    /**
     * Calcula a distancia entre o dispositivo e o dispositivo passado como
     * parametro
     * 
     * @param device
     *            dispositivo a ser calculada a distancia
     * @return distancia em metros do dispositivo
     */
    public abstract double getDistanceFrom(DetectableDevice device);
    
    public abstract void notifySensors();

    /**
     * <p>Compares the Device current position and previous position, checking whether the device has moved or not.</p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    public abstract boolean hasMoved();
    
    /**
     * <p>This method is called to verify the detected status of this device</p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return boolean
     */
    public abstract boolean wasDetected();
    
    /**
     * <p>This method is called when a Sensor detects this device</p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    public abstract void detect();
    
    /**
     * <p>This method is called to reset the detected status of this device</p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    public abstract void undetect();
}
