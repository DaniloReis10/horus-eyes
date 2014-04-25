package simulagent;

import java.util.List;
import java.util.Map;

import sensordataset.DetectedDevice;

/**
 * @author tiagoportela <tiagoporteladesouza@gmail.com>
 *
 */
public interface Detector {
    
    
    /**
     * <p>
     *      Implementation of a method to scan the area.        
     * </p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    public abstract void scanArea();
    
    public abstract void addDetectedDevice(DetectedDevice device);
    
    public abstract Map<Integer, List<DetectedDevice>> getDetectedDevices();
}
