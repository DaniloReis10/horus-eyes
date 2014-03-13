/*
 * Created on Jul 2, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package myserver.kernel;

/**
 * @author Danilo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CommandDecode {
    private String          commandFormat;
    private int             commandParams;
    private CommandExecute   commandExecs;
    private int                      code;

	/**
	 * 
	 */
	public CommandDecode(int code,String commandFormat,int commandParams,CommandExecute commandExecs) {
		super();
		this.setCode(code);
		this.setCommandFormat(commandFormat);
		this.setCommandParams(commandParams);
		this.setCommandExecs(commandExecs);
	}

	/**
	 * @param commandFormat The commandFormat to set.
	 */
	void setCommandFormat(String commandFormat) {
		this.commandFormat = commandFormat;
	}

	/**
	 * @return Returns the commandFormat.
	 */
	String getCommandFormat() {
		return commandFormat;
	}

	/**
	 * @param commandParams The commandParams to set.
	 */
	void setCommandParams(int commandParams) {
		this.commandParams = commandParams;
	}

	/**
	 * @return Returns the commandParams.
	 */
	int getCommandParams() {
		return commandParams;
	}

	/**
	 * @param commandExecs The commandExecs to set.
	 */
	void setCommandExecs(CommandExecute commandExecs) {
		this.commandExecs = commandExecs;
	}

	/**
	 * @return Returns the commandExecs.
	 */
	CommandExecute getCommandExecs() {
		return commandExecs;
	}

	/**
	 * @param code The code to set.
	 */
	void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return Returns the code.
	 */
	int getCode() {
		return code;
	}

}