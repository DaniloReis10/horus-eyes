package soundid;

import java.util.Date;

/**
 * Classe que registra a medida do tempo de deteccao do ultrasom enviado pela base
 * @author DaniloReis
 *
 */
public class TimeMeasure {
	private int        id;// Identificador da base
	private int      time;// tempo do relogio em microsegundos
	private int      tick;// ciclo do relogio sincronizado
    private Date dateTime;// Data e hora da medida	

	public TimeMeasure(int id, int time, int tick) {
		super();
		this.id = id;
		this.time = time;
		this.tick = tick;
	}
	
	public TimeMeasure() {
		super();
		id = time = tick = 0;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
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
