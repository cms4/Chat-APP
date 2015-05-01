package cmd;

import comp310f13.rmiChat.IStatusFail;
import provided.datapacket.ADataPacket;
import provided.datapacket.ADataPacketAlgoCmd;
import provided.datapacket.DataPacket;
import provided.datapacket.ICmd2ModelAdapter;
/**
 * Status Fail cmd
 * @author Minsheng
 *
 */
public class StatusFailCmd extends ADataPacketAlgoCmd<ADataPacket, IStatusFail, Void>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7001769671104254665L;
	private Cmd2ModelAdapter adapter;
	@Override
	public ADataPacket apply(Class<?> index, DataPacket<IStatusFail> host,
			Void... params) {
		
		adapter.append("Datapackage was failed to be send!");
		return null;
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		this.adapter=(Cmd2ModelAdapter) cmd2ModelAdpt;
		
	}

}
