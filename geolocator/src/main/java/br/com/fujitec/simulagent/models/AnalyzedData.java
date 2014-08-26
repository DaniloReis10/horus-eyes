package br.com.fujitec.simulagent.models;

import java.util.List;

import br.com.fujitec.location.facade.IGeoPosition;

/**
 * @author tiagoportela <tiagoporteladesouza@gmail.com>
 *
 */
public class AnalyzedData {
    
    private Class<? extends Device> dataClass;
    private Mobility realMobility;
    private Mobility predictedMobility;
    private List<Sensor> sensorsThatDetectedTheDevice;
    private IGeoPosition devicePosition;
    
    public AnalyzedData(Class<? extends Device> dataClass, Mobility realMobility, Mobility predictedMobility) {
        super();
        this.dataClass = dataClass;
        this.realMobility = realMobility;
        this.predictedMobility = predictedMobility;
    }

    /**
     * @param devicePosition 
     * @param sensorsThatDetectedTheDevice
     * @param mobility
     * @param predictedMobility
     */
    public AnalyzedData(final IGeoPosition devicePosition, final List<Sensor> sensorsThatDetectedTheDevice, final Mobility realMobility, final Mobility predictedMobility) {
        super();
        this.devicePosition = devicePosition;
        this.sensorsThatDetectedTheDevice = sensorsThatDetectedTheDevice;
        this.realMobility = realMobility;
        this.predictedMobility = predictedMobility;
    }

    public Class<? extends Device> getDataClass() {
        return dataClass;
    }

    public Mobility getRealMobility() {
        return realMobility;
    }

    public Mobility getPredictedMobility() {
        return predictedMobility;
    }

    public List<Sensor> getSensorsThatDetectedTheDevice() {
        return sensorsThatDetectedTheDevice;
    }

    public IGeoPosition getDevicePosition() {
        return devicePosition;
    }

    /**
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    public boolean isPredictionCorrect() {
        return this.realMobility.equals(this.predictedMobility);
    }

    /**
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    public boolean isPredictionUndefined() {
        return this.predictedMobility.equals(Mobility.UNDEFINED);
    }
}
