package br.com.fujitec.location.facade;
/**
 * Interface para definir a a�ao que deve ser tomada quando
 * @author Danilo Reis
 *
 */
public interface IProximityListener {
	/**
	 * Metodo que � chamado para executar uma acao com dispositivos vizinhos
	 * @param device1 dispositivo central
	 * @param device2 dispositivo vizinho
	 * @param distance distancia em metros entre os dois dispositivos
	 */
	void  action(IMobileDevice device1,IMobileDevice device2,double distance);
};
