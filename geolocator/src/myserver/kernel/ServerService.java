/*
 * Created on Jul 2, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package myserver.kernel;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import myserver.ui.IconList;
import myserver.ui.ListItem;


/**
 * @author Danilo
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ServerService implements RegisterLogable {
	private Thread           thread;// thread que roda o servidor
	private Service         service;// servico de servidor
	private IconList connectionList;// lista de icones conectadas
	boolean                    view;// flag para indicar se as conexoes serao mostradas

	/**
	 *  
	 */
	static ServerService    instance;	
	/**
	 * 
	 */
	 final String led1 = "D:\\USA\\Denver\\Docs\\java\\Server\\server\\util\\LED1ON.GIF";
//	 final String led1 = "..\\util\\LED1ON.GIF";
     final String led2 = "D:\\USA\\Denver\\Docs\\java\\Server\\server\\util\\LED2ON.GIF";
//	 final String led2 = "..\\util\\LED2ON.GIF";
     
     static {
     }
    /**
     * 
     * @param port
     * @param bufferSize
     */
	public ServerService(int port,int bufferSize,boolean view) {
		super();
		service        = new Service(port,bufferSize);
		thread         = new Thread(service);
		if ( view == true){
			connectionList = new IconList();
			connectionList.createWindow(500, 0);
		}
		instance       = this;
	}

	/**
	 * 
	 * @param status
	 * @param ip
	 * @return
	 */
	public synchronized int addNewConnection(boolean status, String ip) {
		Icon icon;

		if(view == true){
			// cria
			if (status == true)
				icon = new ImageIcon( led2);
			else
				icon = new ImageIcon( led1 );

			// retorna o indice do elemento na lista
			return connectionList.addItem(new ListItem(icon, ip)) - 1;
		}
		return 0;
	}
	/**
	 * 
	 * @param status
	 * @param index
	 */
	public synchronized  void changeConnectionStatus(boolean status, int index) {
		ListItem li;
		Icon icon;

		if(view == true){
			// retorna o indice do elemento na lista
			li = connectionList.getItem(index);
			if (status == true)
				icon = new ImageIcon( led2);
			else
				icon = new ImageIcon( led1 );
			li.setIcon(icon);
			connectionList.show();
		}

	}

	/**
	 *  
	 */
	public synchronized RegisterLogable getInterface() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.kernel.RegisterLogable#addLog(java.lang.String)
	 */
	public synchronized void addLog(String log) {

	}
	
	/**
	 * 
	 * @param index
	 */
	public synchronized void removeConnection(int index) {

		if(view == true){
			// retorna o indice do elemento na lista
			connectionList.removeItem(index);
		}
	}
	/**
	 * 
	 *
	 */
	public synchronized void start(){
		thread.start();
	}

	/**
	 *  
	 */
	@SuppressWarnings("deprecation")
	public void stopAll() {
		int i;

		if(view == true){
			for (i = 0; i < connectionList.getJList().getModel().getSize(); i++) {
				this.changeConnectionStatus(false, i);
			}
		}
        service.stop();
        thread.stop();
	}
	/**
	 * 
	 * @return
	 */
	public boolean isRunning(){
		return this.service.isRunning();
	}

	/**
	 * @return
	 */
	public static ServerService getInstance() {
		return instance;
	}
}