/*
 * ParserInterface.java
 *
 * Created on September 10, 2004, 3:33 PM
 */

package myserver.kernel;

/**
 * Interface to define parser object
 * @author  Danilo
 */
interface Parsernable {
    /**
     * Parsers the command lie
     * @param commandline - line with command and params separates by commas
     * @return true - parsers is OK
     * @return false - invalid command or sintaxe error
     */
    public Command parser(String inputCommandLine);
    /**
     * Format a command line for a valid command
     * @param Command
     * @param params - params separates by commas
     * @return formated command line.
     */
    public String format(String command,String parameters);
    
    public Parsernable parserInterface();
    
}