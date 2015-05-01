package model;


/**
 * The Adapter connect the MiniView and MiniModel
 * @author Minsheng
 *
 */
public interface MiniModel2ViewAdapter {
	/**
	 * Append the message to the view
	 * @param message
	 */
	public void append(String message);
	
}
