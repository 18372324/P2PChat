package controller;

/**
 * an interface for transmit message
 * 
 * @author Aisingioro
 *
 */
public interface Sendable {
	
	/**
	 * send msg and display it in the log window
	 * 
	 * @param msg
	 */
	public void logMessage(String msg);
	
	/**
	 * pop an error prompt
	 * 
	 * @param msg
	 */
	public void showErrorMessage(String msg);
	
}
