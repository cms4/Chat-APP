package model.packetType;

import provided.datapacket.ADataPacket;
import comp310f13.rmiChat.IStatusFail;
/**
 * The status fail packet
 * @author Minsheng
 *
 */
public class StatusFail implements IStatusFail{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8984877043397472525L;

	@Override
	public String getMsg() {
		
		return null;
	}

	@Override
	public ADataPacket getDataPacket() {
		
		return null;
	}

}
