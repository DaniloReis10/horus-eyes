package simulagent;

import java.util.ArrayList;
import java.util.Iterator;

import location.facade.IGeoPosition;
import location.geoengine.GeoPosition;

import trilaceration.Point;
import trilaceration.ScaleConverter;

/**
 * Classe responsável pelo armazenamento das posicoes que o agente percorreu em um dia . Considera-se o intervalo de analise de 1 minuto.
 * @author DaniloReis
 *
 */
public class AgentPath {

	ArrayList<PositionPath> dataPath;// Array com as posicoes do agente no dia
	/**
	 * 
	 */
	public AgentPath() {
		super();
		dataPath = new ArrayList<PositionPath>();
	}
	/**
	 * 
	 * @param array
	 */
	public AgentPath(ArrayList<PositionPath> array){
		super();
		dataPath = array;
	}
	/**
	 * Adiciona uma nova posicao na lista de posicoes
	 * @param p
	 */
	public void addPoint(PositionPath p){
		if( p != null)
		   dataPath.add(p);
	}
	/**
	 * Limpa a lista de posicoes
	 */
	public void clear(){
		dataPath.clear();
	}
	/**
	 * Retorna o iterator da lista de posicoes
	 * @return
	 */
	public Iterator<PositionPath> iterator(){
		return dataPath.iterator();
	}
	/**
	 * Pega a posicao do agente no tempo time
	 * @param time tempo decorrido em minutos dentro de um dia desde da 0:00
	 * Por exemplo 1:15 = 1x60 + 15 = 75
	 * @return a interface da posicao no momento
	 */
	private PositionPath getStaticPosition(int time){
		Iterator<PositionPath> iterator;
		PositionPath           position;
		
		iterator = dataPath.iterator();
		while(iterator.hasNext()){
			position = iterator.next();
			// encontrou um local que o ponto estava parado
			if( (position.getArriveTime() <= time)&&(position.getLeaveTime() >= time)){
				return position;
			}
		}
		// o ponto deve estar em movimento
		return null;
	}
	/**
	 * 
	 * @param time
	 * @return
	 */
	private IGeoPosition getEstimatedPosition(int time){
		PositionPath         posiPrev;
		PositionPath         posiNext;
		int                       i,n;
		int               xc,yc,x0,y0;
		int             deltax,deltay;
		float                tpercent;
		GeoPosition               pos;

		// neste caso devemos encontra entre quais pontos da rota o agente deverá estar
		n = dataPath.size();
		if ( n < 2){
			return null;
		}
		// Calcula a posicao intermediaria
		for( i = 0; i < n - 1; i++){
			// Pega duas posicoes sequenciais na rota do agente
			posiPrev = dataPath.get(i);
			//posiPrev.print();
			posiNext = dataPath.get(i + 1);
			//posiNext.print();
			// verifica se no tempo especificado a agente esta entre estes pontos
			if( (posiPrev.getLeaveTime() <= time)&&(posiNext.getArriveTime() >= time)){
				deltax = (ScaleConverter.convertToX(posiNext)-ScaleConverter.convertToX(posiPrev)); 
				deltay = (ScaleConverter.convertToY(posiNext)-ScaleConverter.convertToY(posiPrev));  
				x0 = ScaleConverter.convertToX(posiPrev);
				y0 = ScaleConverter.convertToY(posiPrev);
				// calcula a posicao corrente
				tpercent = (float)(time - posiPrev.getLeaveTime())/(float)(posiNext.getArriveTime() -posiPrev.getLeaveTime());
				xc = x0 + (int)(deltax*tpercent);
				yc = y0 + (int)(deltay*tpercent);
				// Cria a nova posicao
				pos = new GeoPosition(ScaleConverter.convertToLatitude(xc),ScaleConverter.convertToLongitude(yc));
				
				return pos;
			}
		}
		return null;
		
	}
	/**
	 * 
	 * @param time
	 * @return
	 */
	public IGeoPosition getPositionAtTime(int time){
		PositionPath         position;
		
		position = getStaticPosition(time);
		// caso o no tempo especificado o agente nao esteja parado calcula a posicao estimada senao volta a posicao estatica 
		return (position == null)?getEstimatedPosition(time):position;
	}
	/**
	 * 
	 * @return
	 */
	public ArrayList<PositionPath> getDataPath() {
		return dataPath;
	}
	/**
	 * 
	 * @param dataPath
	 */
	public void setDataPath(ArrayList<PositionPath> dataPath) {
		this.dataPath = dataPath;
	}	
	
}
