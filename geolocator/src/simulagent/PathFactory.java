package simulagent;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import location.facade.IGeoPosition;
import location.geoengine.GeoPosition;

import trilaceration.ScaleConverter;

/**
 * Fabrica de rotas diária para os agentes móveis
 * @author DaniloReis
 *
 */
public class PathFactory {
	
    public static final int TICKS_DAY = 24 * 60;
    public static final int MAX_TRIP_TIME = 60;
    public static final int MIN_TRIP_TIME = 5;
    
	public PathFactory() {
		super();
	}
    /**
    * Construtor da classe
    * @param width largura da imagem
    * @param height altura da imagem
    * @param n numero de paradas na rota do agente dentro do dia
    * @return rota do dia
    */
	public AgentPath create(int width,int height,int n){
		ArrayList<PositionPath>     points;
		int i,dayTime,timePoint,timeWindow;
		double          latitude,longitude;
		int                      posX,posY;
		int                      startTime;
		int                       timeTrip;
		PositionPath                 point;
		GeoPosition               geoPoint;
		
		// Cria o array para armazenar as posicoes
		points = new ArrayList<PositionPath>();
		// define o numero de minutos do dia.
		dayTime    = TICKS_DAY;
		// define a largura media do tempo de parada
		timeWindow = dayTime / n;
		// seta a hora de cehgada inicial como 0:00 ou 0 minuto
		startTime  = 0;
		// Faz loop para gerar n pontos de parada preenchendo de forma aleatoria o tempo 
		// de chagada, saida e tempo para se locomover para a proxima parada
		for( i = 0; i < n; i++){
			// Gera o tempo de parada
			timePoint = (int) (timeWindow * Math.random());
			// gera de forma aleatoria a posicao dentro da imagem
			posX = (int) (width * Math.random());
			posY = (int) (height * Math.random());
			// converte pela escala para latitude e longitude
			latitude = ScaleConverter.convertToLatitude(posX);
			longitude = ScaleConverter.convertToLongitude(posY);
			// cria uma posicao com estas coordenadas
			geoPoint = new GeoPosition(new Date(),latitude,longitude);
			// Gera de forma aleatoria um tempo de viagem para a proxima parada
			// Tempo maximo colocado com 60 minutos
			timeTrip = (int)(Math.random() * (MAX_TRIP_TIME -MIN_TRIP_TIME))+MIN_TRIP_TIME;
			// caso que seja o ultimo ponto é fixado o tempo de parada 
			if( i != (n-1)){
				point = new PositionPath(geoPoint,startTime,timePoint);
				point.setLeaveTime(startTime+timePoint);
				dayTime -= (timePoint + timeTrip);
				startTime = startTime + timePoint + timeTrip; 
			}
			else{
				point = new PositionPath(geoPoint,startTime,dayTime);
				point.setLeaveTime(startTime+dayTime);
			}
			// adiciona o ponto de parada na lista
			points.add(point);
		}
		return new AgentPath(points);
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PathFactory               factory;
		ArrayList<PositionPath>      data;
		Iterator<PositionPath>   iterator;
		PositionPath             position;
		AgentPath                    path;
		int                             i;
		IGeoPosition                  pos;
		
		factory = new PathFactory();
		// seta escalas
		ScaleConverter.height = 200;
		ScaleConverter.width  = 200;
		ScaleConverter.latIni = 0;
		ScaleConverter.latEnd = 2.0;
		ScaleConverter.longIni = 0;
		ScaleConverter.longEnd = 2.0;
		
		path = factory.create(200, 200, 3);
		data = path.getDataPath();
		iterator = data.iterator();
		while(iterator.hasNext()){
			position = iterator.next();
			position.print();
		}
		    
		path = new AgentPath();
		path.setDataPath(data);
		for( i = 0; i < 1440; i++){
			pos = path.getPositionAtTime(i);
			System.out.printf("lat = %f long = %f time = %d \n", pos.getLatitude(),pos.getLongitude(),i);
		}

	}

}
