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
public abstract class CommandExecute {

	/**
	 * 
	 */
	public CommandExecute() {
		super();
	}
	abstract public String execute(String param[]);
}