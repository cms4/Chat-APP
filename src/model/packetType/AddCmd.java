package model.packetType;

import cmd.ImageMessageCmd;
import model.MiniModel;
import provided.datapacket.ADataPacket;
import provided.datapacket.ADataPacketAlgoCmd;
import comp310f13.rmiChat.IAddCmd;
/**
 * AddCmd packet type
 * @author Minsheng
 *
 */
public class AddCmd implements IAddCmd {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6132823083759502705L;
	private Class<?> id;
	private ADataPacketAlgoCmd<ADataPacket, ?, ?> data;
	public AddCmd(Class<?> x){
		this.id=x;
		ImageMessageCmd imageCmd=new ImageMessageCmd();
		this.data=imageCmd;
		//System.out.println("anme" + data.getClass());
	}
	@Override
	public Class<?> getID() {
		return this.id;
	}

	@Override
	public ADataPacketAlgoCmd<ADataPacket, ?, ?> getNewCmd() {
		if(data==null){
			System.out.println("algo is null");
		}else{
			System.out.println("algo has something");
		}
		return data;
	}

}
