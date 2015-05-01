package model.packetType;

import comp310f13.rmiChat.IRequestCmd;
/**
 * Request cmd packet type
 * @author Minsheng
 *
 */
public class RequestCmd implements IRequestCmd{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6229444902891917700L;

	private Class<?> id;
	/**
	 * Constructor
	 * @param d
	 */
	public RequestCmd(Class<?> d){
		this.id=d;
	}
	/**
	 * Get the ID
	 */
	@Override
	public Class<?> getID() {
		
		return id;
	}

}
