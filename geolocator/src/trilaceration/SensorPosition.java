package trilaceration;

import java.util.Date;

import location.geoengine.GeoPosition;

public class SensorPosition  extends GeoPosition{

	private double        radius;// Raio em metros da deteccao
	private double       height;// altura do sensor
	private int           floor;// Andar onde esta o sensor
	private String         name;// Nome do sensor
	private String  description;// descricao

	/**
	 * Construtor completo
	 * @param latitude
	 * @param longitude
	 * @param ratio
	 * @param height
	 * @param floor
	 * @param name
	 * @param description
	 */
	public SensorPosition(double latitude, double longitude, double ratio,double height, int floor, String name, String description) {
		super(new Date(),latitude,longitude);
		this.radius       = ratio;
		this.height      = height;
		this.floor       = floor;
		this.name        = name;
		this.description = description;
	}
	/**
	 * Construtor minimo
	 * @param latitude
	 * @param longitude
	 * @param ratio
	 */
	public SensorPosition(double latitude, double longitude, double ratio) {
		super(new Date(),latitude,longitude);
		this.radius = ratio;
	}
	// Metodos Set e gets dos atributos
	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double ratio) {
		this.radius = ratio;
	}
		/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
