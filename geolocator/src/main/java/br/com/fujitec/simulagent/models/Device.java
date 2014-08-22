package br.com.fujitec.simulagent.models;

import java.awt.Graphics;

import br.com.fujitec.location.facade.IGeoPosition;
import br.com.fujitec.location.geoengine.GeoPosition;
import br.com.fujitec.simulagent.interfaces.IActionTick;
import br.com.fujitec.simulagent.strategies.DrawStrategy;
import br.com.fujitec.simulagent.strategies.MovementStrategy;

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
    protected DrawStrategy drawStrategy;

    /**
     * @param agentID
     * @param mobility
     */
    public Device(final Integer agentID, final Mobility mobility) {
        this.id = agentID;
        this.mobility = mobility;
        this.movementStrategy = MovementStrategy.createMovementStrategy(this.mobility);
        this.drawStrategy = DrawStrategy.createDrawStrategy(this.getClass());
        this.path = new Path();
    }

    /**
     * <p>
     *     Uses a MovementStrategy to move the device
     * </p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    @Override
    public void move() {
        this.movementStrategy.move(this);
    }
    
    /**
     * <p>
     *      Uses a DrawStrategy to draw the device<br/>
     *      Labels: Fixed Agents = ORANGE<br/>
     *              Mobile Agents = BLUE<br/>
     *              Fixed Sensors = BLACK<br/>
     *              Mobile Sensors = RED
     * </p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    @Override
    public void draw(Graphics graphics) {
       this.drawStrategy.draw(this, graphics);
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

    /**
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    public boolean isMobile() {
        return this.mobility.equals(Mobility.MOBILE);
    }
}
