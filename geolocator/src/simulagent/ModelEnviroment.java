package simulagent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import location.geoengine.GeoPosition;
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
    PathFactory factory;                            // Fabrica de agentes
    ArrayList<RFAgent> agents;                      // lista de agentes criados
    private int lastAgentId = 0;

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
        this.factory = new PathFactory();
        this.agents = new ArrayList<RFAgent>();
    }

    /**
     * Cria agentes moveis no ambiente
     * 
     * @param n
     *            Numero de agentes a serem criados
     * @param nPositions
     *            - numero de posicoes estaticas que o agente terá na sua rota
     *            diária.
     */
    public void createMobileAgents(int n, int nPositions) {
        for (int i = 0; i < n; i++) {
            final AgentPath path = factory.create(ScaleConverter.width, ScaleConverter.height, nPositions);
            final MobileAgent agent = new MobileAgent(generateAgentId());
            agent.setPath(path);
            agent.setCurrentPosition(agent.getPath().getPositionAtTime(0));
            this.agents.add(agent);
        }
    }

    /**
     * Cria agentes fixos no ambiente
     * 
     * @param n
     *            numero de agentes a serem criados
     */
    public void createStaticAgents(int n) {
        for (int i = 0; i < n; i++) {
            final RFAgent agent = new RFAgent(this.generateAgentId());

            final double latitude = ScaleConverter.latIni + Math.random() * (ScaleConverter.latEnd - ScaleConverter.latIni);
            final double longitude = ScaleConverter.longIni + Math.random() * (ScaleConverter.longEnd - ScaleConverter.longIni);
            final GeoPosition position = new GeoPosition(latitude, longitude);
            
            agent.setCurrentPosition(position);
            this.agents.add(agent);
        }
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
        ScaleConverter.width = width;
        ScaleConverter.height = height;
        ScaleConverter.latIni = latitude0;
        ScaleConverter.longIni = longitude0;
        ScaleConverter.latEnd = latitude1;
        ScaleConverter.longEnd = longitude1;
    }

    @Override
    public void moveImage() {
        RFAgent agent;
        Iterator<RFAgent> iterator;

        // Percorre todos os agentes do ambiente movendo no modelo cada um.
        iterator = this.agents.iterator();
        while (iterator.hasNext()) {
            agent = iterator.next();
            agent.moveImage();
        }

    }

    @Override
    public void clearImage(BufferedImage image) {
        RFAgent agent;
        Iterator<RFAgent> iterator;
        // Percorre todos os agentes do ambiente apagando cada um.
        iterator = this.agents.iterator();
        while (iterator.hasNext()) {
            agent = iterator.next();
            agent.clearImage(image);
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
    public void drawImage(Graphics graphics, Color color) {
        RFAgent agent;
        Iterator<RFAgent> iterator;

        // Percorre todos os agentes do ambiente desenhando cada um.
        iterator = this.agents.iterator();
        while (iterator.hasNext()) {
            agent = iterator.next();
            agent.drawImage(graphics, color);
        }
    }

    /**
     * <p>This method creates mobile agents with random generated positions</p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    public void createMobileAgentsWithRandomPosition(int numberOfAgents) {
        final Random randomNumberGenerator = new Random();
        
        for (int i = 0; i < numberOfAgents; i++) {
            final short numberOfPositions = (short) (randomNumberGenerator.nextInt(10) + 1);
            final AgentPath path = this.factory.create(ScaleConverter.width, ScaleConverter.height, numberOfPositions);
            final MobileAgent agent = new MobileAgent(generateAgentId()); 
            agent.setPath(path);
            agent.setCurrentPosition(agent.getPath().getFirstPosition());
            this.agents.add(agent);
        }
    }
    
    /**
     * <p>This methods increments the agent ID number and returns the incremented ID.</p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    private Integer generateAgentId() {
        ModelEnviroment.instance.lastAgentId++;
        return new Integer(lastAgentId);
    }
}