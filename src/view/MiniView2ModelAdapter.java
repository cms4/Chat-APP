package view;
/**
 * The adapter connect the miniview and the miniModel
 * @author Minsheng
 *
 */
public interface MiniView2ModelAdapter {
	/**
	 * Quit the application by stopping the model.
	 */
	public void quit();
	/**
	 * send Invitation
	 * @param remoteHost
	 */
	public void sendInvitation(String remoteHost);
	/**
	 * Send message
	 * @param text
	 */
	public void sendMessage(String text);
	public void sendImage(String absolutePath);
}
