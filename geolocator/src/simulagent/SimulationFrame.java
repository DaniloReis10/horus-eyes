package simulagent;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

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

    private int width;
    private int height;

    private SimulationPanel drawingPanel;
    private JLabel horusEyeLabel;
    
    private JInternalFrame configurationFrame;
    private JInternalFrame simulationFrame;
    private JInternalFrame labelsFrame;
    private JCheckBox chckbxFixedAgent;
    private JCheckBox chckbxMobileAgent;
    private JCheckBox chckbxFixedSensor;
    private JCheckBox chckbxMobileSensor;

    /**
     * 
     */
    public SimulationFrame() {
        this(1024, 768);
    }

    /**
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
        
        this.configurationFrame = new JInternalFrame("Configuration");
        this.configurationFrame.setVisible(true);
        
        this.simulationFrame = new JInternalFrame("Simulation");
        this.simulationFrame.setVisible(true);
        
        try {
            this.configurationFrame.setIcon(true);
            this.simulationFrame.setIcon(true);
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
            mainGroupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(mainGroupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(mainGroupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(mainGroupLayout.createSequentialGroup()
                            .addComponent(simulationFrame, GroupLayout.DEFAULT_SIZE, 998, Short.MAX_VALUE)
                            .addContainerGap())
                        .addGroup(Alignment.TRAILING, mainGroupLayout.createSequentialGroup()
                            .addComponent(configurationFrame, GroupLayout.DEFAULT_SIZE, 813, Short.MAX_VALUE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(labelsFrame, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                        .addGroup(mainGroupLayout.createSequentialGroup()
                            .addComponent(horusEyeLabel)
                            .addGap(360))))
        );
        mainGroupLayout.setVerticalGroup(
            mainGroupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(mainGroupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(horusEyeLabel)
                    .addGap(12)
                    .addGroup(mainGroupLayout.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(labelsFrame, 0, 0, Short.MAX_VALUE)
                        .addComponent(configurationFrame, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(simulationFrame, GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
                    .addContainerGap())
        );
        this.getContentPane().setLayout(mainGroupLayout);
        
        GroupLayout simulationGroupLayout = new GroupLayout(simulationFrame.getContentPane());
        simulationGroupLayout.setHorizontalGroup(
            simulationGroupLayout.createParallelGroup(Alignment.TRAILING)
                .addComponent(drawingPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
        );
        simulationGroupLayout.setVerticalGroup(
            simulationGroupLayout.createParallelGroup(Alignment.LEADING)
                .addComponent(drawingPanel, GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
        );
        this.simulationFrame.getContentPane().setLayout(simulationGroupLayout);
        
        GroupLayout configurationGroupLayout = new GroupLayout(configurationFrame.getContentPane());
        configurationGroupLayout.setHorizontalGroup(
            configurationGroupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(configurationGroupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(configurationGroupLayout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(configurationGroupLayout.createSequentialGroup()
                            .addComponent(numberOfFixedAgentsLabel, GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                            .addGap(88))
                        .addGroup(configurationGroupLayout.createSequentialGroup()
                            .addGroup(configurationGroupLayout.createParallelGroup(Alignment.LEADING)
                                .addGroup(configurationGroupLayout.createSequentialGroup()
                                    .addGroup(configurationGroupLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(configurationGroupLayout.createSequentialGroup()
                                            .addComponent(numberOfMobileAgentsLabel, GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                                            .addPreferredGap(ComponentPlacement.RELATED))
                                        .addComponent(numberOfMobileSensors))
                                    .addGap(24))
                                .addGroup(configurationGroupLayout.createSequentialGroup()
                                    .addComponent(numberOfFixedSensorsLabel)
                                    .addPreferredGap(ComponentPlacement.RELATED)))
                            .addGroup(configurationGroupLayout.createParallelGroup(Alignment.LEADING, false)
                                .addComponent(numberOfMobileAgentsTextField, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
                                .addGroup(configurationGroupLayout.createParallelGroup(Alignment.LEADING, false)
                                    .addComponent(numberOfFixedSensorsTextField, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                                    .addComponent(numberOfFixedAgentsTextField, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                                    .addComponent(numberOfMobileSensorsTextField, 0, 0, Short.MAX_VALUE)))
                            .addGap(108)))
                    .addGap(73)
                    .addGroup(configurationGroupLayout.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(startSimulationButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(stopSimulationButton, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE))
                    .addGap(80))
        );
        configurationGroupLayout.setVerticalGroup(
            configurationGroupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(configurationGroupLayout.createSequentialGroup()
                    .addGap(16)
                    .addGroup(configurationGroupLayout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(configurationGroupLayout.createSequentialGroup()
                            .addComponent(startSimulationButton)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(stopSimulationButton))
                        .addGroup(configurationGroupLayout.createSequentialGroup()
                            .addGroup(configurationGroupLayout.createParallelGroup(Alignment.BASELINE)
                                .addComponent(numberOfFixedAgentsLabel)
                                .addComponent(numberOfFixedAgentsTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(10)
                            .addGroup(configurationGroupLayout.createParallelGroup(Alignment.BASELINE)
                                .addComponent(numberOfMobileAgentsLabel)
                                .addComponent(numberOfMobileAgentsTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(configurationGroupLayout.createParallelGroup(Alignment.LEADING)
                                .addComponent(numberOfFixedSensorsTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(numberOfFixedSensorsLabel))))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(configurationGroupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(numberOfMobileSensors)
                        .addComponent(numberOfMobileSensorsTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        this.configurationFrame.getContentPane().setLayout(configurationGroupLayout);
        
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
    }
    
    public void startSimulation() {
        this.drawingPanel.startSimulation();
    }
    
    public void stopSimulation() {
        this.drawingPanel.stopSimulation();
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