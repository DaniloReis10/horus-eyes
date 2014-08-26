package br.com.fujitec.location.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.fujitec.simulagent.models.AnalyzedData;
import br.com.fujitec.simulagent.models.Sensor;
import br.com.fujitec.simulagent.ui.SimulationResults;

/**
 * @author tiagoporteladesouza@gmail.com
 */
public class FileUtils {
	
    /**
     * <p></p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param simulationAnalysisCounter 
     * @param
     * @return
     */
    public static void saveSimulationAnalyses(final String date, final SimulationResults simulationResults) {
        try {
            final DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            final String timeStamp = formatter.format(new Date()); 
            final String path = FileUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            final File file = new File(path.replace("target/classes/", "Logs/simulation_"+timeStamp+".txt"));
            
            if(!file.exists()) {
                file.createNewFile();
                file.setExecutable(true);
                file.setReadable(true);
                file.setWritable(true);
            }
            
            final PrintStream printStream = new PrintStream(new FileOutputStream(file, true));
            final String log = String.format("+ %s: Correct Predictions: %s | Wrong Predictions: %s | Undetected Agents: %s", date, simulationResults.getNumberOfCorrectPredictions(), simulationResults.getNumberOfWrongPredictions(), simulationResults.getNumberOfUndetectedAgents());
            printStream.println(log);
            
            for (AnalyzedData data: simulationResults.getWrongPredictions()) {
                final String logDetails = String.format("   ++ Device Position at Detection -> Latitude: %s | Longitude: %s | Real Mobility: %s | Predicted Mobility: %s", data.getDevicePosition().getLatitude(), data.getDevicePosition().getLongitude(), data.getRealMobility(), data.getPredictedMobility());
                printStream.println(logDetails);
                
                for (Sensor sensor: data.getSensorsThatDetectedTheDevice()) {
                    final String logSubDetails = String.format("       +++ Sensor Position at Detection -> Latitude: %s | Longitude: %s", sensor.getCurrentPosition().getLatitude(), sensor.getCurrentPosition().getLongitude());
                    printStream.println(logSubDetails);
                }
            }
            
            if (printStream.checkError()){
                System.out.println("Could not save the data!");
            }
            
            printStream.close();
        } catch (IOException e) {
            System.out.println(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
