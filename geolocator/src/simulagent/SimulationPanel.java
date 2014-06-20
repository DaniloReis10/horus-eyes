package simulagent;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class SimulationPanel extends JPanel {
    
    
    private static final long serialVersionUID = -5119927680094692002L;

    private SimulationFrame simulationFrame;
    
    private final int ANIMATION_DELAY = 10; // retardo em milissegundos
    private int width; // largura da imagem
    private int height; // altura da imagem
    private Timer animationTimer; // O timer guia a anima����o

    public SimulationPanel(int width, int height, SimulationFrame simulationFrame) {
        this.width = width;
        this.height = height;
        this.simulationFrame = simulationFrame;
    }
    
    /**
     * Desenha o componente acrescentando as chamadas da anima��ao
     */
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        ModelEnviroment.getInstance().move();
        ModelEnviroment.getInstance().draw(graphics);
    }

    public void startSimulation() {
        this.createDevices();
        this.startAnimation();
    }
    
    public void testAgentRoute() {
        ModelEnviroment.getInstance().createTraceableAgent();
        this.startAnimation();
    }
    
    /**
     * <p></p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    private void createDevices() {
        final short numberOfFixedAgents = this.simulationFrame.getNumberOfFixedAgents();
        final short numberOfMobileAgents = this.simulationFrame.getNumberOfMobileAgents();
        
        final short numberOfFixedSensors = this.simulationFrame.getNumberOfFixedSensors();
        final short numberOfMobileSensors = this.simulationFrame.getNumberOfMobileSensors();
        
        ModelEnviroment.getInstance().createDevices(numberOfFixedAgents, numberOfFixedSensors, numberOfMobileAgents, numberOfMobileSensors);
    }

    public void stopSimulation() {
        ModelEnviroment.getInstance().deleteAllDevices();
        this.stopAnimation();
        this.repaint();
    }
    
    public void startAnimation() {
        if (animationTimer == null) {
            animationTimer = new Timer(ANIMATION_DELAY, new TimerHandler());

            animationTimer.start(); // inicia o timer
        } else {// animationTimer j�� existe, reinicia anima����o
            if (!animationTimer.isRunning())
                animationTimer.restart();
        }
    }

    /**
     * para o timer de anima����o
     */
    public void stopAnimation() {
        animationTimer.stop();
    }

    /**
     * retorna o tamanho m��nimo de anima����o
     */
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    /**
     * retorna tamanho preferido da anima����o
     */
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    /**
     * classe interna para tratar eventos de a����o do Timer chamada a cada 50 ms
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