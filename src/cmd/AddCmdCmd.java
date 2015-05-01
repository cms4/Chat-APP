package cmd;

import model.MiniModel;
import model.packetType.StatusOk;
import comp310f13.rmiChat.IAddCmd;
import comp310f13.rmiChat.IStatusOk;
import provided.datapacket.ADataPacket;
import provided.datapacket.ADataPacketAlgoCmd;
import provided.datapacket.DataPacket;
import provided.datapacket.ICmd2ModelAdapter;
/**
 * AddCmdCmd, add a new command to the data packet processing visitor
 * @author Minsheng
 *
 */
public class AddCmdCmd extends ADataPacketAlgoCmd<ADataPacket, IAddCmd, Void>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8780089570009911617L;
	private Cmd2ModelAdapter adapter;
	@Override
	public ADataPacket apply(Class<?> index, DataPacket<IAddCmd> host,
			Void... params) {
		System.out.println("ADDCmd\n");
		
		@SuppressWarnings("unchecked")
		

		ADataPacketAlgoCmd<ADataPacket, Class<?>, Void> newCmd=
			(ADataPacketAlgoCmd<ADataPacket, Class<?>, Void>) host.getData().getNewCmd();
		newCmd.setCmd2ModelAdpt(adapter);
		MiniModel.algo.setCmd(host.getData().getID(), newCmd);
		
		adapter.getMiniModel().getCurrDefaultPacket().execute(MiniModel.algo);
		
		return new DataPacket<IStatusOk>(IStatusOk.class, adapter.getLocalUserStub(), new StatusOk());
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		this.adapter=(Cmd2ModelAdapter) cmd2ModelAdpt;
	}

}
