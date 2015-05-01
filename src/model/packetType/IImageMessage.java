package model.packetType;

import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * The imagemessage interface
 * @author Minsheng
 *
 */
public interface IImageMessage extends Serializable{
	
	
	/**
	 * The image message being sent.
	 * @return A image
	 */
	public ImageIcon getMsg();
}
