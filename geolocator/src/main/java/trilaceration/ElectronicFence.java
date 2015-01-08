package trilaceration;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import br.com.fujitec.location.facade.IGeoPosition;
import br.com.fujitec.simulagent.ui.SimulationController;

public class ElectronicFence {
	 int[]             xv;
     int[]             yv;
     int         vertices;
     ScaleConverter scale = SimulationController.getScaleInstance();
     
     public static final int MAX_VERTICES = 1000;
     
     public ElectronicFence(ScaleConverter scale) {
		super();
		vertices = 0;
		xv = new int[MAX_VERTICES];
		yv = new int[MAX_VERTICES];
		this.scale = scale;
		   
	}
    /**
     * 
     * @param p
     */
    public void addPoint(IGeoPosition p){
    	if( (p != null)&&( vertices < MAX_VERTICES)){
            xv[vertices] = (int)scale.convertToX(p);
            yv[vertices] = (int)scale.convertToY(p);
            vertices++;
    		
    	}
    }
    /**
     * 
     * @param p
     * @return
     */
    public boolean isInsideFence(IGeoPosition p){
    	BufferedImage   image;
    	Graphics            g;
    	int       xp,yp,cp,cc;
    	
    	image = new BufferedImage(scale.getWidth(),scale.getHeight(),java.awt.image.BufferedImage.TYPE_INT_RGB);
        // desenha o poligono
    	g = image.getGraphics();
    	g.setColor(Color.BLUE);
    	g.fillPolygon(xv, yv, vertices);
    	// calcula na escala as coordenadas do ponto
        xp = (int)scale.convertToX(p);
        yp = (int)scale.convertToY(p);
        // verifica se o ponto nas coordenadas é azul, isto e, esta dentro da cerca
        if( (xp <scale.getWidth())&&(yp < scale.getHeight())){
        	cp = image.getRGB(xp, yp);
        	cc = Color.BLUE.getRGB();
        	if( cp == cc){
        		return true;
        	}
        }
    	return false;
    }
    /**
     * 
     * @param image
     */
    public void drawFenceArea(BufferedImage image){
    	Graphics g;
    	
    	g = image.getGraphics();
    	g.fillPolygon(xv, yv, vertices);
    	
    }

}
