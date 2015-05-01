package cmd;

import model.packetType.StatusOk;
import comp310f13.rmiChat.IAddUser;
import comp310f13.rmiChat.IStatusOk;
import provided.datapacket.ADataPacket;
import provided.datapacket.ADataPacketAlgoCmd;
import provided.datapacket.DataPacket;
import provided.datapacket.ICmd2ModelAdapter;
/**
 * Add the user cmd
 * @author Minsheng
 *
 */
public class AddUserCmd extends ADataPacketAlgoCmd<ADataPacket, IAddUser, Void>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6379061924827229736L;
	private Cmd2ModelAdapter adapter;
	@Override
	public ADataPacket apply(Class<?> index, DataPacket<IAddUser> host,
			Void... params) {
		adapter.getMiniModel().getChatRoom().addLocalUser(host.getData().getUser());
		ADataPacket dp=new DataPacket<IStatusOk>(IStatusOk.class, adapter.getLocalUserStub(), new StatusOk());
		return dp;
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		this.adapter=(Cmd2ModelAdapter) cmd2ModelAdpt;
		
	}

}
