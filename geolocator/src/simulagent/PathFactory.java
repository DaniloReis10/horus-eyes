package simulagent;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import location.facade.IGeoPosition;
import location.geoengine.GeoPosition;
import trilaceration.ScaleConverter;

/**
 * Fabrica de rotas diária para os agentes móveis
 * 
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

    public static Path createPath(int width, int height, int n) {
        ArrayList<PositionPath> points;
        int i, dayTime, timePoint, timeWindow;
        double latitude, longitude;
        int posX, posY,oldPosX,oldPosY,dmax;
        int posStartX,posStartY;
        int startTime;
        int timeTrip;
        PositionPath point;
        GeoPosition geoPoint;
    public static Path createAgentPath(int width, int height, int numberOfPositions) {
        return createPath(width, height, numberOfPositions, Agent.class);
    }

    
    public static Path createSensorPath(int width, int height, int numberOfPositions) {
        return createPath(width, height, numberOfPositions, Sensor.class);
    }

    
    public static Path createPath(int width, int height, int numberOfPositions, Class<? extends Device> deviceClass) {
        final List<PositionPath> positions = new ArrayList<PositionPath>();
        int numberOfMinutesPerDay = TICKS_DAY;
        int startTime = 0;
        
        // define a largura media do tempo de parada
        timeWindow = dayTime / n;
        // seta a hora de cehgada inicial como 0:00 ou 0 minuto
        startTime = 0;
        dmax = (int)(0.25*width);
        oldPosX = oldPosY = 0;
        // Faz loop para gerar n pontos de parada preenchendo de forma aleatoria
        // o tempo
        // de chagada, saida e tempo para se locomover para a proxima parada
        for (int i = 0; i < numberOfPositions; i++) {
            // Gera o tempo de parada
            timePoint = (int) (timeWindow * Math.random());
            // gera de forma aleatoria a posicao dentro da imagem
            posX =(i==0) ?(int) (width * Math.random()):oldPosX+(int) (dmax * Math.random());
            posY =(i==0) ? (int) (height * Math.random()):oldPosY+(int) (dmax * Math.random());
            oldPosX = posX;
            oldPosY = posY;
            // converte pela escala para latitude e longitude
            latitude = ScaleConverter.convertToLatitude(posX);
            longitude = ScaleConverter.convertToLongitude(posY);
            // cria uma posicao com estas coordenadas
            geoPoint = new GeoPosition(new Date(), latitude, longitude);
            // Gera de forma aleatoria um tempo de viagem para a proxima parada
            // Tempo maximo colocado com 60 minutos
            timeTrip = (int) (Math.random() * (MAX_TRIP_TIME - MIN_TRIP_TIME)) + MIN_TRIP_TIME;
            // caso que seja o ultimo ponto é fixado o tempo de parada
            if (i != (n - 1)) {
                point = new PositionPath(geoPoint, startTime, timePoint);
                point.setLeaveTime(startTime + timePoint);
                dayTime -= (timePoint + timeTrip);
                startTime = startTime + timePoint + timeTrip;
            } else {
                point = new PositionPath(geoPoint, startTime, dayTime);
                point.setLeaveTime(startTime + dayTime);
            }
            // adiciona o ponto de parada na lista
            points.add(point);
        }
        return new Path(points);

    }
       
    private static IGeoPosition createPosition(Class<? extends Device> deviceClass, int width, int height) {
        final double latitude = ScaleConverter.latIni + Math.random() * (ScaleConverter.latEnd - ScaleConverter.latIni);
        final double longitude = ScaleConverter.longIni + Math.random() * (ScaleConverter.longEnd - ScaleConverter.longIni);
 
        if (deviceClass.getSimpleName().equalsIgnoreCase("Sensor")) {
            return new SensorPosition(latitude, longitude, Sensor.RADIUS);
        } else {
            return new GeoPosition(latitude, longitude);
        }

    }

}
