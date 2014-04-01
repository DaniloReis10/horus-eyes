package simulagent;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
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

    private JLabel numberOfDevicesLabel;
    private JTextField numberOfDevicesTextField;

    private JLabel numberOfMobileDevicesLabel;
    private JTextField numberOfMobileDevicesTextField;

    private JLabel numberOfSensorsLabel;
    private JTextField numberOfSensorsTextField;

    private JButton startSimulationButton;
    private JButton stopSimulationButton;

    private int width;
    private int height;

    private SimulationPanel drawingPanel;
    private JLabel horusEyeLabel;

    /**
     * 
     */
    public SimulationFrame() {
        this(800, 600);
    }

    /**
     * @param width
     * @param height
     */
    public SimulationFrame(int width, int height) {
        this.width = width;
        this.height = height;

        this.setSize(width, height);

        initializeFormComponents();
        initializeButtons();
        initializeLayout();
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
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(
                groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
                    groupLayout.createSequentialGroup().addGap(363).addComponent(horusEyeLabel)).addGroup(
                        groupLayout.createSequentialGroup().addGap(23).addGroup(
                            groupLayout.createParallelGroup(Alignment.LEADING).addComponent(drawingPanel, GroupLayout.PREFERRED_SIZE, 749, GroupLayout.PREFERRED_SIZE).addGroup(
                                groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                    .addComponent(numberOfDevicesLabel).addComponent(numberOfMobileDevicesLabel).addComponent(numberOfSensorsLabel))
                                        .addPreferredGap(ComponentPlacement.RELATED).addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                            .addComponent(numberOfDevicesTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                               .addGroup(groupLayout.createSequentialGroup().addComponent(numberOfMobileDevicesTextField,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                   .addGap(59).addComponent(startSimulationButton).addGap(43).addComponent(stopSimulationButton))
                                                        .addComponent(numberOfSensorsTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))))
                                                            .addContainerGap(26, Short.MAX_VALUE)));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
                groupLayout.createSequentialGroup().addContainerGap().addComponent(horusEyeLabel).addPreferredGap(ComponentPlacement.RELATED, 24, Short.MAX_VALUE).addGroup(
                    groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(numberOfDevicesLabel).addComponent(numberOfDevicesTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(ComponentPlacement.UNRELATED).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(numberOfMobileDevicesLabel, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
                            .addComponent(numberOfMobileDevicesTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE) .addComponent(startSimulationButton)
                                .addComponent(stopSimulationButton)).addGap(15).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(numberOfSensorsLabel)
                                    .addComponent(numberOfSensorsTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(29)
                                        .addComponent(drawingPanel, GroupLayout.PREFERRED_SIZE, 370, GroupLayout.PREFERRED_SIZE).addGap(27)));
        
        getContentPane().setLayout(groupLayout);
    }

    private void initializeFormComponents() {
        this.horusEyeLabel = new JLabel("Horus Eye");

        this.numberOfDevicesLabel = new JLabel("Number of Devices:");
        this.numberOfDevicesTextField = new JTextField();
        this.numberOfDevicesTextField.setColumns(10);

        this.numberOfMobileDevicesLabel = new JLabel("Number of Mobile Devices:");
        this.numberOfMobileDevicesTextField = new JTextField();
        this.numberOfMobileDevicesTextField.setColumns(10);

        this.numberOfSensorsLabel = new JLabel("Number of Sensors:");
        this.numberOfSensorsTextField = new JTextField();
        this.numberOfSensorsTextField.setColumns(10);

        this.drawingPanel = new SimulationPanel(700, 400, this);
        this.drawingPanel.setBackground(Color.WHITE);
    }

    /**
     * <p>
     * </p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    private void initializeButtons() {
        this.startSimulationButton = new JButton("Start Simulation");
        this.startSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimulationFrame.this.startSimulation();
            }
        });

        this.stopSimulationButton = new JButton("Stop Simulation");
        this.stopSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimulationFrame.this.stopSimulation();;
            }
        });

    }
    
    public void startSimulation() {
        this.drawingPanel.startSimulation();
    }
    
    public void stopSimulation() {
        this.drawingPanel.stopSimulation();
    }

    public short getNumberOfDevices() {
        return this.numberOfDevicesTextField.getText().isEmpty()? 0: Short.parseShort(this.numberOfDevicesTextField.getText());  
    }

    public short getNumberOfMobileDevices() {
        return this.numberOfMobileDevicesTextField.getText().isEmpty()? 0: Short.parseShort(this.numberOfMobileDevicesTextField.getText());
    }

    public short getNumberOfFixedDevices() {
        return (short) (getNumberOfDevices() - getNumberOfMobileDevices() - getNumberOfSensors());
    }

    public short getNumberOfSensors() {
        return this.numberOfSensorsTextField.getText().isEmpty()? 0: Short.parseShort(this.numberOfSensorsTextField.getText());
    }
}