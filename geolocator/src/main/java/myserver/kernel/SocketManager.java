/*
 * SocketManager.java
 *
 * Created on September 6, 2004, 9:59 AM
 */

package myserver.kernel;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 * This class is resxponsable for control data communication using socket
 * @author  Danilo
 */
class SocketManager {
    /**
     * Atendent Server TCP/IP Port
     */
    private  ServerSocket        server;
    /**
     * Socket for communication
     */
    private  Vector             sockets;
    /**
     * TCP/IP Port number
     */
    private  int                   port;
    /**
     * TCP/IP Port number
     */
    private  int             bufferSize;
    
    /**
     * Constructs a new instance of Socket manager
     * @param portNumber - TCP/IP port number
     * @param bufferSize - Size of input/output buffer in bytes
     */
    public SocketManager(int portNumber,int bufferSize) throws IOException{
        port   = portNumber;
        try {
            // Cria a porta do servidor
            server          = new ServerSocket(port);
            sockets         = new Vector();
            this.bufferSize = bufferSize;
        }
        catch(IOException e){}
    }
    /**
     * atends the service port and creates a new socket.
     * @return none.
     */
    public SocketLink accept() throws IOException {
        SocketLink link;
        Socket   socket;
        
        try{
            // aguarda a coexao de um socket
            socket = server.accept();
            // cria objeto link de comunicação
            link   = new SocketLink(socket,bufferSize);
            // retorna o link
            return link;
        }
        catch(IOException e){
            return null;
        }
    }
    /**
     * close a open socket
     * @return none.
     */
    public void close() throws IOException {
        
        try{
            // fecha o socket do servidor de comunicação
            server.close();
        }
        catch(IOException e){}
    }
    
}