package sensordataset;

import java.util.ArrayList;
import java.util.Iterator;

public class AgentDataLog {
	ArrayList<DataPosInfo>  positionHistory;
	
	public AgentDataLog() {
		super();
		positionHistory = new ArrayList<DataPosInfo>();
	}

	public void addPosition(DataPosInfo position){
		if( position != null){
			positionHistory.add(position);
		}
	}
	
	public Iterator<DataPosInfo> getPositionHistoryIterator(){
		return positionHistory.iterator();
	}
	public void clear(){
		positionHistory.clear();
	}


}
