package simulagent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableCellRenderer;

import location.geoengine.DevicesController;
import sensordataset.AnalyzedData;
import sensordataset.DataAnalyzer;

/**
 * @author tiagoportela <tiagoporteladesouza@gmail.com>
 * 
 */
public class SimulationFrame extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 3144519998583203323L;

    private JLabel numberOfFixedAgentsLabel;
    private JTextField numberOfFixedAgentsTextField;

    private JLabel numberOfMobileAgentsLabel;
    private JTextField numberOfMobileAgentsTextField;

    private JLabel numberOfFixedSensorsLabel;
    private JTextField numberOfFixedSensorsTextField;
    
    private JLabel numberOfMobileSensors;
    private JTextField numberOfMobileSensorsTextField;

    private JButton startSimulationButton;
    private JButton stopSimulationButton;
    private JButton analyzeSimulationButton;

    private int width;
    private int height;

    private SimulationPanel drawingPanel;
    private JLabel horusEyeLabel;
    
    private JInternalFrame configurationFrame;
    private JInternalFrame simulationFrame;
    private JInternalFrame resultsFrame;
    private JInternalFrame labelsFrame;
    private JCheckBox chckbxFixedAgent;
    private JCheckBox chckbxMobileAgent;
    private JCheckBox chckbxFixedSensor;
    private JCheckBox chckbxMobileSensor;
    private JTable resultsTable;
    private JScrollPane scrollPane;
    
    private SimulationResults simulationResults;
    
    /**
     * 
     */
    public SimulationFrame() {
        this(1024, 768);
    }

    /**
     * 
     * @param width
     * @param height
     */
    public SimulationFrame(int width, int height) {
        this.width = width;
        this.height = height;

        this.setSize(width, height);

        initializeInternalFrames();
        initializeFormComponents();
        initializeButtons();
        initializeLabels();
        initializeResultTable();
        initializeLayout();
    }

    /**
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    private void initializeFormComponents() {
        this.horusEyeLabel = new JLabel("Horus Eye");
        this.horusEyeLabel.setFont(new Font("Dialog", Font.BOLD, 14));
        
        this.drawingPanel = new SimulationPanel(980, 480, this);
        this.drawingPanel.setBackground(Color.WHITE);

        this.numberOfFixedAgentsLabel = new JLabel("Number of Fixed Agents");
        this.numberOfFixedAgentsTextField = new JTextField();
        this.numberOfFixedAgentsTextField.setColumns(10);
        
        this.numberOfMobileAgentsLabel = new JLabel("Number of Mobile Agents");
        this.numberOfMobileAgentsTextField = new JTextField();
        this.numberOfMobileAgentsTextField.setColumns(10);

        this.numberOfFixedSensorsLabel = new JLabel("Number of Fixed Sensors");
        this.numberOfFixedSensorsTextField = new JTextField();
        this.numberOfFixedSensorsTextField.setColumns(10);

        this.numberOfMobileSensors = new JLabel("Number of Mobile Sensors");
        this.numberOfMobileSensorsTextField = new JTextField();
        this.numberOfMobileSensorsTextField.setColumns(10);
    }

    /**
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    private void initializeInternalFrames() {
        this.labelsFrame = new JInternalFrame("Labels");
        this.labelsFrame.setVisible(true);
        
        this.resultsFrame = new JInternalFrame("Prediction Results");
        this.resultsFrame.setVisible(true);
        
        this.configurationFrame = new JInternalFrame("Configuration");
        this.configurationFrame.setVisible(true);
        
        this.simulationFrame = new JInternalFrame("Simulation");
        this.simulationFrame.setVisible(true);
        
        try {
            this.configurationFrame.setIcon(true);
            this.simulationFrame.setIcon(true);
            this.resultsFrame.setIcon(true);
            this.labelsFrame.setIcon(true);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    private void initializeButtons() {
        this.startSimulationButton = new JButton("Start Simulation");
        this.startSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimulationFrame.this.startSimulationButton.setEnabled(false);
                SimulationFrame.this.stopSimulationButton.setEnabled(true);
                SimulationFrame.this.analyzeSimulationButton.setEnabled(true);
                SimulationFrame.this.startSimulation();
            }
        });

        this.stopSimulationButton = new JButton("Stop Simulation");
        this.stopSimulationButton.setEnabled(false);
        this.stopSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimulationFrame.this.stopSimulation();
                SimulationFrame.this.startSimulationButton.setEnabled(true);
                SimulationFrame.this.stopSimulationButton.setEnabled(false);
                SimulationFrame.this.analyzeSimulationButton.setEnabled(false);
            }
        });
        
        this.analyzeSimulationButton = new JButton("Analyze Simulation");
        this.analyzeSimulationButton.setEnabled(false);
        this.analyzeSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimulationFrame.this.analyzeSimulation();
            }
        });
    }
    
    /**
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    private void initializeLabels() {
        this.chckbxMobileAgent = new JCheckBox("Mobile Agent");
        this.chckbxMobileAgent.setForeground(Color.WHITE);
        this.chckbxMobileAgent.setBackground(Color.BLUE);
        this.chckbxMobileAgent.setEnabled(false);
        
        this.chckbxFixedAgent = new JCheckBox("Fixed Agent");
        this.chckbxFixedAgent.setEnabled(false);
        this.chckbxFixedAgent.setForeground(Color.WHITE);
        this.chckbxFixedAgent.setBackground(Color.ORANGE);
        
        this.chckbxFixedSensor = new JCheckBox("Fixed Sensor");
        this.chckbxFixedSensor.setEnabled(false);
        this.chckbxFixedSensor.setForeground(Color.WHITE);
        this.chckbxFixedSensor.setBackground(Color.BLACK);
        
        this.chckbxMobileSensor = new JCheckBox("Mobile Sensor");
        this.chckbxMobileSensor.setForeground(Color.WHITE);
        this.chckbxMobileSensor.setBackground(Color.RED);
        this.chckbxMobileSensor.setEnabled(false);
    }

    /**
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    private void initializeResultTable() {
        String[] columnNames = {"Correct", "Wrong", "Undetected"};
        
        Object[][] data = {{0, 0, 0}};
        
        this.resultsTable = new JTable(data, columnNames);
        this.resultsTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        this.resultsTable.setFillsViewportHeight(true);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        this.resultsTable.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        this.resultsTable.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        this.resultsTable.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        
        this.scrollPane = new JScrollPane(resultsTable);
        
    }
    
    /**
     * <p>
     * This methods configure all column and row specifications of the chosen
     * layout.
     * </p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    private void initializeLayout() {
        
        
        GroupLayout mainGroupLayout = new GroupLayout(getContentPane());
        this.getContentPane().setLayout(mainGroupLayout);
        mainGroupLayout.setHorizontalGroup(
            mainGroupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(mainGroupLayout.createSequentialGroup()
                    .addGroup(mainGroupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(mainGroupLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(mainGroupLayout.createParallelGroup(Alignment.TRAILING)
                                .addComponent(simulationFrame, GroupLayout.DEFAULT_SIZE, 998, Short.MAX_VALUE)
                                .addGroup(mainGroupLayout.createSequentialGroup()
                                    .addComponent(configurationFrame, GroupLayout.PREFERRED_SIZE, 526, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(resultsFrame, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(labelsFrame, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE))))
                        .addGroup(mainGroupLayout.createSequentialGroup()
                            .addGap(408)
                            .addComponent(horusEyeLabel)))
                    .addContainerGap())
        );
        mainGroupLayout.setVerticalGroup(
            mainGroupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(mainGroupLayout.createSequentialGroup()
                    .addGap(6)
                    .addComponent(horusEyeLabel)
                    .addGap(18)
                    .addGroup(mainGroupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(resultsFrame, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                        .addComponent(configurationFrame, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                        .addComponent(labelsFrame, 0, 0, Short.MAX_VALUE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(simulationFrame, GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
                    .addContainerGap())
        );
        
        GroupLayout simulationGroupLayout = new GroupLayout(simulationFrame.getContentPane());
        this.simulationFrame.getContentPane().setLayout(simulationGroupLayout);
        simulationGroupLayout.setHorizontalGroup(
            simulationGroupLayout.createParallelGroup(Alignment.TRAILING)
                .addComponent(drawingPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
        );
        simulationGroupLayout.setVerticalGroup(
            simulationGroupLayout.createParallelGroup(Alignment.LEADING)
                .addComponent(drawingPanel, GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
        );
        
        GroupLayout configurationGroupLayout = new GroupLayout(configurationFrame.getContentPane());
        this.configurationFrame.getContentPane().setLayout(configurationGroupLayout);
        configurationGroupLayout.setHorizontalGroup(
            configurationGroupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(configurationGroupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(configurationGroupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(numberOfMobileSensors)
                        .addComponent(numberOfFixedSensorsLabel)
                        .addGroup(configurationGroupLayout.createParallelGroup(Alignment.TRAILING, false)
                            .addComponent(numberOfMobileAgentsLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(numberOfFixedAgentsLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(configurationGroupLayout.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(numberOfMobileAgentsTextField, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
                        .addGroup(configurationGroupLayout.createParallelGroup(Alignment.LEADING, false)
                            .addComponent(numberOfFixedSensorsTextField, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                            .addComponent(numberOfFixedAgentsTextField, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                            .addComponent(numberOfMobileSensorsTextField, 0, 0, Short.MAX_VALUE)))
                    .addGap(36)
                    .addGroup(configurationGroupLayout.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(analyzeSimulationButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(stopSimulationButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(startSimulationButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap(74, Short.MAX_VALUE))
        );
        configurationGroupLayout.setVerticalGroup(
            configurationGroupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(configurationGroupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(configurationGroupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(configurationGroupLayout.createSequentialGroup()
                            .addComponent(startSimulationButton)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(stopSimulationButton)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(analyzeSimulationButton))
                        .addGroup(configurationGroupLayout.createSequentialGroup()
                            .addGroup(configurationGroupLayout.createParallelGroup(Alignment.BASELINE)
                                .addComponent(numberOfFixedAgentsTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(numberOfFixedAgentsLabel))
                            .addGap(10)
                            .addGroup(configurationGroupLayout.createParallelGroup(Alignment.BASELINE)
                                .addComponent(numberOfMobileAgentsLabel)
                                .addComponent(numberOfMobileAgentsTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(configurationGroupLayout.createParallelGroup(Alignment.LEADING)
                                .addComponent(numberOfFixedSensorsTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(numberOfFixedSensorsLabel))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(configurationGroupLayout.createParallelGroup(Alignment.BASELINE)
                                .addComponent(numberOfMobileSensors)
                                .addComponent(numberOfMobileSensorsTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
        );
        
        GroupLayout labelsGroupLayout = new GroupLayout(labelsFrame.getContentPane());
        this.labelsFrame.getContentPane().setLayout(labelsGroupLayout);
        labelsGroupLayout.setHorizontalGroup(
            labelsGroupLayout.createParallelGroup(Alignment.TRAILING)
                .addGroup(Alignment.LEADING, labelsGroupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(labelsGroupLayout.createParallelGroup(Alignment.TRAILING, false)
                        .addComponent(chckbxMobileSensor, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chckbxFixedSensor, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chckbxFixedAgent, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                        .addComponent(chckbxMobileAgent, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap(107, Short.MAX_VALUE))
        );
        labelsGroupLayout.setVerticalGroup(
            labelsGroupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(labelsGroupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(chckbxFixedAgent)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(chckbxMobileAgent)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(chckbxFixedSensor)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(chckbxMobileSensor)
                    .addContainerGap(14, Short.MAX_VALUE))
        );
        
        GroupLayout resultsGroupLayout = new GroupLayout(resultsFrame.getContentPane());
        resultsFrame.getContentPane().setLayout(resultsGroupLayout);
        resultsGroupLayout.setHorizontalGroup(
            resultsGroupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(resultsGroupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                    .addContainerGap())
        );
        resultsGroupLayout.setVerticalGroup(
            resultsGroupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(Alignment.TRAILING, resultsGroupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                    .addContainerGap())
        );
    }
    
    public void startSimulation() {
        this.drawingPanel.startSimulation();
    }
    
    public void stopSimulation() {
        this.drawingPanel.stopSimulation();
    }

    public void analyzeSimulation() {
        final List<Device> devices = DevicesController.getInstance().getDevices();
        final List<AnalyzedData> analyzedData = DataAnalyzer.predictDevicesClasses(devices);
        
        this.simulationResults = new SimulationResults();
        
        for (AnalyzedData data : analyzedData) {
            if(data.isPredictionCorrect()) {
                this.simulationResults.increaseNumberOfCorrectPredictions();
            } else if(data.isPredictionUndefined()) {
                this.simulationResults.increaseNumberOfUndetectedAgents();
            } else {
                this.simulationResults.increaseNumberOfWrongPredictions();
            }
        }
        
        this.resultsTable.setValueAt(this.simulationResults.getNumberOfCorrectPredictions(), 0, 0);
        this.resultsTable.setValueAt(this.simulationResults.getNumberOfWrongPredictions(), 0, 1);
        this.resultsTable.setValueAt(this.simulationResults.getNumberOfUndetectedAgents(), 0, 2);
//        SimulationAnalysisFrame.showAnalysis(analyzedData);
    }
    
    public short getNumberOfFixedAgents() {
        return this.numberOfFixedAgentsTextField.getText().isEmpty()? 0: Short.parseShort(this.numberOfFixedAgentsTextField.getText());  
    }

    public short getNumberOfMobileAgents() {
        return this.numberOfMobileAgentsTextField.getText().isEmpty()? 0: Short.parseShort(this.numberOfMobileAgentsTextField.getText());
    }

    public short getNumberOfFixedSensors() {
        return this.numberOfFixedSensorsTextField.getText().isEmpty()? 0: Short.parseShort(this.numberOfFixedSensorsTextField.getText());
    }
    
    public short getNumberOfMobileSensors() {
        return this.numberOfMobileSensorsTextField.getText().isEmpty()? 0: Short.parseShort(this.numberOfMobileSensorsTextField.getText());
    }
}