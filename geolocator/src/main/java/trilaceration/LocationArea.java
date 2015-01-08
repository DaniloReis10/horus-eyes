package trilaceration;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.fujitec.location.facade.IGeoPosition;
import br.com.fujitec.location.geoengine.GeoPosition;
import br.com.fujitec.simulagent.models.Sensor;
import br.com.fujitec.simulagent.ui.SimulationController;

public class LocationArea {
    private double       lati,longi;// coordenadas do canto superior esquerdo da area
    private double       latf,longf;// coordenadas do canto inferior direito da area
    private int        width,height;// largura e altura da area em analise
//    private double    deltaX,deltaY;// Variacao de pixels no eixo X e Y
    private int              status;// flag para indicar se o elemento a ser localizado ja foi detectado por algum sensor
    private int               xc,yc;// Centro da area em torno da posicao estimada do dispositivo
    private double            ratio;// Raio em torno do centroide da posicao estimada em metros
    private int          ratioPixel;// Raio em torno do centroide da posi��o estimada em pixels
//    private int             ratio_x;// Raio horizontal em pixels
//    private int             ratio_y;// Raio vertical em pixels
    private BufferedImage       img;// Imagem  da area provavel
    private boolean debugMode=false;
	private BufferedImage   imgComb;
    private ArrayList<Point>  shape;// Forma da interseccao
    ScaleConverter            scale = SimulationController.getScaleInstance();
    int                  nSensors=0;
	public static final int NO_POSITION = 0; 
    public static final int DETECTED    = 1; 
    public static final int SX[]={ 0, 1, 1, 1, 0,-1,-1,-1};
    public static final int SY[]={-1,-1, 0, 1, 1, 1, 0,-1};
 
    public static final Color BASE_AREA_COLOR = Color.RED;
    public static final Color COMPARED_AREA_COLOR = Color.YELLOW;
      
