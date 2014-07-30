
package br.com.fujitec.simulagent.factories;

import java.util.ArrayList;
import java.util.List;

import br.com.fujitec.location.facade.IGeoPosition;
import br.com.fujitec.location.geoengine.DevicesController;
import br.com.fujitec.location.geoengine.GeoPosition;
import br.com.fujitec.simulagent.models.Agent;
import br.com.fujitec.simulagent.models.Device;
import br.com.fujitec.simulagent.models.Path;
import br.com.fujitec.simulagent.models.PositionPath;
import br.com.fujitec.simulagent.models.Sensor;
import trilaceration.ScaleConverter;
import trilaceration.SensorPosition;

/**
 * Fabrica de rotas di������ria para os agentes m������veis
 * 
 * @author DaniloReis
 * 
 */
public class PathFactory {

    public static final int TICKS_DAY = (24 * 60 *60);
    public static final int MAX_TRIP_TIME = 3600;
    public static final int MIN_TRIP_TIME = 300;

    public PathFactory() {
        super();
    }

    public static Path createAgentPath(int width, int height, int numberOfPositions) {
        return createPath(width, height, numberOfPositions, Agent.class);
    }

    
    public static Path createSensorPath(int width, int height, int numberOfPositions) {
        return createPath(width, height, numberOfPositions, Sensor.class);
    }

    
    public static Path createPath(int width, int height, int numberOfPositions, Class<? extends Device> deviceClass) {
        final List<PositionPath> positions = new ArrayList<PositionPath>();
        int numberOfSecondsPerDay = TICKS_DAY;
        int startTime = 0;
        double factor,distance,speed;
        double startLat,startLong;
        IGeoPosition  firstPosition=null,lastPosition= null;
        int tripTime;
        
        // define a largura media do tempo de parada
        final int timeWindow = numberOfSecondsPerDay / (numberOfPositions);

        startLat = ScaleConverter.latIni;
        startLong = ScaleConverter.longIni;
        factor    = 1.0;
        // Faz loop para gerar n pontos de parada preenchendo de forma aleatoria o tempo
        // de chagada, saida e tempo para se locomover para a proxima parada
        for (int i = 0; i < numberOfPositions; i++) {
            // Gera o tempo de parada
            final int timePoint = (int) (timeWindow * Math.random());
            
            final IGeoPosition geoPosition = createPosition(deviceClass, factor);
           
            startLat  = geoPosition.getLatitude();
            startLong = geoPosition.getLongitude();
            if( i==0 ){
            	firstPosition  = lastPosition = geoPosition;
            	factor      = 1;
            	tripTime    = 0;
            }
            //final  = (int) (Math.random() * (MAX_TRIP_TIME - MIN_TRIP_TIME)) + MIN_TRIP_TIME;
            
            final PositionPath position;

            // caso seja o ultimo ponto ������ fixado o tempo de parada
            if (i != (numberOfPositions - 1)) {
            	distance = DevicesController.calculateDistance(geoPosition, lastPosition);
                // calcula o tempo em segundos para percorre a distancia
            	speed = (0.05 + 0.3*Math.random());
            	// tempo em segundos
                tripTime = (int)(distance / speed);
                // Guarda o ponto anterior
                lastPosition = geoPosition;
                numberOfSecondsPerDay -= (timePoint + tripTime);
                startTime = startTime + tripTime;
                position = new PositionPath(geoPosition, startTime, timePoint);
                position.setLeaveTime(startTime + timePoint);
                startTime = startTime + timePoint;
            } else {
            	// Ultimo ponto da rota
            	distance = DevicesController.calculateDistance(firstPosition, lastPosition);
                // calcula o tempo em segundos para percorre a distancia
            	speed = (0.05 + 0.3*Math.random());
            	// tempo em segundos
                tripTime = (int)(distance / speed);
                tripTime = (tripTime>numberOfSecondsPerDay)?numberOfSecondsPerDay:tripTime;
                startTime = startTime+tripTime;
                numberOfSecondsPerDay = numberOfSecondsPerDay-tripTime;
                position = new PositionPath(firstPosition, startTime, numberOfSecondsPerDay);
                position.setLeaveTime(startTime + numberOfSecondsPerDay);
            }
            
            // adiciona o ponto de parada na lista
            positions.add(position);
        }
        
        return new Path(positions);
    }
       
    private static IGeoPosition createPosition(Class<? extends Device> deviceClass,double factor) {
        double latitude = ScaleConverter.latIni + Math.random() * (ScaleConverter.latEnd - ScaleConverter.latIni)*factor;
        double longitude = ScaleConverter.longIni + Math.random() * (ScaleConverter.longEnd - ScaleConverter.longIni)*factor;
 
        if (deviceClass.getSimpleName().equalsIgnoreCase("Sensor")) {
            return new SensorPosition(latitude, longitude, Sensor.RADIUS_IN_METERS);
        } else {
            return new GeoPosition(latitude, longitude);
        }
    }
}
