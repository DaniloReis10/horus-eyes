package simulagent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import location.facade.IGeoPosition;
import location.geoengine.GeoPosition;
import trilaceration.ScaleConverter;
import trilaceration.SensorPosition;

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
        final int timeWindow = numberOfMinutesPerDay / numberOfPositions;

        // Faz loop para gerar n pontos de parada preenchendo de forma aleatoria o tempo
        // de chagada, saida e tempo para se locomover para a proxima parada
        for (int i = 0; i < numberOfPositions; i++) {
            // Gera o tempo de parada
            final int timePoint = (int) (timeWindow * Math.random());
            
            final IGeoPosition geoPosition = createPosition(deviceClass, width, height);

            final int tripTime = (int) (Math.random() * (MAX_TRIP_TIME - MIN_TRIP_TIME)) + MIN_TRIP_TIME;
            
            final PositionPath position;

            // caso seja o ultimo ponto é fixado o tempo de parada
            if (i != (numberOfPositions - 1)) {
                position = new PositionPath(geoPosition, startTime, timePoint);
                position.setLeaveTime(startTime + timePoint);
                numberOfMinutesPerDay -= (timePoint + tripTime);
                startTime = startTime + timePoint + tripTime;
            } else {
                position = new PositionPath(geoPosition, startTime, numberOfMinutesPerDay);
                position.setLeaveTime(startTime + numberOfMinutesPerDay);
            }
            
            // adiciona o ponto de parada na lista
            positions.add(position);
        }
        
        return new Path(positions);
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
