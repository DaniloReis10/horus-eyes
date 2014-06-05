package sensordataset;

import location.facade.IGeoPosition;
import simulagent.Device;
import simulagent.Mobility;

/**
 * @author tiagoportela <tiagoporteladesouza@gmail.com>
 *
 */
public class AnalyzedData {
    
    private Class<? extends Device> dataClass;
    private Mobility realMobility;
    private Mobility predictedMobility;
    private IGeoPosition predictedPosition;
    
    public AnalyzedData(Class<? extends Device> dataClass, Mobility realMobility, Mobility predictedMobility, IGeoPosition predictedPosition) {
        super();
        this.dataClass = dataClass;
        this.realMobility = realMobility;
        this.predictedMobility = predictedMobility;
        this.predictedPosition = predictedPosition;
    }
    
    public AnalyzedData(Class<? extends Device> dataClass, Mobility realMobility, Mobility predictedMobility) {
        super();
        this.dataClass = dataClass;
        this.realMobility = realMobility;
        this.predictedMobility = predictedMobility;
        this.predictedPosition = null;
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

    public IGeoPosition getPredictedPosition() {
        return predictedPosition;
    }

    /**
     * <p></p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    public boolean isPredictionCorrect() {
        return this.realMobility.equals(this.predictedMobility);
    }

    /**
     * <p></p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    public boolean isPredictionUndefined() {
        return this.predictedMobility.equals(Mobility.UNDEFINED);
    }
}
