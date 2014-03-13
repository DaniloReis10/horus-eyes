package sensordataset;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class AlarmQueue {
	ArrayList<AlarmData>   alarms;
	
	public AlarmQueue() {
		super();
		alarms = new ArrayList<AlarmData>();
	}
	/**
	 * Adiciona alarme na lista de alarmes
	 * @param data
	 */
	public void addAlarm(AlarmData data){
		if ( data != null){
			alarms.add(data);
		}
	}
	
	public boolean StartHandleAlarm(int type,IAlarmAction action){
		Iterator<AlarmData> iterator;
		AlarmData              alarm;
		boolean  actionResult,result;
		
		iterator = alarms.iterator();
		result = true;
		// Percorre a lista de alarmes pendentes
		while(iterator.hasNext()){
			alarm = iterator.next();
			if( (alarm.getAlarmType() == type) && (alarm.getStatus() == AlarmData.PENDING)){
				// executa a acao associada ao alarme
				actionResult = action.execute(alarm);
				result = result  && actionResult;
				// registra a data de execução da acao
				alarm.setActionDate(new Date());
				alarm.setStatus(AlarmData.HANDLED);
				// TODO Fa z persistencia do alarme no banco de dados
				// Remove alarme da lista
				iterator.remove();
			}
		}
		return result;
	}
	/**
	 * Retorna a lista de alarmes pendentes
	 * @return
	 */
	public ArrayList<AlarmData> getAlarms() {
		return alarms;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
