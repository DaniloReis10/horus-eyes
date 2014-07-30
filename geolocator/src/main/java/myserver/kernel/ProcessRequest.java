/*
 * ProcessRequest.java
 *
 * Created on September 13, 2004, 8:13 AM
 */

package myserver.kernel;

import java.io.IOException;
import java.net.Socket;

/**
 * This class is responsable for perform all request from a client application
 * @author  Danilo
 */
public class ProcessRequest implements Runnable{
    /**
     * Command result
     */
    private String                commandResult;
    /**
     * Object thats parser received commands
     */
    private  CommandParser                parser;
    /**
     * Communication estabilished link
     */
    private SocketLink                      link;
    /**
     * variable to stop the service to client
     */
    private boolean                         stop;
    
    
    /**
     * Creates a new instance of ProcessRequest
     */
    public ProcessRequest(SocketLink link) {
        // creates card controller object
        // cria objeto parser
        parser            = new CommandParser();
        setCommandResult(new String());
        this.link         = link;
        this.stop         = true;
    }
    /**
     * procedure to answer client request
     */
    public void run() {
        String                 request;
        String                  answer;
        Parsernable      parserEnginee;
        RegisterLogable            log;
        ServerService           server;
        Command                    cmd;
        Socket                  socket;
        String              connection;
        
        // buffer to receive request
        parserEnginee = parser.parserInterface();
        server        = ServerService.getInstance();
        log           = server.getInterface();
        
        try{
            // pega o socket
            socket = link.getSocket();
            connection = " - " + socket.getInetAddress().getHostName() + " ( " +socket.getInetAddress().getHostAddress()+" )"; 
            // adiciona na lista de conexoes
            server.addNewConnection(true,connection);
            
            stop = false;
            while (!stop) {
                // ler quantidade de bytes recebidas pela conexao
                // ler bytes ate que nao cheguem mais bytes
                request   = link.read();
                if ( request != null){
                    log.addLog("Estabilished conection");
                    log.addLog("Received bytes  = " + request.length());
                    log.addLog("Command Request = " + request);
                    // pega o comando a ser executado
                    cmd = parserEnginee.parser(request);
                    // Faz o parser do comando
                    if(cmd != null){
                        // faz a execu����o do comando
                        answer = this.processCommnad(cmd);
                        // envia a resposta
                        if ( answer != null){
//                            System.out.println("Command Response = " + answer);
                            log.addLog("Command Response = " + answer);
                            link.write(answer);
                        }
                    }
                    else{
                        // caso de error
                        log.addLog("Invalid Command");
                        link.write("Invalid Command");
                        
                    }
                }
                try {
                    Thread.sleep(500);
                }
                catch (Exception e) {
                }
            }
            // fecha o link de comunica����o
            link.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
            log.addLog(e.getMessage());
        }
        
    }
    /**
     * Performs apropriate command and generates a responce.
     * @param commandCode - Parsed command code.
     */
    public String processCommnad(Command cmd) {
        String    resp;
        
        resp = cmd.getCmdExec().execute(cmd.getParams());
        return resp;
    }
    
    /**
     * Getter for property stop.
     * @return Value of property stop.
     */
    public boolean isStop() {
        return stop;
    }
    
    /**
     * Setter for property stop.
     * @param stop New value of property stop.
     */
    public void setStop(boolean stop) {
        this.stop = stop;
    }
	public String getCommandResult() {
		return commandResult;
	}
	public void setCommandResult(String commandResult) {
		this.commandResult = commandResult;
	}
    
}