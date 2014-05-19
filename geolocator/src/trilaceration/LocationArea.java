package trilaceration;
import javax.swing.*;

import java.awt.*;
import java.awt.image.*;
import java.util.*;
import location.geoengine.GeoPosition;

public class LocationArea {
	private double       lati,longi;// coordenadas do canto superior esquerdo da area
	private double       latf,longf;// coordenadas do canto inferior direito da area
	private int        width,height;// largura e altura da area em analise
	private double    deltaX,deltaY;// Variacao de pixels no eixo X e Y
	private int              status;// flag para indicar se o elemento a ser localizado ja foi detectado por algum sensor
	private int               xc,yc;// Centro da area em torno da posicao estimada do dispositivo
	private int               ratio;// Raio em torno da possicao estimada
	private BufferedImage       img;// Imagem  da area provavel
	private ArrayList<Point>  shape;// Forma da intersec��ao

	public static final int NO_POSITION = 0; 
	public static final int DETECTED    = 1; 
	public static final int SX[]={ 0, 1, 1, 1, 0,-1,-1,-1};
	public static final int SY[]={-1,-1, 0, 1, 1, 1, 0,-1};
	
	/**
	 * Classe respons��vel por calculo da trilecera��ao em uma ��rea de estudo
	 * @param lati  latitude do canto inferior esquerdo da ��rea em estudo.
	 * @param longi longitude do canto inferior esquerdo da ��rea em estudo.
	 * @param latf latitude do canto superior direito da ��rea em estudo.
	 * @param longf longitude do canto superior direito da ��rea em estudo.
	 * @param width largura em pixeis da imagem da ��rea em estudo.
	 * @param height altura em pixeis da imagem da ��rea em estudo.
	 */
	public LocationArea(double lati,double longi,double latf,double longf,int width,int height) {
		super();
		// Verifica se paramentros sao validos
		if( (lati < latf)&&(longi < longf) &&(width > 0) &&(height > 0)){
			// Seta parametros
			this.lati   = lati;
			this.longi  = longi;
			this.latf   = latf;
			this.longf  = longf;
			this.width  = width;
			this.height = height;
			img = new BufferedImage(width,height,java.awt.image.BufferedImage.TYPE_INT_RGB);
			Graphics g = img.createGraphics();
			g.setColor( Color.WHITE );
			g.fillRect( 0, 0, width, height );
			//g.setColor( Color.BLACK );
			//g.drawLine( 0, 0, width, height );
			deltaY = (latf - lati)/height;
			deltaX = (longf - longi)/width;
			status = NO_POSITION;
		}
		
	}
	/**
	 * Desenha um circulo preenchido com centro x,y e raio (ratio) na miagem image.
	 * @param x coordenadas do centro do circulo.
	 * @param y coordenadas do centro do circulo.
	 * @param ratio raio do circulo
	 * @param color cor do circulo
	 * @param image imagem onde o circulo sera desenhado
	 */
	private void drawCicle(int x,int y, int ratio,Color color,BufferedImage image){
		Graphics g = image.createGraphics();
		g.setColor( color );
		// desenha o circulo
		g.fillOval(x - ratio, y - ratio,2*ratio, 2*ratio);
	}
	/**
	 * Limpa imagem
	 * @param image imagem a ser limpa
	 */
	private void clearImage(BufferedImage image){
		Graphics g = image.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.width, this.height);
		
	}
	/**
	 * Gera uma imagem da interseccao das duas areas sendo uma definida pela posicao do sensor e da intersecao anterior
	 * @param x coordenadas do centro do circulo
	 * @param y coordenadas do centro do circulo
	 * @param ratio raio do circulo
	 * @param color1 cor do circulo 1
	 * @param color2 cor do circulo 2
	 * @param img1 imagem onde se encontra o circulo 1
	 * @param img2 imagem onde se encontra o circulo 2
	 * @return imagem com a interseccao
	 */
	private BufferedImage getIntersectionImage(int x,int y, int ratio,Color color1,Color color2,BufferedImage img1,BufferedImage img2){
		int              i,j;
		BufferedImage result;
		int      c1,c2,c3,c4;
		Point          point;
		int        xmin,ymin;
		int        xmax,ymax;
	
		if( hasIntersection(x, y, ratio, color1, color2, img1, img2)){
			result = new BufferedImage(width,height,java.awt.image.BufferedImage.TYPE_INT_RGB);
			shape  = new ArrayList<Point>();
			
			clearImage(result);
			c3 = color1.getRGB();
			c4 = color2.getRGB();
			xmin =((x-ratio) > 0)?(x - ratio) : 0;
			ymin =((y-ratio) > 0)?(y - ratio) : 0;
			xmax =((x+ratio+1)>(width -1))?(width - 1):(x + ratio + 1);
			ymax =((y+ratio+1)>(height -1))?(height - 1):(y + ratio + 1);
			// Percorre a area em torno do circulo da imagem 1
			for (i =xmin;i < xmax;i++){
				for( j=ymin; j <ymax; j++){
					c1 = img1.getRGB(i, j);
					c2 = img2.getRGB(i, j);
					//System.out.printf("%d %d\n",c1,c2);
					// verifica se o pixel da imagem 1 e na imagem 2 tem o pixel na cor pintada
					if( (c1 == c3) && (c2 == c4) ){
						result.setRGB(i, j, Color.BLUE.getRGB());
						point = new Point( i,j );
						shape.add(point);
					}
				}
			}
			return result;
		}
		else
			return this.img;
	}
	/**
	 * Verifica se existe intersec��ao entre as ��reas
	 * @param x coordenadas do centro do circulo
	 * @param y coordenadas do centro do circulo
	 * @param ratio raio do circulo
	 * @param color1 cor do circulo 1
	 * @param color2 cor do circulo 2
	 * @param img1 imagem do circulo 1
	 * @param img2 imagem do circulo 2
	 * @return true existe area de intersec��ao
	 * @return false n��o existe ��rea de intersec����o
	 */
	private boolean hasIntersection(int x,int y, int ratio,Color color1,Color color2,BufferedImage img1,BufferedImage img2){
		int              i,j;
		int      c1,c2,c3,c4;
		int        xmin,ymin;
		int        xmax,ymax;
	
		
		c3 = color1.getRGB();
		c4 = color2.getRGB();
		xmin =((x-ratio) > 0)?(x - ratio) : 0;
		ymin =((y-ratio) > 0)?(y - ratio) : 0;
		xmax =((x+ratio+1)>(width -1))?(width - 1):(x + ratio + 1);
		ymax =((y+ratio+1)>(height -1))?(height - 1):(y + ratio + 1);
		// Percorre a area em torno do circulo da imagem 1
		for (i =xmin;i < xmax;i++){
			for( j=ymin; j <ymax; j++){
				c1 = img1.getRGB(i, j);
				c2 = img2.getRGB(i, j);
				// verifica se o pixel da imagem 1 e na imagem 2 tem o pixel na cor pintada
				if( (c1 == c3) && (c2 == c4) ){
					return true;
				}
			}
		}
		return false;
	}	
	/**
	 * Adiciona a detec��ao de sensor ao objeto
	 * @param sensor objeto associdado ao sensor
	 * @param ratio raio em metros
	 */
	public void addSensorDetection(SensorPosition sensor,double ratio){
		int               xs,ys;
		int                  rs;
		Double               db;
		double           dratio;
		BufferedImage      img1;
		// Verifica se o sensor estar dentro da area
		if( (sensor.getLatitude() >= lati)&&(sensor.getLatitude() <= latf)&&
			(sensor.getLongitude() >= longi)&&(sensor.getLongitude()<=longf)){
		
			// Calcula coordenadas dentro da imagem
			db = new Double( (sensor.getLatitude() - lati)/deltaY);
			ys = height - db.intValue();
			db = new Double( (sensor.getLongitude() - longi)/deltaX);
			xs = db.intValue();
			// Raio convertido para graus
			dratio = ScaleConverter.convertToGrades(ratio, sensor.getLatitude(), sensor.getLongitude());
			// converte o raio em pixeis
			db = new Double( (dratio /(latf - lati))*height);
			rs = db.intValue();
			// Verifica se parametros sao validos
			if( (rs >0)&&(xs >=0)&&(ys>=0)){
				if( status == LocationArea.NO_POSITION){
					clearImage(this.img);
					// Primeira vez que esta sendo detectado a area possivel e  a mesma da estacao
					drawCicle(xs,ys,rs,Color.BLUE,this.img);
					xc    = xs;
					yc    = ys;
					ratio = rs;
					status = LocationArea.DETECTED;
					
				}
				else{
					img1 = new BufferedImage(width,height,java.awt.image.BufferedImage.TYPE_INT_RGB);
					drawCicle(xs,ys,rs,Color.BLUE,img1);
					// Calcula a imagem interseccao entre as duas imagens
					img = getIntersectionImage(xs,ys, rs,Color.BLUE,Color.BLUE,img1,this.img);
				}
			}
		}
		
	}
	/**
	 * Desenha o centroide da ��rea de intersec����o
	 */
	public void showCentroide(){
		// calcula o centroide da interseccao
		calculateCentroid();
		// calcula o raio que centrado no centroide engloba todos os pontos
		calculateRatioCentroid();
		drawCentroide(Color.RED);
		
	}
	/**
	 * Retorna o objeto centroide da imagem com a intersec����oi
	 * @return
	 */
	public Centroide getCentroide(){
		Centroide  centroide;
		// calcula o centroide da interseccao
		calculateCentroid();
		// calcula o raio que centrado no centroide engloba todos os pontos
		calculateRatioCentroid();
		centroide = new Centroide(ScaleConverter.convertToLatitude(xc),
				                  ScaleConverter.convertToLongitude(yc),
				                  ratio);
		return centroide;
		
	}
	/**
	 * Retorna a imagem com a intersec����o		
	 * @return
	 */
	public ImageIcon getImagem() {
		
		BufferedImage buffer = img;
		return new ImageIcon( buffer );
    }
	/**
	 * Calcula o centroide da ��rea de intersec����o
	 * @return
	 */
	private Point calculateCentroid(){
		int                    n;
		Iterator<Point> iterator;
		Point              point;

		xc = yc = n = 0;
		iterator = shape.iterator();		
		while(iterator.hasNext()){
			point = iterator.next();
			xc = xc +point.getX();
			yc = yc + point.getY();
			n++;
		}
		xc = xc / n;
		yc = yc / n;
		point = new Point(xc,yc);
		return point;
		
	}
	/**
	 * Calcula o raio do circulo da area que contem toda area de intersecao.
	 * @return raio em pixeis
	 */
	private long calculateRatioCentroid(){
		Iterator<Point> iterator;
		Point              point;
		double          distance;

		ratio = 0;
		iterator = shape.iterator();		
		while(iterator.hasNext()){
			point = iterator.next();
			distance = Math.sqrt( Math.pow((point.getX()-xc),2.0) + Math.pow(((point.getY() - yc)),2.0));
			if (distance > ratio){
				ratio = (int)Math.round(distance);
			}
		}
		return ratio;
	}
	/**
	 * Desenha o centroide na imagem
	 * @param color
	 */
	public void drawCentroide(Color color){
		Graphics g=img.getGraphics();
		
		g.setColor(color);
		g.drawLine(xc - 2, yc, xc + 2, yc);
		g.drawLine(xc, yc - 2, xc, yc + 2);
		g.drawOval(xc -ratio, yc-ratio, 2*ratio, 2*ratio);
	}
	/**
	 * @param args
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
	    BufferedImage   image;

		LocationArea   area   = new LocationArea (-3,-5,-1,-1,400,200);
	    SensorPosition sensor1 = new SensorPosition(-2,-3,0.5);
	    SensorPosition sensor2 = new SensorPosition(-2.5,-3,0.5);
	    SensorPosition sensor3 = new SensorPosition(-2.0,-2.5,0.5);
	    area.addSensorDetection(sensor1, 1);
	    area.addSensorDetection(sensor2, 1);
	    area.addSensorDetection(sensor3, 1);
	    area.showCentroide();
	    /**
		image = new BufferedImage(400,400,java.awt.image.BufferedImage.TYPE_INT_RGB);
	    
		ScaleConverter.latIni = 0.0;
	    ScaleConverter.longIni = 0.0;
	    ScaleConverter.latEnd = 4.0;
	    ScaleConverter.longEnd = 4.0;
	    ScaleConverter.height = 400;
	    ScaleConverter.width  = 400;
	    
	    ElectronicFence fence = new ElectronicFence();
	    
	    GeoPosition   p1,p2,p3,p4,p5;
	    
	    p1 = new GeoPosition(new Date(),0.3,0.2);
	    p2 = new GeoPosition(new Date(),0.3,1.3);
	    p3 = new GeoPosition(new Date(),2.3,1.3);
	    p4 = new GeoPosition(new Date(),2.3,0.2);
	   // p5 = new GeoPosition(new Date(),0.8,1.2);	    
	    
	    fence.addPoint(p1);
	    fence.addPoint(p2);
	    fence.addPoint(p3);
	    fence.addPoint(p4);
	    //fence.addPoint(p5);
	    
	    p1 = new GeoPosition(new Date(),0.1,0.1);
	    
	    boolean result = fence.isInsideFence(p1);
	    System.out.print(result);
	    fence.drawFenceArea(image);
		*/
		// TODO Auto-generated method stub
        JFrame frm = new JFrame("Teste Imagem");
        JPanel pan = new JPanel();
        JLabel lbl = new JLabel( area.getImagem() );
        //JLabel lbl = new JLabel( new ImageIcon( image ));
        pan.add( lbl );
        frm.getContentPane().add( pan );
        frm.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frm.pack();
        frm.show();		

	}

}
