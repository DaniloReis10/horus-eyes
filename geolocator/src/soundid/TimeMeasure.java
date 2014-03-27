package soundid;

import java.util.Date;

/**
 * Classe que registra a medida do tempo de deteccao do ultrasom enviado pela base
 * @author DaniloReis
 *
 */
public class TimeMeasure {
	private int        id;// Identificador da base
	private double   time;// tempo do relogio em microsegundos
	private int      tick;// ciclo do relogio sincronizado
    private Date dateTime;// Data e hora da medida	

	public TimeMeasure(int id, double timeToDetect, int tick) {
		super();
		this.id = id;
		this.time = timeToDetect;
		this.tick = tick;
	}
	
	public TimeMeasure() {
		super();
		id = tick = 0;
		time = 0.0;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public int getTick() {
		return tick;
	}

	public void setTick(int tick) {
		this.tick = tick;
	}
	
	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

}
