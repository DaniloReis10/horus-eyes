package br.com.fujitec.simulagent.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableCellRenderer;

import trilaceration.ScaleConverter;
import br.com.fujitec.location.geoengine.DevicesController;
import br.com.fujitec.location.utils.DataAnalyzer;
import br.com.fujitec.location.utils.FileUtils;
import br.com.fujitec.simulagent.factories.PathFactory;
import br.com.fujitec.simulagent.models.AnalyzedData;
import br.com.fujitec.simulagent.models.Device;

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
    
    private JLabel numberOfMobileSensorsLabel;
    private JTextField numberOfMobileSensorsTextField;
    
    private JLabel numberOfSimulationDaysLabel;
    private JTextField numberOfSimulationDaysTextField;

    private int width;
    private int height;

    private JProgressBar simulationProgressBar;
    private SimulationPanel drawingPanel;
    
    private JFrame configurationFrame;
    
    private JFrame labelsFrame;
    private JCheckBox checkboxFixedAgent;
    private JCheckBox checkboxMobileAgent;
    private JCheckBox checkboxFixedSensor;
    private JCheckBox checkboxMobileSensor;
    private JCheckBox checkboxRegenerateOnlyAgentsRoutesEveryday;
    
    private JFrame resultsFrame;
    private JTable resultsTable;
    private JScrollPane scrollPane;
    
    private JMenuBar menuBar;
    private JMenu optionsMenu;
    private JMenu simulationMenu;
    private JMenuItem startSimulationMenuItem;
    private JMenuItem stopSimulationMenuItem;
    private JMenuItem analyzeSimulationMenuItem;
    private JMenuItem testAgentRoute;
    private JMenuItem configureSimulationMenuItem;
    private JMenu helpMenu;
    private JMenuItem labelsMenuItem;
    
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

        initializeFrames();
        initializeComponents();
        initializeMenu();
        initializeLabels();
        initializeResultTable();
        initializeLayout();
    }

    /**
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    private void initializeComponents() {

        this.numberOfFixedAgentsLabel = new JLabel("Number of Fixed Agents");
        this.numberOfFixedAgentsTextField = new JTextField();
        this.numberOfFixedAgentsTextField.setColumns(10);
        
        this.numberOfMobileAgentsLabel = new JLabel("Number of Mobile Agents");
        this.numberOfMobileAgentsTextField = new JTextField();
        this.numberOfMobileAgentsTextField.setColumns(10);

        this.numberOfFixedSensorsLabel = new JLabel("Number of Fixed Sensors");
        this.numberOfFixedSensorsTextField = new JTextField();
        this.numberOfFixedSensorsTextField.setColumns(10);

        this.numberOfMobileSensorsLabel = new JLabel("Number of Mobile Sensors");
        this.numberOfMobileSensorsTextField = new JTextField();
        this.numberOfMobileSensorsTextField.setColumns(10);
        
        this.numberOfSimulationDaysLabel = new JLabel("Number of Simulation Days");
        this.numberOfSimulationDaysTextField = new JTextField();
        this.numberOfSimulationDaysTextField.setColumns(10);
        
        this.drawingPanel = new SimulationPanel(ScaleConverter.width, ScaleConverter.height, this);
        this.drawingPanel.setBackground(Color.WHITE);
        
        this.simulationProgressBar = new JProgressBar(0, PathFactory.TICKS_DAY);
        this.simulationProgressBar.setStringPainted(true);
    }

    /**
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    private void initializeFrames() {
        this.labelsFrame = new JFrame("Labels");
        this.configurationFrame = new JFrame("Configuration");
        this.resultsFrame = new JFrame("Prediction Results");
    }
    
    /**
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    private void initializeMenu() {
        this.menuBar = new JMenuBar();
        this.simulationMenu = new JMenu("Simulation");
        this.optionsMenu = new JMenu("Options");
        this.helpMenu = new JMenu("Help");
        
        this.startSimulationMenuItem = new JMenuItem("Start Simulation");
        this.stopSimulationMenuItem = new JMenuItem("Stop Simulation");
        this.analyzeSimulationMenuItem = new JMenuItem("Analyze Simulation");
        this.configureSimulationMenuItem = new JMenuItem("Configure Simulation");
        this.testAgentRoute = new JMenuItem("Test Agent Route");
        this.labelsMenuItem = new JMenuItem("Labels");
        
        this.startSimulationMenuItem.setEnabled(true);
        this.stopSimulationMenuItem.setEnabled(false);
        this.analyzeSimulationMenuItem.setEnabled(false);
        
        this.setJMenuBar(menuBar);
        this.menuBar.add(simulationMenu);
        this.menuBar.add(optionsMenu);
        this.menuBar.add(helpMenu);
        
        this.simulationMenu.add(startSimulationMenuItem);
        this.simulationMenu.add(stopSimulationMenuItem);
        this.simulationMenu.add(analyzeSimulationMenuItem);
        this.simulationMenu.add(testAgentRoute);
        this.optionsMenu.add(configureSimulationMenuItem);
        this.helpMenu.add(labelsMenuItem);
        
        this.initializeMenuItemActionListerners();
    }
    
    /**
     * <p></p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    private void initializeMenuItemActionListerners() {
        this.startSimulationMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimulationFrame.this.startSimulationMenuItem.setEnabled(false);
                SimulationFrame.this.testAgentRoute.setEnabled(false);
                SimulationFrame.this.stopSimulationMenuItem.setEnabled(true);
                SimulationFrame.this.analyzeSimulationMenuItem.setEnabled(true);
                SimulationFrame.this.startSimulation();
            }
        });
        
        this.stopSimulationMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimulationFrame.this.stopSimulation();
                SimulationFrame.this.startSimulationMenuItem.setEnabled(true);
                SimulationFrame.this.testAgentRoute.setEnabled(true);
                SimulationFrame.this.stopSimulationMenuItem.setEnabled(false);
                SimulationFrame.this.analyzeSimulationMenuItem.setEnabled(false);
            }
        });
        
        this.analyzeSimulationMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimulationFrame.this.analyzeSimulation();
                SimulationFrame.this.resultsFrame.setSize(350, 150);
                SimulationFrame.this.resultsFrame.setVisible(true);
            }
        });
        
        this.testAgentRoute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimulationFrame.this.startSimulationMenuItem.setEnabled(false);
                SimulationFrame.this.testAgentRoute.setEnabled(false);
                SimulationFrame.this.stopSimulationMenuItem.setEnabled(true);
                SimulationFrame.this.analyzeSimulationMenuItem.setEnabled(false);
                SimulationFrame.this.testAgentRoute();
            }
        });
        
        this.configureSimulationMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimulationFrame.this.configurationFrame.setSize(400, 240);
                SimulationFrame.this.configurationFrame.setVisible(true);
            }
        });
        
        this.labelsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimulationFrame.this.labelsFrame.setSize(190, 100);
                SimulationFrame.this.labelsFrame.setVisible(true);
            }
        });
    }

    /**
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    private void initializeLabels() {
        this.checkboxMobileAgent = new JCheckBox("Mobile Agent");
        this.checkboxMobileAgent.setForeground(Color.WHITE);
        this.checkboxMobileAgent.setBackground(Color.BLUE);
        this.checkboxMobileAgent.setEnabled(false);
        
        this.checkboxFixedAgent = new JCheckBox("Fixed Agent");
        this.checkboxFixedAgent.setEnabled(false);
        this.checkboxFixedAgent.setForeground(Color.WHITE);
        this.checkboxFixedAgent.setBackground(Color.ORANGE);
        
        this.checkboxFixedSensor = new JCheckBox("Fixed Sensor");
        this.checkboxFixedSensor.setEnabled(false);
        this.checkboxFixedSensor.setForeground(Color.WHITE);
        this.checkboxFixedSensor.setBackground(Color.BLACK);
        
        this.checkboxMobileSensor = new JCheckBox("Mobile Sensor");
        this.checkboxMobileSensor.setForeground(Color.WHITE);
        this.checkboxMobileSensor.setBackground(Color.RED);
        this.checkboxMobileSensor.setEnabled(false);
        
        this.checkboxRegenerateOnlyAgentsRoutesEveryday = new JCheckBox("Gerar novas rotas a cada dia apenas para Agentes");
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
        mainGroupLayout.setHorizontalGroup(
            mainGroupLayout.createParallelGroup(Alignment.TRAILING)
                .addGroup(Alignment.LEADING, mainGroupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(mainGroupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(simulationProgressBar, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 998, Short.MAX_VALUE)
                        .addComponent(drawingPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 998, Short.MAX_VALUE))
                    .addContainerGap())
        );
        mainGroupLayout.setVerticalGroup(
            mainGroupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(Alignment.TRAILING, mainGroupLayout.createSequentialGroup()
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(simulationProgressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(drawingPanel, GroupLayout.PREFERRED_SIZE, 668, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
        );
        this.getContentPane().setLayout(mainGroupLayout);
        
        GroupLayout configurationGroupLayout = new GroupLayout(configurationFrame.getContentPane());
        configurationGroupLayout.setHorizontalGroup(
            configurationGroupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(configurationGroupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(configurationGroupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(numberOfMobileSensorsLabel)
                        .addComponent(numberOfFixedSensorsLabel)
                    .addGroup(configurationGroupLayout.createParallelGroup(Alignment.TRAILING, false)
                        .addComponent(numberOfMobileAgentsLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(numberOfFixedAgentsLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))
                    .addGroup(configurationGroupLayout.createParallelGroup(Alignment.TRAILING, false)
                        .addComponent(numberOfSimulationDaysLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addPreferredGap(ComponentPlacement.RELATED)

                    .addGroup(configurationGroupLayout.createParallelGroup(Alignment.LEADING, false)
                    .addComponent(numberOfMobileAgentsTextField, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
                    .addGroup(configurationGroupLayout.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(numberOfFixedSensorsTextField, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                        .addComponent(numberOfFixedAgentsTextField, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                        .addComponent(numberOfMobileSensorsTextField, 0, 0, Short.MAX_VALUE)
                        .addComponent(numberOfSimulationDaysTextField, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)))
                    .addContainerGap(218, Short.MAX_VALUE))
                    
                    .addGroup(configurationGroupLayout.createParallelGroup(Alignment.TRAILING, false)
                            .addComponent(checkboxRegenerateOnlyAgentsRoutesEveryday, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        configurationGroupLayout.setVerticalGroup(
            configurationGroupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(configurationGroupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(configurationGroupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(numberOfFixedAgentsTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(numberOfFixedAgentsLabel))
                    .addGap(10)
                    .addGroup(configurationGroupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(numberOfMobileAgentsLabel)
                        .addComponent(numberOfMobileAgentsTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGap(10)
                    .addGroup(configurationGroupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(numberOfFixedSensorsTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(numberOfFixedSensorsLabel))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGap(10)
                    .addGroup(configurationGroupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(numberOfMobileSensorsLabel)
                        .addComponent(numberOfMobileSensorsTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(10)
                    .addGroup(configurationGroupLayout.createParallelGroup(Alignment.BASELINE)
                            .addComponent(numberOfSimulationDaysLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(numberOfSimulationDaysTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(10)
                    .addGroup(configurationGroupLayout.createParallelGroup(Alignment.BASELINE)
                            .addComponent(checkboxRegenerateOnlyAgentsRoutesEveryday)))
        );
        this.configurationFrame.getContentPane().setLayout(configurationGroupLayout);
        
        GroupLayout labelsGroupLayout = new GroupLayout(labelsFrame.getContentPane());
        this.labelsFrame.getContentPane().setLayout(labelsGroupLayout);
        labelsGroupLayout.setHorizontalGroup(
            labelsGroupLayout.createParallelGroup(Alignment.TRAILING)
                .addGroup(Alignment.LEADING, labelsGroupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(labelsGroupLayout.createParallelGroup(Alignment.TRAILING, false)
                        .addComponent(checkboxMobileSensor, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(checkboxFixedSensor, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(checkboxFixedAgent, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                        .addComponent(checkboxMobileAgent, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap(107, Short.MAX_VALUE))
        );
        labelsGroupLayout.setVerticalGroup(
            labelsGroupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(labelsGroupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(checkboxFixedAgent)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(checkboxMobileAgent)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(checkboxFixedSensor)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(checkboxMobileSensor)
                    .addContainerGap(14, Short.MAX_VALUE))
        );
        
        GroupLayout resultsGroupLayout = new GroupLayout(resultsFrame.getContentPane());
        resultsGroupLayout.setHorizontalGroup(
            resultsGroupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(resultsGroupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 334, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(642, Short.MAX_VALUE))
        );
        resultsGroupLayout.setVerticalGroup(
            resultsGroupLayout.createParallelGroup(Alignment.TRAILING)
                .addGroup(Alignment.LEADING, resultsGroupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(42, Short.MAX_VALUE))
        );
        resultsFrame.getContentPane().setLayout(resultsGroupLayout);
    }
    
    public void startSimulation() {
        this.drawingPanel.startSimulation();
    }
    
    public void resetSimulationToDefault() {
        this.stopSimulationMenuItem.doClick();
    }
    
    public void stopSimulation() {
        this.drawingPanel.stopSimulation();
    }
    
    public void testAgentRoute() {
        this.drawingPanel.testAgentRoute();
    }

    public void analyzeSimulation() {
        final String simulationTime = this.simulationProgressBar.getString();
        System.out.println("---------- Simulation Analysis Starting at: " + simulationTime + " ----------");
        
        final List<Device> devices = DevicesController.getInstance().getDevices();
        final List<AnalyzedData> analyzedData = DataAnalyzer.predictDevicesClasses(devices, this);
        
        final SimulationResults simulationResults = new SimulationResults();
        
        for (AnalyzedData data : analyzedData) {
            if(data.isPredictionCorrect()) {
                simulationResults.increaseNumberOfCorrectPredictions();
            } else if(data.isPredictionUndefined()) {
                simulationResults.increaseNumberOfUndetectedAgents();
            } else {
                simulationResults.increaseNumberOfWrongPredictions();
                simulationResults.addWrongPredictionData(data);
            }
        }
        
        this.resultsTable.setValueAt(simulationResults.getNumberOfCorrectPredictions(), 0, 0);
        this.resultsTable.setValueAt(simulationResults.getNumberOfWrongPredictions(), 0, 1);
        this.resultsTable.setValueAt(simulationResults.getNumberOfUndetectedAgents(), 0, 2);
        
        FileUtils.saveSimulationAnalyses(simulationTime, simulationResults);
        System.out.println("----------//----------//----------//----------//----------//----------//----------");
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
    
    public short getNumberOfSimulationDays() {
        return this.numberOfSimulationDaysTextField.getText().isEmpty()? 1: Short.parseShort(this.numberOfSimulationDaysTextField.getText());
    }
    
    public boolean regenerateOnlyAgentsRoutesEveryday() {
        return this.checkboxRegenerateOnlyAgentsRoutesEveryday.isSelected();
    }

    /**
     * <p></p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    public void updateSimulationProgressBar(final int simulationTime, final String formattedSimulationTime) {
        this.simulationProgressBar.setValue(simulationTime);
        this.simulationProgressBar.setString(formattedSimulationTime);
    }
    
    /**
     * <p></p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    public void updateAnalysisProgressBar(final int numberOfAgents, final int analysisTime) {
        System.out.println(analysisTime + "%");
    }
}
    