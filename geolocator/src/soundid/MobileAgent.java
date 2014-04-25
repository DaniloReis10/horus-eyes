package simulagent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import location.geoengine.GeoPosition;
import trilaceration.ScaleConverter;

/**
 * Classe do agente que possui um interface de rede sem fio
 * 
 * @author DaniloReis
 * 
 */
public class MobileAgent extends RFAgent {
    private AgentPath path;// Rota do agente no dia

    /**
     * Construtor generico
     * 
     */
    public MobileAgent() {
        this.path = new AgentPath();
        this.mobility = AgentMobility.MOBILE;
    }
    
    /**
     * Construtor com inicialização do ID
     * @param integer 
     */
    public MobileAgent(Integer agentID) {
        this.path = new AgentPath();
        this.mobility = AgentMobility.MOBILE;
        this.id = agentID;
    }

    // Metodos set e gets
    public AgentPath getPath() {
        return path;
    }

    public void setPath(AgentPath path) {
        this.path = path;
    }

    public AgentMobility getMobility() {
        return mobility;
    }

    public void setMobility(AgentMobility mobility) {
        this.mobility = mobility;
    }

    @Override
    public void clearImage(BufferedImage image) {
        Graphics g;

        g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillOval(ScaleConverter.convertToX(currentPosition) - 5,
                ScaleConverter.convertToY(currentPosition) - 5, 10, 10);
    }

//    @Override
//    public void drawImage(BufferedImage image, Color color) {
//        Graphics g;
//
//        g = image.getGraphics();
//        g.setColor(Color.MAGENTA);
//        g.fillOval(ScaleConverter.convertToX(currentPosition) - 5,
//                ScaleConverter.convertToY(currentPosition) - 5, 10, 10);
//    }

    /**
     * <p>Draws a filled circle representing mobile agents</p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    @Override
    public void drawImage(Graphics graphics, Color color) {
        graphics.setColor(color);
        graphics.fillOval(ScaleConverter.convertToX(currentPosition) - 5, ScaleConverter.convertToY(currentPosition) - 5, 10, 10);
    }
    
    @Override
    public void moveImage() {
        currentTime = (currentTime) % PathFactory.TICKS_DAY;
        currentPosition = (GeoPosition) path.getPositionAtTime(currentTime);
        currentTime++;
    }

}
