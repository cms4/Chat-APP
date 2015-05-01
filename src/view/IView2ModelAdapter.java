package view;
/**
 * The adapter connect the main model and main view
 * @author Minsheng
 *
 */
public interface IView2ModelAdapter {
	/**
	 * Create a chat room
	 * @param chatroomName
	 */
	public void createChatRoom(String chatroomName);
	/**
	 * Quit
	 */
	public void quit();
	/**
	 * Log in
	 * @param text
	 * @param hasLogout
	 */
	public void login(String text, Boolean hasLogout);
	/**
	 * Log out
	 */
	public void logout();
	/**
	 * Get the local IP
	 * @return
	 */
	public String getLocalIp();
	
}
