package sensordataset;

import location.facade.IGeoPosition;

/**
 * @author tiagoportela <tiagoporteladesouza@gmail.com>
 * 
 */
public class DetectedDevice {

    private Integer id;
    private int time;
    private int radius;
    private IGeoPosition sensorPositionAtDetection;

    public DetectedDevice(final Integer id, final int time, final int radius, final IGeoPosition position) {
        super();
        this.id = id;
        this.time = time;
        this.radius = radius;
        this.sensorPositionAtDetection = position;
    }

    public Integer getId() {
        return id;
    }

    public int getTime() {
        return time;
    }

    public int getRadius() {
        return radius;
    }

    public IGeoPosition getSensorPositionAtDetection() {
        return sensorPositionAtDetection;
    }
}
