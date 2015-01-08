package trilaceration;

import java.util.ArrayList;
import java.util.Date;
import br.com.fujitec.location.facade.IGeoPosition;

public class Centroide implements IGeoPosition{
	
	private double         latitude;// latitude em graus
    private double        longitude;// longitude em graus
    private double            ratio;// raio em metros
    private ArrayList<Point>  shape;// Forma da interseccao
    private Date               date;// time stamp da data e hora do calculo
    
	public Centroide()  {
		super();
		this.latitude  = 0;
		this.longitude = 0;
		this.ratio     = 0;
		this.date      = new Date();
	}
	
	public Centroide(double latitude, double longitude, double ratio) {
		super();
		this.latitude  = latitude;
		this.longitude = longitude;
		this.ratio     = ratio;
		this.date = new Date();
	}



	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	@Override
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@Override
	public double getLatitude() {
		return latitude;
	}

	@Override
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public Date getDate() {
		return date;
	}

	public ArrayList<Point> getShape() {
		return shape;
	}

	public void setShape(ArrayList<Point> shape) {
		this.shape = shape;
	}

	
}
