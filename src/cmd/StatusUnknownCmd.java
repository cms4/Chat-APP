package cmd;

import comp310f13.rmiChat.IStatusOk;
import provided.datapacket.ADataPacket;
import provided.datapacket.ADataPacketAlgoCmd;
import provided.datapacket.DataPacket;
import provided.datapacket.ICmd2ModelAdapter;
/**
 * Status OK cmd
 * @author Minsheng
 *
 */
public class StatusUnknownCmd extends ADataPacketAlgoCmd<ADataPacket, IStatusOk, Void>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8930381790324400142L;
	private Cmd2ModelAdapter adapter;


	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		this.adapter=(Cmd2ModelAdapter) cmd2ModelAdpt;
		
	}


	@Override
	public ADataPacket apply(Class<?> index, DataPacket<IStatusOk> host,
			Void... params) {
	
		adapter.append("Datapackage has been send, but unknown type to remote system!");
		return null;
	}
	

}
