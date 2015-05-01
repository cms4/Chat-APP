package cmd;

import model.packetType.StatusOk;
import comp310f13.rmiChat.IRemoveUser;
import comp310f13.rmiChat.IStatusOk;
import provided.datapacket.ADataPacket;
import provided.datapacket.ADataPacketAlgoCmd;
import provided.datapacket.DataPacket;
import provided.datapacket.ICmd2ModelAdapter;
/**
 * Remove user cmd
 * @author Minsheng
 *
 */
public class RemoveUserCmd extends ADataPacketAlgoCmd<ADataPacket, IRemoveUser, Void>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8399067702105613305L;
	private Cmd2ModelAdapter adapter;
	@Override
	public ADataPacket apply(Class<?> index, DataPacket<IRemoveUser> host,
			Void... params) {
		adapter.getMiniModel().getChatRoom().removeLocalUser(host.getSender());
		
		ADataPacket dp=new DataPacket<IStatusOk>(IStatusOk.class, adapter.getLocalUserStub(), new StatusOk());
		return dp;
		
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		adapter=(Cmd2ModelAdapter) cmd2ModelAdpt;
	}

}
