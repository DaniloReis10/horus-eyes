/*
 * Service.java
 *
 * Created on September 19, 2004, 7:34 PM
 */

package myserver.kernel;

/**
 *
 * @author  Danilo
 */
public class Service implements Runnable{
    /**
     *
     */
    private ServerController  serverControl;
    /**
     *
     */
    
    /**
	 * @param port
	 * @param bufferSize
	 */
	public Service(int port, int bufferSize) {
		
		// TODO Auto-generated constructor stub
        // Cria a infra-estrutura do serviço
        serverControl = new ServerController(port,bufferSize);
	}
	/**
     *
     */
    public void run() {
        serverControl.run();
    }
    /**
     *
     */
    public void stop(){
        serverControl.setStop(true);
    }
    /**
     *
     */
    public boolean isRunning(){
        return !serverControl.isStop();
    }
}