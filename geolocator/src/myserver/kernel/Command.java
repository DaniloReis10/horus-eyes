/*
 * Command.java
 *
 * Created on September 11, 2004, 5:23 PM
 */

package myserver.kernel;

/**
 *
 * @author  Danilo
 */
public class Command {
    /**
     * Commnad Code 0 - is for invalid command
     */
    int           commandCode;
    /**
     * String of main command
     */
    String            command;
    /**
     * array of parameters
     */
    String []          params;
    /**
     * Param Number
     */
    int               nParams;
    /**
     * Executed command
     */
    CommandExecute    cmdExec;
    
    final int  maxParams = 100;
    /** Creates a new instance of Command */
    public Command() {
        command     = new String();
        params      = new String[maxParams];
        nParams     = 0;
        commandCode = 0;
    }
    
    /**
     * Getter for property command.
     * @return Value of property command.
     */
    public java.lang.String getCommand() {
        return command;
    }
    
    /**
     * Setter for property command.
     * @param command New value of property command.
     */
    public void setCommand(java.lang.String command) {
        this.command = command;
    }
    
    /**
     * Getter for property commandCode.
     * @return Value of property commandCode.
     */
    public int getCommandCode() {
        return commandCode;
    }
    
    /**
     * Setter for property commandCode.
     * @param commandCode New value of property commandCode.
     */
    public void setCommandCode(int commandCode) {
        this.commandCode = commandCode;
    }
    
    /**
     * Getter for property nParams.
     * @return Value of property nParams.
     */
    public int getNParams() {
        return nParams;
    }
    
    /**
     * Setter for property nParams.
     * @param nParams New value of property nParams.
     */
    public void setNParams(int nParams) {
        this.nParams = nParams;
    }
    
    /**
     * Getter for property params.
     * @return Value of property params.
     */
    public java.lang.String[] getParams() {
        return this.params;
    }
    
    /**
     * Setter for property params.
     * @param params New value of property params.
     */
    public void setParams(java.lang.String[] params) {
        this.params = params;
    }
    /**
     *
     */
    public String getParam(int index){
        return params[index];
    }
    /**
     *
     */
    public int getParamNumber(){
        return nParams;
    }
    /**
     * 
     * @param cmd
     */
    public void setCmdExec(CommandExecute cmd){
    	this.cmdExec = cmd;
    }
    /**
     * 
     */
    public CommandExecute getCmdExec(){
    	return this.cmdExec;
    }
}