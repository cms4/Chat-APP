package model.packetType;

import java.util.Date;

import comp310f13.rmiChat.ITextMessage;
/**
 * TextMessage packet type
 * @author Minsheng
 *
 */
public class TextMessage implements ITextMessage{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3984081741230715879L;
	private String text;
	private String name;
	public TextMessage(String text, String name){
		this.text=text;
		this.name=name;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public Date getTime() {
		Date date = new Date();
		return date;
	}

	@Override
	public String getMsg() {
		return text;
	}

}
