package location.facade;

import java.util.List;

import location.geoengine.DevicePath;

/**
 * Interface do controlador de georeferenciamento
 * @author Danilo Reis
 *
 */
public interface IGeoPositionControl{

	/**
	 * Registra um dispositivo no controlador de georeferenciamento
	 * @param device
	 * @return true registro feito com sucesso
	 * @return false - falha na atualiza��o
	 */
	public boolean             addMobileDeviceToTrack     (IMobileDevice device);
	/**
	 * Atualiza a posi��o de um dispositivo movel
	 * @param device dispositivo a ser atualizado
	 * @param position interface da posi��o corrente
	 * @return true - atualizacao feita com sucesso
	 * @return false - falha na atualiza��o
	 */
	public boolean             updateMobileDevicePosition (IMobileDevice device,IGeoPosition position);
	/**
	 * Busca o dispositivo pelo identificador
	 * @param id identificador
	 * @return Ponteiro para a interface do dispositivo
	 */
	public IMobileDevice       findMobileDeviceById       (int id);
	/**
	 * Retorna a path do dispositivo
	 * @param device dispositivo movel
	 * @return Ponteiro para objeto gerenciador de path
	 */
	public DevicePath          getMobileDeviceTrack       (IMobileDevice device);
	/**
	 * Retorna a imagem do mapa do dispositivo movel
	 * @param device interface do dispositivo
	 * @return Imagem do dispositivo
	 */
	public Object              getMobileDeviceMap         (IMobileDevice device);
	/**
	 * Retorna os dispositivos em uma �rea retangular tendo o dispositivo m�vel
	 * no centro do retangulo
	 * @param device Interface do dispositivo m�vel
	 * @param width largura do retangulo (m)
	 * @param height altura do dispositivo (m)
	 * @return Lista dos dispositivos na �rea
	 */
	public List<IMobileDevice> getMobileDevicesInArea     (IMobileDevice device,double width,double height);
	/**
	 * Retorna os dispositivos em uma �rea circular tendo o dispositivo m�vel
	 * no centro do circulo
	 * @param device Interface do dispositivo m�vel
	 * @param ratio raio do circulo em torno do dispositivo
	 * @return Lista de dispositivos dentro do circulo
	 */
	public List<IMobileDevice> getMobileDevicesAround     (IMobileDevice device,double ratio);
	/**
	 * Retorna o dispositivo movel mais proximo
	 * @param device Interface do dispositivo m�vel
	 * @return Interface do dispositivo m�vel mais proximo
	 */
	public IMobileDevice       getClosestMobileDevice     (IMobileDevice device);
	/**
	 * Retorna todos os dispositivos de um grupo
	 * @param groupId identificador do grupo
	 * @return Lista de dispositivos moveis de um mesmo grupo
	 */
	public List<IMobileDevice> getMobileDevicesByGroup    (int groupId);
}