    /**
     * Classe responsavel por calculo da trileceracao em uma area de estudo
     * @param lati  latitude do canto inferior esquerdo da area em estudo.
     * @param longi longitude do canto inferior esquerdo da area em estudo.
     * @param latf latitude do canto superior direito da area em estudo.
     * @param longf longitude do canto superior direito da area em estudo.
     * @param width largura em pixeis da imagem da area em estudo.
     * @param height altura em pixeis da imagem da area em estudo.
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
            
            scale = new ScaleConverter(lati,latf,longi,longf,width,height);
            
            img     = new BufferedImage(width,height,java.awt.image.BufferedImage.TYPE_INT_RGB);
            imgComb = new BufferedImage(width,height,java.awt.image.BufferedImage.TYPE_INT_RGB);
            Graphics g = img.getGraphics();
            g.setColor( Color.WHITE );
            g.fillRect( 0, 0, width, height );
            //g.setColor( Color.BLACK );
            //g.drawLine( 0, 0, width, height );
 //           deltaY = (latf - lati)/height;
 //           deltaX = (longf - longi)/width;
            status = NO_POSITION;
        }
    }
    public LocationArea(ScaleConverter scale) {
        super();
        // Verifica se paramentros sao validos
        if( (scale.getLatIni() < scale.getLatEnd())&&(scale.getLongIni() < scale.getLongEnd()) &&(scale.getWidth() > 0) &&(scale.getHeight() > 0)){
           // Seta parametros
           this.lati   = scale.getLatIni();
           this.longi  = scale.getLongIni();
           this.latf   = scale.getLatEnd();
           this.longf  = scale.getLongEnd();
           this.width  = scale.getWidth();
           this.height = scale.getHeight();
              
           this.scale = scale;
                
           img     = new BufferedImage(width,height,java.awt.image.BufferedImage.TYPE_INT_RGB);
           imgComb = new BufferedImage(width,height,java.awt.image.BufferedImage.TYPE_INT_RGB);
           Graphics g = img.getGraphics();
           g.setColor( Color.WHITE );
           g.fillRect( 0, 0, width, height );
//           deltaY = (latf - lati)/height;
//           deltaX = (longf - longi)/width;
           status = NO_POSITION;
        }
    }
	public boolean isDebugMode() {
		return debugMode;
	}
	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}
    public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

    /**
     * Desenha um circulo preenchido com centro x,y e raio (ratio) na miagem image.
     * @param x coordenadas do centro do circulo.
     * @param y coordenadas do centro do circulo.
     * @param ratio raio do circulo
     * @param color cor do circulo
     * @param image imagem onde o circulo sera desenhado
     */
    private void drawCicle(int x,int y, int ratio_x, int ratio_y,Color color,BufferedImage image){
        Graphics g = image.getGraphics();
        g.setColor( color );
        // desenha o circulo
        g.fillOval(x - ratio_x, y - ratio_y,2*ratio_x, 2*ratio_y);
    }
    /**
     * Limpa imagem
     * @param image imagem a ser limpa
     */
    public void clearImage(BufferedImage image){
        Graphics g = image.getGraphics();
        g.setColor(Color.RED);
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
   // @SuppressWarnings("unused")
	private BufferedImage getIntersectionImage(int x,int y, int ratiox,int ratioy,Color color1,Color color2,BufferedImage img1,BufferedImage img2){
        int              i,j;
        BufferedImage result;
        int      c1,c2,c3,c4;
        Point          point;
        int        xmin,ymin;
        int        xmax,ymax;

        shape  = new ArrayList<Point>();
        if( hasIntersection(x, y, ratiox,ratioy, color1, color2, img1, img2)){
            result = new BufferedImage(width,height,java.awt.image.BufferedImage.TYPE_INT_RGB);
            
            clearImage(result);
            c3 = color1.getRGB();
            c4 = color2.getRGB();
            xmin =((x-ratiox - 1) > 0)?(x - ratiox - 1) : 0;
            ymin =((y-ratioy - 1) > 0)?(y - ratioy - 1) : 0;
            xmax =((x+ratiox + 1)>(width -1)) ?(width - 1) :(x + ratiox + 1);
            ymax =((y+ratioy + 1)>(height -1))?(height - 1):(y + ratioy + 1);
            // Percorre a area em torno do circulo da imagem 1
            for (i =xmin;i < xmax;i++){
                for( j=ymin; j <ymax; j++){
                    c1 = img1.getRGB(i, j);
                    c2 = img2.getRGB(i, j);
                    //System.out.printf("%d %d\n",c1,c2);
                    // verifica se o pixel da imagem 1 e na imagem 2 tem o pixel na cor pintada
                    if( (c1 == c3) && (c2 == c4) ){
                        result.setRGB(i, j, Color.BLUE.getRGB());
                        Color intersection;
                        int       modColor;
                        modColor = (nSensors % 6);
                        switch(modColor){
                        case 0:
                        	intersection = Color.GREEN;
                        	break;
                        case 1:
                        	intersection = Color.GRAY;
                        	break;
                        case 2:
                        	intersection = Color.YELLOW;
                        	break;
                        case 3:
                        	intersection = Color.RED;
                        	break;
                        case 4:
                        	intersection = Color.MAGENTA;
                        	break;
                        case 5:
                        	intersection = Color.ORANGE;
                        	break;
                        default:
                        	intersection = Color.GREEN;
                        }

                    	imgComb.setRGB(i, j, intersection.getRGB());
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
    private boolean hasIntersection(int x,int y, int ratio_x, int ratio_y,Color color1,Color color2,BufferedImage img1,BufferedImage img2){
        int              i,j;
        int      c1,c2,c3,c4;
        int        xmin,ymin;
        int        xmax,ymax;
    
        
        c3 = color1.getRGB();
        c4 = color2.getRGB();
        xmin =((x-ratio_x) > 0)?(x - ratio_x) : 0;
        ymin =((y-ratio_y) > 0)?(y - ratio_y) : 0;
        xmax =((x+ratio_x+1)>(width -1))?(width - 1):(x + ratio_x + 1);
        ymax =((y+ratio_y+1)>(height -1))?(height - 1):(y + ratio_y + 1);
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
    public BufferedImage calculateIntersectionArea(final IGeoPosition basePosition, final IGeoPosition comparedPosition, final int areaRadius) {
        final BufferedImage baseAreaImage = this.getAreaImage(basePosition, areaRadius, BASE_AREA_COLOR);
        return this.calculateIntersectionArea(baseAreaImage, comparedPosition, areaRadius);
    }

    public BufferedImage calculateIntersectionArea(final BufferedImage baseAreaImage, final IGeoPosition comparedPosition, final int areaRadius) {
        final BufferedImage comparedSensorAreaImage = this.getAreaImage(comparedPosition, areaRadius, COMPARED_AREA_COLOR);
        
        if(baseAreaImage == null) {
            return null;
        } else {
            return this.getIntersectionAreaImage(baseAreaImage, comparedSensorAreaImage, comparedPosition);
        }
    }
    public BufferedImage getAreaImage(final IGeoPosition position, final int areaRadius) {
        return this.getAreaImage(position, areaRadius, BASE_AREA_COLOR);
    }
    
    private BufferedImage getAreaImage(final IGeoPosition position, final int areaRadius, Color color) {
        final int diameter = areaRadius * 2;
        final BufferedImage sensorArea = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
        final Graphics graphics = sensorArea.createGraphics();

        graphics.setColor(color);
        graphics.fillOval(scale.convertToX(position) - areaRadius, scale.convertToY(position) - areaRadius, diameter, diameter);

        return sensorArea;
    }   

    private BufferedImage getIntersectionAreaImage(BufferedImage baseAreaImage, BufferedImage comparedAreaImage, IGeoPosition comparedAreaPosition) {
        final int radius = (int) (Sensor.RADIUS_IN_PIXELS * 1.1);

        BufferedImage intersectionAreaImage = null;

        final int rgbRepresentationOfBaseArea = BASE_AREA_COLOR.getRGB();
        final int rgbRepresentationOfComparedArea = COMPARED_AREA_COLOR.getRGB();

        final int comparedAreaX = scale.convertToX(comparedAreaPosition);
        final int comparedAreaY = scale.convertToY(comparedAreaPosition);
        
        final int comparedAreaBeginX = ((comparedAreaX - radius) > 0) ? (comparedAreaX - radius) : 0;
        final int comparedAreaBeginY = ((comparedAreaY - radius) > 0) ? (comparedAreaY - radius) : 0;
        final int comparedAreaEndX = ((comparedAreaX + radius+1) > (width -1)) ? (width - 1) : (comparedAreaX + radius + 1);
        final int comparedAreaEndY = ((comparedAreaY + radius+1) > (height -1)) ? (height - 1) : (comparedAreaY + radius + 1);
        
        for (int row = comparedAreaBeginX; row < comparedAreaEndX; row++) {
            for (int column = comparedAreaBeginY; column < comparedAreaEndY; column++) {

                final int baseAreaPixel = baseAreaImage.getRGB(row, column);
                final int comparedAreaPixel = comparedAreaImage.getRGB(row, column);
                final boolean isBaseAreaPixelPainted = (baseAreaPixel == rgbRepresentationOfBaseArea);
                final boolean isComparedAreaPixelPainted = (comparedAreaPixel == rgbRepresentationOfComparedArea);
                final boolean hasIntersection = isBaseAreaPixelPainted && isComparedAreaPixelPainted;

                if (hasIntersection) {
                    if (intersectionAreaImage == null) {
                        intersectionAreaImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                    }
                    
                    intersectionAreaImage.setRGB(row, column, BASE_AREA_COLOR.getRGB());
                }
            }
        }
        
        return intersectionAreaImage;
    }
  
    /**
     * Adiciona a detec��ao de sensor ao objeto
     * @param sensor objeto associdado ao sensor
     * @param ratio raio em metros
     */
    @SuppressWarnings("deprecation")
	public boolean addSensorDetection(SensorPosition sensor){
        int               xs,ys;
        int             rsx,rsy;
        double            ratio;
        Rpixel           dratio;
        BufferedImage      img1;
        GeoPosition    position;
        
        // Verifica se o sensor estar dentro da area
        if( (sensor.getLatitude() >= lati)&&(sensor.getLatitude() <= latf)&&
            (sensor.getLongitude() >= longi)&&(sensor.getLongitude()<=longf)&&
            (sensor.getRatio() > 0)){
        
        	ratio = sensor.getRatio();
        	position = new GeoPosition(sensor.getLatitude(),sensor.getLongitude());
            // Calcula coordenadas dentro da imagem
            ys = scale.convertToY(position);
            xs = scale.convertToX(position);
            // Raio convertido para graus
            //dratio = ScaleConverter.convertToGrades(ratio, sensor.getLatitude(), sensor.getLongitude());
            // converte o raio em pixeis
            dratio = scale.convertToPixel(ratio, sensor.getLatitude(), sensor.getLongitude(),height,width);
            //db = new Double( (dratio /(latf - lati))*height);
            rsx = dratio.getRadiox().intValue();
            rsy = dratio.getRadioy().intValue();
            // Verifica se parametros sao validos
            if( (rsx >0)&&(rsy >0)&&(xs >=0)&&(ys>=0)){
                if( status == LocationArea.NO_POSITION){
                    clearImage(this.img);
                    // Primeira vez que esta sendo detectado a area possivel e  a mesma da estacao
                    drawCicle(xs,ys,rsx,rsy,Color.BLUE,this.img);
                    drawCicle(xs,ys,rsx,rsy,Color.BLUE,this.imgComb);
                    //drawCicle(xs,ys,rs,Color.BLUE,this.img);
                    xc    = xs;
                    yc    = ys;
                    //ratio = ( (rsx+rsy)/2);
                    status = LocationArea.DETECTED;
                    if( debugMode ){
	                    JFrame frm = new JFrame("Teste Inicial");
	                    JPanel pan = new JPanel();
	                    JLabel lbl = new JLabel( new ImageIcon(img) );
	                    pan.add( lbl );
	                    frm.getContentPane().add( pan );
	                    frm.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	                    frm.pack();
	                    frm.show();
                    }
                    
                }
                else{
                    img1 = new BufferedImage(width,height,java.awt.image.BufferedImage.TYPE_INT_RGB);
                    Graphics g = img1.createGraphics();
                    g.setColor( Color.WHITE );
                    g.fillRect( 0, 0, width, height );
                    //drawCicle(xs,ys,rs,Color.BLUE,img1);
                    drawCicle(xs,ys,rsx,rsy,Color.BLUE,img1);
                    drawCicle(xs,ys,rsx,rsy,Color.BLUE,this.imgComb);
                    if( hasIntersection(xs, ys, rsx, rsy, Color.BLUE,Color.BLUE,img1,this.img)){
	                    // Calcula a imagem interseccao entre as duas imagens
	                    //img = getIntersectionImage(xs,ys, rs,Color.BLUE,Color.BLUE,img1,this.img);
	                    img = getIntersectionImage(xs,ys, rsx, rsy,Color.BLUE,Color.BLUE,img1,this.img);
	 
	                    if( debugMode ){
		                    JFrame frm = new JFrame("Intersecao");
		                    JPanel pan = new JPanel();
		                    JLabel lbl = new JLabel( new ImageIcon(img1) );
		                    pan.add( lbl );
		                    frm.getContentPane().add( pan );
		                    frm.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		                    frm.pack();
		                    frm.show();
		
		                    JFrame frm1 = new JFrame("Uniao");
		                    JPanel pan1 = new JPanel();
		                    JLabel lb2 = new JLabel( new ImageIcon(imgComb) );
		                    pan1.add( lb2 );
		                    frm1.getContentPane().add( pan1 );
		                    frm1.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		                    frm1.pack();
		                    frm1.show();
	                    }
                    }
                    else{
                    	return false;
                    }
                }
                nSensors++;
            }
        }
        return true;
        
    }
    /**
     * Desenha o centroide da area de intersecao
     */
    
    public void showCentroide(){
        // calcula o centroide da interseccao
        calculateCentroid();
        // calcula o raio que centrado no centroide engloba todos os pontos
        calculateRatioCentroidPixel();
        drawCentroide(Color.RED);
        
    } /**
     * Retorna o objeto centroide da imagem com a intersecao
     * @return
     */
    public Centroide getCentroide(){
        Centroide  centroide=null;
        Point       position;
        long             rpx;
        double    latC,longC;
        // calcula o centroide da interseccao em coordenadas x,y da imagem
        position = calculateCentroid();
        // calcula o raio que centrado no centroide engloba todos os pontos
        rpx      = calculateRatioCentroidPixel();
        if(position!= null){
	        latC     = scale.convertToLatitude(position.getY());
	        longC    = scale.convertToLongitude(position.getX());
	        ratio    = scale.convertPixelDistanceToMeters(rpx);
	        centroide = new Centroide(latC,longC,ratio);
	        centroide.setShape(shape);
        }
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
        Point         point=null;

        if((shape != null) &&(!shape.isEmpty())){
            xc = yc = n = 0;
            iterator = shape.iterator();   
	        while(iterator.hasNext()){
	            point = iterator.next();
	            xc = xc +point.getX();
	            yc = yc + point.getY();
	            n++;
	        }
	        if( n!= 0){
		        xc = xc / n;
		        yc = yc / n;
		    }
        }
	    point = new Point(xc,yc);
        return point;
    }
    /**
     * Calcula o raio do circulo da area que contem toda area de intersecao.
     * @return raio em pixeis
     */
    private long calculateRatioCentroidPixel(){
        Iterator<Point> iterator;
        Point              point;
        double          distance;
        int           ratioPixel;

        ratioPixel = 0;
        if((shape != null)&&(!shape.isEmpty())){
	        iterator = shape.iterator();        
	        while(iterator.hasNext()){
	            point = iterator.next();
	            distance = Math.sqrt( Math.pow((point.getX()-xc),2.0) + Math.pow(((point.getY() - yc)),2.0));
	            if (distance > ratioPixel){
	                ratioPixel = (int)Math.round(distance);
	            }
	        }
        }
        return ratioPixel;
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
        g.drawOval(xc -ratioPixel, yc-ratioPixel, 2*ratioPixel, 2*ratioPixel);
    }
    /**
     * @param args
     */
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
//        BufferedImage   image;

//        LocationArea   area   = new LocationArea (-3,-5,-1,-1,400,200);
//        SensorPosition sensor1 = new SensorPosition(-3.769644,-38.479653,5);
//        SensorPosition sensor2 = new SensorPosition(-3.769621,-38.479605,5);
//        SensorPosition sensor3 = new SensorPosition(-3.769661,-38.479586,5);
//        area.addSensorDetection(sensor1, 1);
//        area.addSensorDetection(sensor2, 1);
//        area.addSensorDetection(sensor3, 1);
//        area.showCentroide();
        
//        ScaleConverter.latIni = -3.769274;
//        ScaleConverter.longIni = -38.480586;
//        ScaleConverter.latEnd = -3.770056;
//        ScaleConverter.longEnd = -38.478736;
//        ScaleConverter.height = 700;
//        ScaleConverter.width  = 700;
        
        LocationArea   area   = new LocationArea (-3.769816, -38.479963, //-3.770030, -38.480291, //-3.769274, -38.480586,
        		-3.769481, -38.479293, //-3.769361, -38.478950,// -3.770056,-38.478736,
				  650,610);
        //B3
        SensorPosition sensorB3 = new SensorPosition(-3.769702, -38.479606,1.0);
        //B4
        SensorPosition sensorB4 = new SensorPosition(-3.769724, -38.479657,1.0);
        //KIT
        SensorPosition sensorKIT = new SensorPosition(-3.769630,-38.479628,1.0);
        area.addSensorDetection(sensorB3);
        area.addSensorDetection(sensorB4);
        area.addSensorDetection(sensorKIT);
        Centroide c = area.getCentroide();
        if( c!= null){
            area.drawCentroide(Color.RED);
        	System.out.println("lat: ["+c.getLatitude()+"] ; lng: ["+c.getLongitude()+"] ; radio = [" +c.getRatio() + "]");
        }
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
    
    public boolean checkIntersection(SensorPosition sensor){
        int               xs,ys;
        int             rsx,rsy;
        double            ratio;
        Rpixel           dratio;
        BufferedImage      img1;
        GeoPosition    position;
        // Verifica se o sensor estar dentro da area
        if( (sensor.getLatitude() >= lati)&&(sensor.getLatitude() <= latf)&&
            (sensor.getLongitude() >= longi)&&(sensor.getLongitude()<=longf)&&
            (sensor.getRatio() > 0)){
        
        	position = new GeoPosition(sensor.getLatitude(),sensor.getLongitude());
        	ratio = sensor.getRatio();
            // Calcula coordenadas dentro da imagem
            ys = scale.convertToX(position);
            xs = scale.convertToY(position);
            // Raio convertido para graus
            //dratio = ScaleConverter.convertToGrades(ratio, sensor.getLatitude(), sensor.getLongitude());
            // converte o raio em pixeis
            dratio = scale.convertToPixel(ratio, sensor.getLatitude(), sensor.getLongitude(),height,width);
            //db = new Double( (dratio /(latf - lati))*height);
            rsx = dratio.getRadiox().intValue();
            rsy = dratio.getRadioy().intValue();
            // Verifica se parametros sao validos
            if( (rsx >0)&&(rsy >0)&&(xs >=0)&&(ys>=0)){
                if( status == LocationArea.NO_POSITION){
                    clearImage(this.img);
                    // Primeira vez que esta sendo detectado a area possivel e  a mesma da estacao
                    drawCicle(xs,ys,rsx,rsy,Color.BLUE,this.img);
                    //drawCicle(xs,ys,rs,Color.BLUE,this.img);
                    xc    = xs;
                    yc    = ys;
                    ratio = sensor.getRatio();
                    //ratio = ( (rsx+rsy)/2);
                    status = LocationArea.DETECTED;
                }
                else{
                    img1 = new BufferedImage(width,height,java.awt.image.BufferedImage.TYPE_INT_RGB);
                    Graphics g = img1.createGraphics();
                    g.setColor( Color.WHITE );
                    g.fillRect( 0, 0, width, height );
                    //drawCicle(xs,ys,rs,Color.BLUE,img1);
                    drawCicle(xs,ys,rsx,rsy,Color.BLUE,img1);
                    // Calcula a imagem interseccao entre as duas imagens
                    //img = getIntersectionImage(xs,ys, rs,Color.BLUE,Color.BLUE,img1,this.img);
                    return hasIntersection(xs,ys, rsx, rsy,Color.BLUE,Color.BLUE,img1,this.img);
                }
            }
        }
    	return false;
    }
    public double getLati() {
 		return lati;
 	}
 	public void setLati(double lati) {
 		this.lati = lati;
 	}
 	public double getLatf() {
 		return latf;
 	}
 	public void setLatf(double latf) {
 		this.latf = latf;
 	}
 	public double getLongf() {
 		return longf;
 	}
 	public void setLongf(double longf) {
 		this.longf = longf;
 	}
 	public int getWidth() {
 		return width;
 	}
 	public void setWidth(int width) {
 		this.width = width;
 	}
 	public int getHeight() {
 		return height;
 	}
 	public void setHeight(int height) {
 		this.height = height;
 	}
 	public int getXc() {
 		return xc;
 	}
 	public void setXc(int xc) {
 		this.xc = xc;
 	}
 	public int getYc() {
 		return yc;
 	}
 	public void setYc(int yc) {
 		this.yc = yc;
 	}
 	public BufferedImage getImg() {
 		return img;
 	}
 	public void setImg(BufferedImage img) {
 		this.img = img;
 	}
 	public ScaleConverter getScale() {
 		return scale;
 	}
 	public void setScale(ScaleConverter scale) {
 		this.scale = scale;
 	}

}
