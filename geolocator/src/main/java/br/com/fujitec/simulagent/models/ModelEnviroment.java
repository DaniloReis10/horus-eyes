package br.com.fujitec.simulagent.models;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import br.com.fujitec.location.facade.GeopositionFacade;
import br.com.fujitec.simulagent.factories.DeviceFactory;
import br.com.fujitec.simulagent.factories.PathFactory;
import br.com.fujitec.simulagent.interfaces.IActionTick;
import br.com.fujitec.simulagent.ui.SimulationController;
import trilaceration.ScaleConverter;

/**
 * Classe responsável por criar o modelo do ambiente dos agentes sensores ,fixos
 * e moveis. Nesta classe sao criados os agentes e implementados os metodos de
 * animacao dos mesmos. Nesta classe tambem é criada uma escala do ambiente para
 * conversao entre latitude e longitude e linhas e colunas na imagem. esta
 * classe segue o padrão de projeto singleton, sendo tambem um Model
 * 
 * @author DaniloReis
 * 
 */
public class ModelEnviroment implements IActionTick {

    private static ModelEnviroment instance;        // Instancia do modelo
    private List<Device> devices;                      // lista de agentes criados
//    private List<Agent> mobileAgents;                      // lista de agentes criados
//    private List<Sensor> sensors;                      // lista de agentes criados
    private int deviceIdController = 0;
    private ScaleConverter scale= SimulationController.getScaleInstance();
  

    /**
     * Retorna a instancia do modelo (Padrão de projeto singleton)
     * 
     * @return
     */
    public static ModelEnviroment getInstance() {
        if (instance == null) {
            instance = new ModelEnviroment();
        }
        return instance;
    }

    /**
     * Construtor generico
     */
    public ModelEnviroment() {
        super();
        this.devices = new ArrayList<Device>();
        //this.mobileAgents = new ArrayList<Agent>();
        //this.sensors= new ArrayList<Sensor>();
    }

    /**
     * <p>
     *  Creates all the devices used in simulation
     * </p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    public void createDevices(short numberOfFixedAgents, short numberOfFixedSensors, short numberOfMobileAgents, short numberOfMobileSensors) {
        List<Device> fixedAgents = DeviceFactory.createStaticDevices(Agent.class, numberOfFixedAgents);
        List<Device> mobileAgents = DeviceFactory.createMobileDevices(Agent.class, numberOfMobileAgents);
        List<Device> mobileSensors = DeviceFactory.createMobileDevices(Sensor.class, numberOfMobileSensors);
        List<Device> fixedSensors = DeviceFactory.createStaticDevices(Sensor.class, numberOfFixedSensors);
        
        this.devices.addAll(fixedAgents);
        this.devices.addAll(mobileAgents);
        this.devices.addAll(mobileSensors);
        this.devices.addAll(fixedSensors);
        
        GeopositionFacade.getInstance().addDevicesToTrack(devices);
    }
    
    /**
     * <p>
     *  Clears sensors detected devices info
     * </p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    public void deleteSensorsDetectedDevices() {
        for (Device device : devices) {
            if (device instanceof Sensor) {
                ((Sensor) device).deleteAllDetectedDevices();
            }
        }
    }
    
    /**
     * <p>
     *  Regenerates only mobile agents routes
     * </p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    public void regenerateAgentsRoutes() {
        for (Device device : devices) {
            if (device.isMobile() && device instanceof Agent) {
                final Random randomNumberGenerator = new Random();
                final short numberOfPositions = (short) (randomNumberGenerator.nextInt(10) + 10);
                final Path newPath = PathFactory.createAgentPath(scale.getWidth(), scale.getHeight(), numberOfPositions);
                ((Agent) device).setPath(newPath);
            }
        }
    }
    
    /**
     * <p>
     *  Regenerates all mobile devices routes
     * </p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    public void regenerateDevicesRoutes() {
        for (Device device : devices) {
            if (device.isMobile() && device instanceof Agent) {
                final Random randomNumberGenerator = new Random();
                final short numberOfPositions = (short) (randomNumberGenerator.nextInt(10) + 10);
                final Path newPath;
                
                if (device instanceof Agent) {
                    newPath = PathFactory.createAgentPath(scale.getWidth(), scale.getHeight(), numberOfPositions);
                } else {
                    newPath = PathFactory.createSensorPath(scale.getWidth(), scale.getHeight(), numberOfPositions);
                }
                
                device.setPath(newPath);
            }
        }
    }
    
    /**
     * <p>
     *  Creates one traceable agent
     * </p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    public void createTraceableAgent() {
        this.devices = DeviceFactory.createMobileDevices(TraceableAgent.class, 1);
        GeopositionFacade.getInstance().addDevicesToTrack(devices);
    }
    
    @Override
    public void move() {
        final Iterator<Device> iterator = this.devices.iterator();

        while (iterator.hasNext()) {
            final Device device = iterator.next();
            device.move();
        }
    }

    /**
     * <p></p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    public void draw(Graphics graphics) {
        final Iterator<Device> iterator = this.devices.iterator();
        
        while (iterator.hasNext()) {
            final Device device = iterator.next();
            device.draw(graphics);
        }
    }

    /**
     * <p>This method increments the device ID number and returns the incremented ID.</p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    public Integer generateDeviceId() {
        this.deviceIdController++;
        return new Integer(deviceIdController);
    }
    
    /**
     * <p>This method resets the device ID number.</p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    public void resetDeviceIdController() {
        this.deviceIdController = 0;
    }

    /**
     * <p>This method deletes all static and mobile devices.</p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    public void deleteAllDevices() {
        this.devices.clear();
        this.resetDeviceIdController();
        GeopositionFacade.getInstance().removeDevicesFromTracking();
    }
    
    /**
     * Seta os parametros de escala do ambiente
     * 
     * @param latitude0
     *            latitude do canto superior esquerdo (0,0)
     * @param longitude0
     *            longitude do canto superior direito (0,0)
     * @param latitude1
     *            latitude do canto inferior direito (width,height)
     * @param longitude1
     *            longitude do canto inferior direito (width,height)
     * @param width
     *            largura da imagem
     * @param height
     *            altura da imagem
     */
    public void setScaleEnviroment(double latitude0, double longitude0, double latitude1, double longitude1, int width, int height) {
      
    	scale = new ScaleConverter(latitude0,latitude1,longitude0,longitude1,width,height);
    }
}