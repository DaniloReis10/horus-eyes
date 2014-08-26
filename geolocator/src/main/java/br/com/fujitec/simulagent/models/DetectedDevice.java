package br.com.fujitec.simulagent.models;

import br.com.fujitec.location.facade.IGeoPosition;

/**
 * @author tiagoportela <tiagoporteladesouza@gmail.com>
 * 
 */
public class DetectedDevice {

    private Integer id;
    private int time;
    private IGeoPosition sensorPositionAtDetection;

    public DetectedDevice(final Integer id, final int time, final IGeoPosition position) {
        super();
        this.id = id;
        this.time = time;
        this.sensorPositionAtDetection = position;
    }

    public Integer getId() {
        return id;
    }

    public int getTime() {
        return time;
    }

    public IGeoPosition getSensorPositionAtDetection() {
        return sensorPositionAtDetection;
    }
}
