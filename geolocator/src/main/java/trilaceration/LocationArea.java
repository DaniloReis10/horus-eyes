package trilaceration;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;

import br.com.fujitec.location.facade.IGeoPosition;
import br.com.fujitec.simulagent.models.Sensor;

/*Classe responsável por calculo da trileceração em uma área de estudo*/
public class LocationArea {
    private double       initialLatitude, initialLongitude;// coordenadas do canto superior esquerdo da area
    private double       finalLatitude, finalLongitude;// coordenadas do canto inferior direito da area
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
    
    public static final Color BASE_AREA_COLOR = Color.RED;
    public static final Color COMPARED_AREA_COLOR = Color.YELLOW;
    
    public LocationArea() {
        this(ScaleConverter.latIni, ScaleConverter.longIni, ScaleConverter.latEnd, ScaleConverter.longEnd, ScaleConverter.width,
                ScaleConverter.height);
    }

    public LocationArea(double initialLongitude, double initialLatitude, double finalLongitude, double finalLatitude, int width, int height) {
        final boolean isLongitudeValid = (initialLongitude < finalLongitude);
        final boolean isLatitudeValid = (initialLatitude < finalLatitude);
        final boolean isWidthValid = (width > 0);
        final boolean isHeightValid = (height > 0);
        final boolean areParametersValid = isLongitudeValid && isLatitudeValid && isWidthValid && isHeightValid;

        if (areParametersValid) {
            this.initialLongitude = initialLongitude;
            this.initialLatitude = initialLatitude;

            this.finalLongitude = finalLongitude;
            this.finalLatitude = finalLatitude;

            this.width = width;
            this.height = height;

            this.deltaX = (finalLongitude - initialLongitude) / width;
            this.deltaY = (finalLatitude - initialLatitude) / height;
            
            this.img = new BufferedImage(width,height,java.awt.image.BufferedImage.TYPE_INT_RGB);
            final Graphics graphics = img.createGraphics();
            graphics.setColor( Color.WHITE );
            graphics.fillRect( 0, 0, width, height );
        } else {
            throw new RuntimeException("Location Area parameters are invalid!");
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
        if( (sensor.getLatitude() >= initialLatitude)&&(sensor.getLatitude() <= finalLatitude)&&
            (sensor.getLongitude() >= initialLongitude)&&(sensor.getLongitude()<=finalLongitude)){
        
            // Calcula coordenadas dentro da imagem
            db = new Double( (sensor.getLatitude() - initialLatitude)/deltaY);
            ys = height - db.intValue();
            db = new Double( (sensor.getLongitude() - initialLongitude)/deltaX);
            xs = db.intValue();
            // Raio convertido para graus
            dratio = ScaleConverter.convertToGrades(ratio, sensor.getLatitude(), sensor.getLongitude());
            // converte o raio em pixeis
            db = new Double( (dratio /(finalLatitude - initialLatitude))*height);
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
        graphics.fillOval(ScaleConverter.convertToX(position) - areaRadius, ScaleConverter.convertToY(position) - areaRadius, diameter, diameter);

        return sensorArea;
    }

    private BufferedImage getIntersectionAreaImage(BufferedImage baseAreaImage, BufferedImage comparedAreaImage, IGeoPosition comparedAreaPosition) {
        final int radius = (int) (Sensor.RADIUS_IN_PIXELS * 1.1);

        BufferedImage intersectionAreaImage = null;

        final int rgbRepresentationOfBaseArea = BASE_AREA_COLOR.getRGB();
        final int rgbRepresentationOfComparedArea = COMPARED_AREA_COLOR.getRGB();

        final int comparedAreaX = ScaleConverter.convertToX(comparedAreaPosition);
        final int comparedAreaY = ScaleConverter.convertToY(comparedAreaPosition);
        
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
}
