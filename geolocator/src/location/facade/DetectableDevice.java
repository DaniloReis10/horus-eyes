package location.facade;

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
}
