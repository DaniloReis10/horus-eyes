package trilaceration;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import location.facade.IGeoPosition;
import location.geoengine.DevicesController;
import location.geoengine.GeoPosition;
import simulagent.Device;
import simulagent.DeviceFactory;
import simulagent.Sensor;

public class LocationArea2 {

    public static final Color BASE_AREA_COLOR = Color.RED;
    public static final Color COMPARED_AREA_COLOR = Color.YELLOW;
    
    private double initialLatitude, initialLongitude;
    private double finalLatitude, finalLongitude;
    private int width, height;
    private double deltaX, deltaY;

    public static final int SX[] = { 0, 1, 1, 1, 0, -1, -1, -1 };
    public static final int SY[] = { -1, -1, 0, 1, 1, 1, 0, -1 };

    public LocationArea2() {
        this(ScaleConverter.latIni, ScaleConverter.longIni, ScaleConverter.latEnd, ScaleConverter.longEnd, ScaleConverter.width,
                ScaleConverter.height);
    }

    public LocationArea2(double initialLongitude, double initialLatitude, double finalLongitude, double finalLatitude, int width, int height) {
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
        } else {
            throw new RuntimeException("Location Area parameters are invalid!");
        }
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
        final int radius = Sensor.RADIUS;

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

    public static void main(String[] args) {
        ScaleConverter.latIni = 0.0;
        ScaleConverter.longIni = 0.0;
        ScaleConverter.latEnd = 0.006;
        ScaleConverter.longEnd = 0.006;
        ScaleConverter.width  = 980;
        ScaleConverter.height = 480;

        LocationArea2 area = new LocationArea2();
        List<Device> sensors = DeviceFactory.createStaticDevices(Sensor.class, 3);
        
        sensors.get(0).setCurrentPosition(new SensorPosition(0.025, 0.029911, 0.05));
        sensors.get(1).setCurrentPosition(new SensorPosition(0.025, 0.03, 0.05));
        ScaleConverter.convertToLongitude(1);
//        sensors.get(2).setCurrentPosition(new SensorPosition(0.023, 0.031, 0.05));

//        BufferedImage intersectionImage = area.calculateIntersectionArea((Sensor)sensors.get(0), (Sensor)sensors.get(1));
        
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setSize(ScaleConverter.width, ScaleConverter.height);

        JFrame frame = new JFrame();
        frame.add(panel);
        frame.setSize(ScaleConverter.width, ScaleConverter.height);
        frame.setVisible(true);
  
        Graphics graphics = panel.getGraphics();
        
        while(true) {

            graphics.setColor(Color.LIGHT_GRAY);
            GeoPosition geo = new GeoPosition(0, ScaleConverter.convertToLongitude(10));
            int lon = ScaleConverter.convertToX(geo);
            graphics.fillRect(ScaleConverter.convertToX(sensors.get(0).getCurrentPosition()) - 5, ScaleConverter.convertToY(sensors.get(0).getCurrentPosition()) - 5, 5, 5);

            graphics.setColor(Color.BLUE);
            graphics.fillRect(ScaleConverter.convertToX(sensors.get(1).getCurrentPosition()) - 2, ScaleConverter.convertToY(sensors.get(1).getCurrentPosition()) - 2, 5, 5);

//            graphics.setColor(Color.BLACK);
//            graphics.fillRect(ScaleConverter.convertToX(sensors.get(0).getCurrentPosition()), ScaleConverter.convertToY(sensors.get(0).getCurrentPosition()), 5, 5);
    
            final double distance = DevicesController.calculateDistance(sensors.get(0).getCurrentPosition(), sensors.get(1).getCurrentPosition());
            graphics.drawString(String.valueOf(distance), 100, 100);
        }
//        graphics.drawImage(area.getAreaImage((Sensor)sensors.get(0), BASE_AREA_COLOR), 100, 100, panel);
//        graphics.drawImage(area.getAreaImage((Sensor)sensors.get(1), COMPARED_AREA_COLOR), 100, 100, panel);
//        graphics.drawImage(intersectionImage, 100, 100, panel);
//        graphics.drawImage(area.getAreaImage((Sensor)sensors.get(2), Color.BLUE), 100, 100, panel);
//        intersectionImage = area.calculateIntersectionArea(intersectionImage, (Sensor)sensors.get(2));
//        graphics.drawImage(intersectionImage, 100, 100, panel);
    }
}