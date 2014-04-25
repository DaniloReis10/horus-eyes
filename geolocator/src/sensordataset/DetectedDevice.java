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
    private IGeoPosition position;
    
    public DetectedDevice(final Integer id, final int time, final int radius, final IGeoPosition position) {
        super();
        this.id = id;
        this.time = time;
        this.radius = radius;
        this.position = position;
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }
    public int getRadius() {
        return radius;
    }
    public void setRadius(int radius) {
        this.radius = radius;
    }
    public IGeoPosition getPosition() {
        return position;
    }
    public void setPosition(IGeoPosition position) {
        this.position = position;
    }
}
