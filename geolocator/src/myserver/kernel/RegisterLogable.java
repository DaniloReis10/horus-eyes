/*
 * Logable.java
 *
 * Created on September 19, 2004, 10:17 PM
 */

package myserver.kernel;

/**
 *
 * @author  Danilo
 */
public interface RegisterLogable {
    public void addLog(String log);
    public RegisterLogable getInterface();
}