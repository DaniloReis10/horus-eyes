package myserver.kernel;

import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;

/**
 * Thie class is a facade class that holds all busiess rules and control others classes
 * @author Danilo Reis
 */
public class ServerController {
    private  SocketManager   socketManager;
    private Vector                requests;
    private Vector                 threads;    
    private Vector                 sockets;    
    private boolean                   stop;
    static ServerController       instance;
    /**
     * Creates a new instance of ServerCotroller
     * @param None.
     * @return None.
     */
    public ServerController(int port,int bufferSize){
        try {
            // creates comunication manager object
            socketManager     = new SocketManager(port,bufferSize);
            // creates parser object
            requests          = new Vector();
            threads           = new Vector();
            sockets           = new Vector();
            stop              = true;
            instance          = this;
        }
        catch(IOException e){}
    }
    public static ServerController getInstance(){
    	return instance;
    }
    /**
     * Control query responce process for clients request
     * @param None.
     * @return None.
     */
    public void run(){
        SocketLink        link;
        Socket          socket;
        ProcessRequest request;
        Thread          thread;
        
        stop = false;
        System.out.println("Servico do servidor acionado...");
        while (!stop){
            try{
                // aguarda conexao
                link    = socketManager.accept();
                // cria um novo objeto de tratamento da requisição
                request = new ProcessRequest(link);
                // cria thread para realizar o processamento
                thread  = new Thread(request);
                // adiociona a lista de requisicoes
                this.addRequest(request,thread,link);
                // executa a thread
                thread.start();
            }
            catch(Exception e){}
        }
        System.out.println("Servico shutdown pelo Grid..");
    }
    
    /**
     * Return a XML with all transactions realized between two specoific dates
     * @param startDate - start date
     * @param endDate - end date.
     * @return XML with all realized transactions
     * XML Format:
     *
     */
    public String getXMLTransactions() {
        return null;
    }
    
    /**
     * Getter for property stop.
     * @return Value of property stop.
     */
    public synchronized  boolean isStop() {
        return stop;
    }
    /**
     *
     */
    void addRequest(ProcessRequest request,Thread thread,SocketLink link){
        // adiciona a requisição
        requests.add(request);
        // adiciona a thread
        threads.add(thread);
        // adiciona o link
        sockets.add(link.getSocket());
    }
    
    /**
     * Setter for property stop.
     * @param stop New value of property stop.
     */
    public  synchronized  void setStop(boolean stop) {
        int              i;
        Iterator iterator1;
        Iterator iterator2;
        Iterator iterator3;
        ProcessRequest  pr;
        Thread          th;
        Socket          sk;
        
        try{
            this.stop = stop;
            iterator1 = requests.iterator();
            iterator2 = threads.iterator();
            iterator3 = sockets.iterator();
            do {
                // pega elemento da lista
                pr = (ProcessRequest)iterator1.next();
                th = (Thread)iterator2.next();
                sk = (Socket)iterator3.next();
                //  compara com o numero serial
                pr.setStop(true);
                sk.close();
  //              th.destroy();
            }
            while (iterator1.hasNext() == true);
        }
        catch(Exception e){}
    }
}