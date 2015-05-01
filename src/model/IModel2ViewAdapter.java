package model;

import javax.swing.JComponent;

/**
 * The adapter connect the Main model and Main view
 * @author Minsheng
 *
 */
public interface IModel2ViewAdapter {
	/**
	 * Show the invite info
	 * @param invitor
	 * @return
	 */
	public boolean showsInvite(String invitor);
	/**
	 * Append status in the view
	 * @param s
	 */
	public void append(String s);
	/**
	 * Add the chatroom
	 * @param miniview
	 */
	public void addchatroom(JComponent miniview);
	/**
	 * Leave chatroom
	 * @param miniview
	 */
	public void leavechatroom(JComponent miniview);
	/**
	 * add to the chat room
	 * @param nameOfChat
	 * @param view
	 */
	public void addchatroom2(String nameOfChat, JComponent view);
}
