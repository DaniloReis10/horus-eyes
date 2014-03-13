package location.facade;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
//import org.w3c.dom.Node;

import location.geoengine.DevicePath;

/**
 * Interface que define as informa��es de um dispositivo movel ( jogado, mina, muni��o etc.)
 * @author Danilo Reis
 *
 */
public interface IMobileDevice {
	/**
	 * Retorna o identificador do dispositivo
	 * @return
	 */
	Integer                                          getId();
	/**
	 * Seta a interface da posi��o corrente
	 * @param position
	 */
	void               setGeoPosition(IGeoPosition position);
	/**
	 * Retorna a interface da posi��o corrente
	 * @return
	 */
	IGeoPosition                            getGeoPosition();
	/**
	 * Calcula a distancia entre o dispositivo e o dispositivo passado como parametro
	 * @param device dispositivo a ser  calculada a distancia
	 * @return distancia em metros do dispositivo
	 */
	double                   getDistanceFrom(IMobileDevice device);
	/**
	 * Retorna o valor da ultima distancia calculada
	 * @return ultima distancia calculada em metros
	 */
	double                                 getLastDistance();
	/**
	 * Retorna o tipo do dispositivo
	 * @return tipo do dispositivo
	 */
	Integer                                        getType();
	/**
	 * Seta o tipo do dispositivo
	 * @param type tipo do dispositivo
	 */
	void                               setType(Integer type);
	/**
	 * Seta distancia na qual limite para um dispositivo ser considerado sobre
	 * @param distancia em metros
	 */
	void                           setDistanceOn(double dist);
	/**
	 * Seta o grupo que o dispositivo pertence
	 * @param group c�digo do grupo
	 */
	void                             setGroup(Integer group);
	/**
	 * Retorna o grupo que o us�rio pertence
	 * @return c�digo do grupo
	 */
	Integer                                       getGroup();
	/**
	 * Retorna a distancia limite para um dispositivo ser considerado sobre
	 * @return distancia em metros
	 */
	double                                   getDistanceOn();
	/**
	 * Seta o objeto de controle da rota realizada  do dispositivo
	 * @param path
	 */
	void                     setDevicePath(DevicePath path);
	/**
	 * Retorna o objeto de controle da rota realizada do dispositivo
	 * @return
	 */
	DevicePath                              getDevicePath();
	/** 
	 * Seta o listener que � atuado quando o servidor de georeferenciamento
	 * detectar situa��o de proximidade
	 * @param listener
	 */
	void  setProximityListener(IProximityListener listener);
	/**
	 * Retorna o listener que � atuado quando o servidor de georeferenciamento
	 * detectar situa��o de proximidade
	 * @return
	 */
	IProximityListener               getProximityListener();
	/**
	 * Retorna o listener que � atuado quando o servidor de georeferenciamento
	 * detectar situa��o de proximidade
	 * @return
	 */
	IVisibilityListener               getVisibilityListener();
	/**
	 * Converte as informa�oes do dispositivo em parametros XML
	 * @return
	 * @throws Exception 
	 */
	String toXML() throws Exception;
	
	/**
	 * Converte as informa�oes do dispositivo em parametros XML
	 * @return
	 * @throws Exception 
	 */	
	void toXML(Element devices, Document doc);
	
	void fromXML(Element item);
	IMobileDevice clone();
	
	void setId(Integer id);
}
