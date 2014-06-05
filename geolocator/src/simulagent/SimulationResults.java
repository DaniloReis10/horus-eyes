package simulagent;

/**
 * @author tiagoportela <tiagoporteladesouza@gmail.com>
 *
 */
public class SimulationResults {
    
    private int numberOfCorrectPredictions;
    private int numberOfWrongPredictions;
    private int numberOfUndetectedAgents;
    
    public void increaseNumberOfCorrectPredictions() {
        this.numberOfCorrectPredictions++;
    }
    
    public void increaseNumberOfWrongPredictions() {
        this.numberOfWrongPredictions++;
    }
    
    public void increaseNumberOfUndetectedAgents() {
        this.numberOfUndetectedAgents++;
    }

    public int getNumberOfCorrectPredictions() {
        return numberOfCorrectPredictions;
    }

    public int getNumberOfWrongPredictions() {
        return numberOfWrongPredictions;
    }

    public int getNumberOfUndetectedAgents() {
        return numberOfUndetectedAgents;
    }
}