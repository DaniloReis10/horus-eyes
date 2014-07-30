package trilaceration;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import br.com.fujitec.location.facade.IGeoPosition;

public class ElectronicFence {
	 int[]     xv;
     int[]     yv;
     int vertices;
     
     public static final int MAX_VERTICES = 1000;
     
     public ElectronicFence() {
		super();
		vertices = 0;
		xv = new int[MAX_VERTICES];
		yv = new int[MAX_VERTICES];
		   
	}
    /**
     * 
     * @param p
     */
    public void addPoint(IGeoPosition p){
    	if( (p != null)&&( vertices < MAX_VERTICES)){
            xv[vertices] = (int)ScaleConverter.convertToX(p);
            yv[vertices] = (int)ScaleConverter.convertToY(p);
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
    	
    	image = new BufferedImage(ScaleConverter.width,ScaleConverter.height,java.awt.image.BufferedImage.TYPE_INT_RGB);
        // desenha o poligono
    	g = image.getGraphics();
    	g.setColor(Color.BLUE);
    	g.fillPolygon(xv, yv, vertices);
    	// calcula na escala as coordenadas do ponto
        xp = (int)ScaleConverter.convertToX(p);
        yp = (int)ScaleConverter.convertToY(p);
        // verifica se o ponto nas coordenadas é azul, isto e, esta dentro da cerca
        if( (xp <ScaleConverter.width)&&(yp < ScaleConverter.height)){
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
