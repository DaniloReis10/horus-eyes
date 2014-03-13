package myserver.kernel;

import java.util.StringTokenizer;
import java.util.Vector;

/**
 *  This class is responsable for parser and interpreter command for server.
 *
 *  Commnad list and Format
 *  <command>param0,param1,....paramN,</command>
 */
public class CommandParser implements Parsernable{
    /**
     * Set of sub-String separates by commas in original ccommand line
     */
    StringTokenizer   commandLine;
    static int               code;    
    static CommandParser instance;
    static Vector  commandDecoder;
    static
    {
        commandDecoder = new Vector(); 
        code           = 0;
    }
    /**
     * Creates a new instance of command parser
     * @param None.
     * @return None.
     */
    public CommandParser(){
        instance       = this;
    }
    /**
     * 
     * @param command
     * @param nParams
     */
    public static void addCommand(String command,int nParams,CommandExecute cmdExec){
    	CommandDecode  cmdDecode;
    	
    	code++;
    	cmdDecode = new CommandDecode(code,command,nParams,cmdExec);
    	commandDecoder.add(cmdDecode);
    }
    /**
     * 
     * @param cmd
     * @return
     */
    public static CommandDecode findCommandDecoder(String cmd){
    	int                i;
    	CommandDecode cmdDec;
    	
    	for(i = 0; i < commandDecoder.size(); i++){
    		cmdDec = (CommandDecode)commandDecoder.get(i);
    		if(cmdDec.getCommandFormat().compareTo(cmd) == 0){
    			return cmdDec;
    		}
    	}
    	return null;
    }
    public static CommandDecode findCommandDecoder(int code){
    	int                i;
    	CommandDecode cmdDec;
    	
    	for(i = 0; i < commandDecoder.size(); i++){
    		cmdDec = (CommandDecode)commandDecoder.get(i);
    		if(cmdDec.getCode() == code){
    			return cmdDec;
    		}
    	}
    	return null;
    }
    /**
     * Parsers the command lie
     * @param commandline - line with command and params separates by commas
     * @return true - parsers is OK
     * @return false - invalid command or sintaxe error
     */
    public Command parser(String inputCommandLine) {
        int                i,n;
        Command            cmd;
        CommandExecute cmdExec;
        CommandDecode   cmdDec;
        
        cmd = new Command();
        if((inputCommandLine == null)||(inputCommandLine.compareTo("")==0)){
            return null;
        }
        commandLine = new StringTokenizer(inputCommandLine,",");
        n = commandLine.countTokens();
        String[] elem = new String[n];
        for(i=0;commandLine.hasMoreTokens();i++){
            elem[i] = commandLine.nextToken();
        }
        // verifica se o comando existe
        cmd.setCommandCode(this.checkCommandSintax(elem[0],elem[n - 1]));
        // Caso que o comando existe
        if (cmd.getCommandCode() != 0){
            cmd.setNParams(n - 2);
            cmd.setCommand(elem[0]);
            for(i=1; i < n - 1;i++){
                cmd.getParams()[i - 1] = elem[i];
            }
            cmdDec = CommandParser.findCommandDecoder(cmd.getCommandCode());
            cmdExec = cmdDec.getCommandExecs();
            cmd.setCmdExec( cmdExec);
            return cmd;
        }
        return null;
    }
    /**
     * Verifies if the main command in command line is a valid command
     * @param command0 - main command
     * @param command1 - terminator of main command
     * @return 0 - invalid command
     * @return command code ( 1 - 10)
     */
    private int checkCommandSintax(String command0,String command1){
        int                 i;
        StringBuffer    compl;
        String         compls;
        CommandDecode  cmdDec;
        
        // monta o terminador do comando
        compl = new StringBuffer(command0);
        compl.insert(1,'/');
        compls = compl.toString();
        // Busca na lista de comandos validos
        cmdDec = CommandParser.findCommandDecoder(command0);
        if(cmdDec != null){
        	return cmdDec.getCode(); 
        }
        return 0;
    }
    /**
     * check if the number of params match with requiment param number of command
     * @param code - command code
     * @param n - number of params
     * @return true - param and command matchs
     * @return false - param and command doesn't matchs
     */
    private boolean checkCommandParams(int code,int params){
        CommandDecode cmdDec;
    	
    	// verifica se o codigo e valido
    	if (code == 0){
    		return false;
    	}
    	// verifica se nao precisa checar numero de parametros
    	// ou numero de parametros confere com os parametros esperados
    	cmdDec = CommandParser.findCommandDecoder(code);
    	if( cmdDec != null){
    		if((cmdDec.getCommandParams()== -1)||(cmdDec.getCommandParams()== params)){
    			return true;
    		}
    	}
        return false;
        
    }
    /**
     * Check if the command is a valid command
     * @param command
     * @return command code
     * @return 0 - invalid command
     */
    private int checkValidCommand(String command){
        CommandDecode  cmdDec;
        
        // Busca na lista de comandos validos
        cmdDec = CommandParser.findCommandDecoder(command);
        if ( cmdDec == null){
        	return 0;
        }
        return cmdDec.getCode();
    }
    /**
     * Format a command line for a valid command
     * @param Command
     * @param params - params separates by commas
     * @return formated command line.
     */
    public String format(String command,String parameters) {
        int                 n,code;
        boolean                  b;
        StringTokenizer paramsLine;
        StringBuffer         compl;
        StringBuffer        result;
        
        
        // verifica se o comando e valido
        code = this.checkValidCommand(command);
        if ( code == 0)
            return null;
        
        // conta o numero de parametros
        paramsLine = new StringTokenizer(parameters,",");
        n = paramsLine.countTokens();
        
        b = this.checkCommandParams(code, n);
        // caso de erro no numero de parametros para o comando
        if ( b == false)
            return null;
        
        // monta a string do comando
        result = new StringBuffer(command);
        // monta o terminador do comando
        compl  = new StringBuffer(command);
        compl.insert(1,'/');
        // adiciona parametros
        result.append(parameters);
        // adiciona terminador
        result.append(compl);
        
        return result.toString();
    }
    /**
     *
     */
    public static CommandParser getInstance(){
        return instance;
    }
    /**
     *
     */
    public Parsernable parserInterface() {
        
        return this;
    }
    
}