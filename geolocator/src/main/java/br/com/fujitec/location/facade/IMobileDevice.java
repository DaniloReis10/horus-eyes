package br.com.fujitec.location.facade;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
//import org.w3c.dom.Node;


import br.com.fujitec.location.geoengine.DevicePath;

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
	public Integer                                          getId();
	/**
	 * Seta a interface da posi��o corrente
	 * @param position
	 */
	public void               setGeoPosition(IGeoPosition position);
	/**
	 * Retorna a interface da posi��o corrente
	 * @return
	 */
	public IGeoPosition                            getGeoPosition();
	/**
	 * Calcula a distancia entre o dispositivo e o dispositivo passado como parametro
	 * @param device dispositivo a ser  calculada a distancia
	 * @return distancia em metros do dispositivo
	 */
	public double                   getDistanceFrom(IMobileDevice device);
	/**
	 * Retorna o valor da ultima distancia calculada
	 * @return ultima distancia calculada em metros
	 */
	public double                                 getLastDistance();
	/**
	 * Retorna o tipo do dispositivo
	 * @return tipo do dispositivo
	 */
	public Integer                                        getType();
	/**
	 * Seta o tipo do dispositivo
	 * @param type tipo do dispositivo
	 */
	public void                               setType(Integer type);
	/**
	 * Seta distancia na qual limite para um dispositivo ser considerado sobre
	 * @param distancia em metros
	 */
	public void                           setDistanceOn(double dist);
	/**
	 * Seta o grupo que o dispositivo pertence
	 * @param group c�digo do grupo
	 */
	public void                             setGroup(Integer group);
	/**
	 * Retorna o grupo que o us�rio pertence
	 * @return c�digo do grupo
	 */
	public Integer                                       getGroup();
	/**
	 * Retorna a distancia limite para um dispositivo ser considerado sobre
	 * @return distancia em metros
	 */
	public double                                   getDistanceOn();
	/**
	 * Seta o objeto de controle da rota realizada  do dispositivo
	 * @param path
	 */
	public void                     setDevicePath(DevicePath path);
	/**
	 * Retorna o objeto de controle da rota realizada do dispositivo
	 * @return
	 */
	public DevicePath                              getDevicePath();
	/** 
	 * Seta o listener que � atuado quando o servidor de georeferenciamento
	 * detectar situa��o de proximidade
	 * @param listener
	 */
	public void  setProximityListener(IProximityListener listener);
	/**
	 * Retorna o listener que � atuado quando o servidor de georeferenciamento
	 * detectar situa��o de proximidade
	 * @return
	 */
	public IProximityListener               getProximityListener();
	/**
	 * Retorna o listener que � atuado quando o servidor de georeferenciamento
	 * detectar situa��o de proximidade
	 * @return
	 */
	public IVisibilityListener               getVisibilityListener();
	/**
	 * Converte as informa�oes do dispositivo em parametros XML
	 * @return
	 * @throws Exception 
	 */
	public String toXML() throws Exception;
	
	/**
	 * Converte as informa�oes do dispositivo em parametros XML
	 * @return
	 * @throws Exception 
	 */	
	public void toXML(Element devices, Document doc);
	
	public void fromXML(Element item);
	// public IMobileDevice clone();
	
	public void setId(Integer id);
	void startListener();
	void stopListener();
}
