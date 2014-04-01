package simulagent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class SimulationPanel extends JPanel {
    
    
    private static final long serialVersionUID = -5119927680094692002L;

    private SimulationFrame simulationFrame;
    
    private final int ANIMATION_DELAY = 50; // retardo em milissegundos
    private int width; // largura da imagem
    private int height; // altura da imagem
    private Timer animationTimer; // O timer guia a animação

    public SimulationPanel(int width, int height, SimulationFrame simulationFrame) {
        this.width = width;
        this.height = height;
        this.simulationFrame = simulationFrame;
    }
    
    /**
     * Desenha o componente acrescentando as chamadas da animaçao
     */
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        ModelEnviroment.getInstance().drawImage(graphics, Color.BLUE);
        ModelEnviroment.getInstance().moveImage();
    }

    public void startSimulation() {
        final short numberOfDevices = this.simulationFrame.getNumberOfDevices();
        final short numberOfSensors = this.simulationFrame.getNumberOfSensors();
        final short numberOfFixedDevices = this.simulationFrame.getNumberOfFixedDevices();
        final short numberOfMobileDevices = this.simulationFrame.getNumberOfMobileDevices();
        
        ModelEnviroment.getInstance().createMobileAgentsWithRandomPosition(numberOfMobileDevices);
        ModelEnviroment.getInstance().createStaticAgents(numberOfFixedDevices);
        
        this.startAnimation();
    }
    
    public void stopSimulation() {
        ModelEnviroment.getInstance().deleteAllAgents();
        this.stopAnimation();
    }
    
    public void startAnimation() {
        if (animationTimer == null) {
            animationTimer = new Timer(ANIMATION_DELAY, new TimerHandler());

            animationTimer.start(); // inicia o timer
        } else {// animationTimer já existe, reinicia animação
            if (!animationTimer.isRunning())
                animationTimer.restart();
        }
    }

    /**
     * para o timer de animação
     */
    public void stopAnimation() {
        animationTimer.stop();
    }

    /**
     * retorna o tamanho mínimo de animação
     */
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    /**
     * retorna tamanho preferido da animação
     */
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    /**
     * classe interna para tratar eventos de ação do Timer chamada a cada 50 ms
     * 
     * @author DaniloReis
     * 
     */
    private class TimerHandler implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            repaint();
        }
    }

}