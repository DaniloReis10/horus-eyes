/*
 * SocketLink.java
 *
 * Created on September 13, 2004, 8:43 AM
 */

package myserver.kernel;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author  Danilo
 */
public class SocketLink {
    /**
     * Socket for communication
     */
    private  Socket              socket;
    /**
     * Output Stream object
     */
    private  OutputStream           out;
    /**
     * Input Stream object
     */
    private  InputStream             in;
    private  DataOutputStream 	 writer;
    private  BufferedReader		 reader;
    
    /** Creates a new instance of SocketLink */
    public SocketLink(Socket socket,int bufferSize) {
        try {
            this.socket = socket;
            // Pega o objeto de controle de saida da porta
            out    = socket.getOutputStream();
            writer = new DataOutputStream(out);
            // Pega o objeto de controle de entrada da porta
            in      = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));            
        }
        catch(IOException e){}
        
    }
    
	/**
	 * Writes a array of bytes to open socket
	 * @param b - array of bytes
	 * @return none.
	 */
	public synchronized void write(String out)throws IOException {
		writer.writeBytes(out);
		writer.write('\n');
	}
	
	/**
	 * Reads a array of bytes from open socket
	 * @param b - array of bytes
	 * @return number of read bytes.
	 */
	public synchronized  String read()throws IOException {
		   return reader.readLine();
	}
	
    /**
     * close a open socket
     * @return none.
     */
    public synchronized  void close() throws IOException {
         // fecha objeto de entrada
         in.close();
         // fecha objeto de saida
         out.close();
         // fecha o socket de comunicação
         this.close();
    }
            
    /**
     * Getter for property socket.
     * @return Value of property socket.
     */
    public java.net.Socket getSocket() {
        return socket;
    }
    
    /**
     * Setter for property socket.
     * @param socket New value of property socket.
     */
    public void setSocket(java.net.Socket socket) {
        this.socket = socket;
    }
    
}