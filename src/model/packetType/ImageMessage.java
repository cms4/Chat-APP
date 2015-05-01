package model.packetType;


import javax.swing.ImageIcon;

/**
 * IMageMessage
 * @author Minsheng
 *
 */
public class ImageMessage implements IImageMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5946611547908021747L;
	private ImageIcon file;
	
	public ImageMessage(ImageIcon file){
		this.file=file;
	}

	@Override
	public ImageIcon getMsg() {
		return this.file;
	}

}
