package br.com.fujitec.simulagent.ui;

import java.util.ArrayList;
import java.util.List;

import br.com.fujitec.simulagent.models.AnalyzedData;

/**
 * @author tiagoportela <tiagoporteladesouza@gmail.com>
 *
 */
public class SimulationResults {
    
    private int numberOfCorrectPredictions;
    private int numberOfWrongPredictions;
    private int numberOfUndetectedAgents;
    private List<AnalyzedData> wrongPredictions;
    
    public SimulationResults() {
        this.wrongPredictions = new ArrayList<AnalyzedData>();
    }
    
    public void addWrongPredictionData(final AnalyzedData analyzedData) {
        this.getWrongPredictions().add(analyzedData);
    }
    
    public List<AnalyzedData> getWrongPredictions() {
        return wrongPredictions;
    }

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