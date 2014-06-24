package location.facade;

import java.util.List;

import location.geoengine.DevicePath;
import simulagent.Device;

/**
 * Interface do controlador de georeferenciamento
 * 
 * @author Danilo Reis
 * 
 */
public interface IGeoPositionControl {

    /**
     * Registra vários dispositivos no controlador de georeferenciamento
     * 
     * @param device
     * @return true registro feito com sucesso
     * @return false - falha na atualiza��o
     */
    public void addDevicesToTrack(List<Device> devices);
    
    /**
     * Registra um dispositivo no controlador de georeferenciamento
     * 
     * @param device
     * @return true registro feito com sucesso
     * @return false - falha na atualiza��o
     */
    public void addDeviceToTrack(Device device);
    
    /**
     * Remove vários dispositivos do controlador de georeferenciamento
     * 
     * @param device
     * @return true registro feito com sucesso
     * @return false - falha na atualiza��o
     */
    public void removeDevicesFromTracking();
    
    /**
     * Busca o dispositivo pelo identificador
     * 
     * @param id
     *            identificador
     * @return Ponteiro para a interface do dispositivo
     */
    public Device findDeviceById(int id);

    /**
     * Retorna a path do dispositivo
     * 
     * @param device
     *            dispositivo movel
     * @return Ponteiro para objeto gerenciador de path
     */
    public DevicePath getDevicePath(Device device);

    /**
     * Retorna os dispositivos em uma �rea circular tendo o dispositivo m�vel no
     * centro do circulo
     * 
     * @param device
     *            Interface do dispositivo m�vel
     * @param ratio
     *            raio do circulo em torno do dispositivo
     * @return Lista de dispositivos dentro do circulo
     */
    public List<DetectableDevice> getDevicesAround(Device device, int radius);
}
