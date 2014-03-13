package simulagent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class DrawAnimation implements IActionTick {
	    private int          width;
        private int         height;
        private int  speedx,speedy;
		int                    x,y;
		int              ratio;
 
        public DrawAnimation(int width, int height) {
  			super();
  			this.width  = width;
  			this.height = height;
  			speedx = speedy = 10;
			x = (int)(width * Math.random());
			y = (int)(height * Math.random());
			ratio = 50;  			
  		}	@Override

  		public void drawImage(BufferedImage image,Color color) {
			Graphics  g;

			g = image.getGraphics();
			g.setColor(color);
 			g.fillOval(x, y, ratio, ratio);
		}

		@Override
		public void moveImage() {
	        x = x + speedx;
	        y = y + speedy;
	        if( x >(width - (ratio/2)-1)){
	          	x = (width - (ratio/2)-1);
	           	speedx = -1*speedx;
	        }
	        if( x < (ratio/2)){
	          	x = (ratio/2);
	           	speedx = -1*speedx;
	        }
	        if( y >(height - (ratio/2)-1)){
	           	y = (height - (ratio/2)-1);
	           	speedy = -1*speedy;
	        }
	        if( y <(ratio/2)){
	           	y = (ratio/2);
	           	speedy = -1*speedy;
	        }
		}

		@Override
		public void clearImage(BufferedImage image) {
			Graphics  g;

			g = image.getGraphics();
			g.setColor(Color.WHITE);
 			g.fillOval(x, y, ratio, ratio);		
		}

}
