package soundid;

import simulagent.ModelEnviroment;

public class SoundIdModel extends ModelEnviroment {
    public static final int TAG_TYPE   = 1;
    public static final int BASE_TYPE  = 2;
    public static final int COORD_TYPE = 3;
    public static final double DISTANCE_DETECTION = (float) 10.0;
    public static final double TIME_TICK = (float) 0.001;
    
    
    public static double getSoundSpeed(double temperature){
    	return 349.00;
    }
    
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}

}
