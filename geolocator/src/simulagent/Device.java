package simulagent;

import java.awt.Color;
import java.awt.Graphics;

import trilaceration.ScaleConverter;
import location.facade.IGeoPosition;
import location.geoengine.GeoPosition;

/**
 * @author tiagoportela <tiagoporteladesouza@gmail.com>
 *
 */
public abstract class Device implements IActionTick{
    protected Integer id;
    protected Mobility mobility;
    protected IGeoPosition currentPosition;
    protected int currentTime;
    protected Path path;
    protected MovementStrategy movementStrategy;
    
    /**
     * @param agentID
     * @param mobility
     */
    public Device(final Integer agentID, final Mobility mobility) {
        this.id = agentID;
        this.mobility = mobility;
        this.movementStrategy = MovementStrategy.createMovementStrategy(this.mobility);
        this.path = new Path();
    }

    /**
     * Interface de animaçao do agente
     */
    @Override
    public void move() {
        this.movementStrategy.move(this);
    }
    
    /**
     * <p>
     *      Draws a rect representing agents and sensors.<br/>
     *      Labels: Fixed Agents = ORANGE<br/>
     *              Mobile Agents = BLUE<br/>
     *              Fixed Sensors = BLACK<br/>
     *              Mobile Sensors = RED
     *              
     * </p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    @Override
    public void draw(Graphics graphics) {
        Color color = null;
        
        if (this instanceof Agent) {
            if (mobility.equals(Mobility.FIXED)) {
                color = Color.ORANGE;
            } else if (mobility.equals(Mobility.MOBILE)) {
                color = Color.BLUE;
            }
        } else if (this instanceof Sensor) {
            if (mobility.equals(Mobility.FIXED)) {
                color = Color.BLACK;
            } else if (mobility.equals(Mobility.MOBILE)) {
                color = Color.RED;
            }
            
            final int diameter = Sensor.RADIUS * 2;
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.fillOval(ScaleConverter.convertToX(currentPosition) - 5, ScaleConverter.convertToY(currentPosition) - 5, diameter, diameter);
        }
        
        graphics.setColor(color);
        graphics.fillRect(ScaleConverter.convertToX(currentPosition), ScaleConverter.convertToY(currentPosition), 10, 10);
        
        graphics.setColor(Color.BLACK);
        graphics.drawString(String.valueOf(this.id), ScaleConverter.convertToX(currentPosition), ScaleConverter.convertToY(currentPosition));
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Mobility getMobility() {
        return mobility;
    }

    public void setMobility(Mobility mobility) {
        this.mobility = mobility;
    }

    public IGeoPosition getCurrentPosition() {
        return currentPosition;
    }
    
    public void setCurrentPosition(IGeoPosition currentPosition) {
        if (currentPosition != null) {
            this.currentPosition = (GeoPosition) currentPosition;
        }
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    /**
     * @return the path
     */
    public Path getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(Path path) {
        this.path = path;
    }

    /**
     * <p>Increases current time</p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    public void increaseCurrentTime() {
        this.currentTime++;
    }

    /**
     * @return the movementStrategy
     */
    public MovementStrategy getMovementStrategy() {
        return movementStrategy;
    }

    /**
     * @param movementStrategy the movementStrategy to set
     */
    public void setMovementStrategy(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }
}
