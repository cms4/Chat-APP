package cmd;

import model.packetType.RequestCmd;
import comp310f13.rmiChat.IRequestCmd;
import provided.datapacket.ADataPacket;
import provided.datapacket.ADataPacketAlgoCmd;
import provided.datapacket.DataPacket;
import provided.datapacket.ICmd2ModelAdapter;
/**
 * The default cmd 
 * @author Minsheng
 *
 */
public class DefaultCmd extends ADataPacketAlgoCmd<ADataPacket, Object, Void> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8933622951339982269L;
	private Cmd2ModelAdapter adapter;

	@Override
	public ADataPacket apply(Class<?> index, DataPacket<Object> host,
			Void... params) {
		adapter.append("a unknown packet\n");
		System.out.println("index="+index);
		System.out.println("host.getData().getClass()="+host.getData().getClass());
		adapter.getMiniModel().setCurrDefaulPackt(host);
		ADataPacket ds = new DataPacket<IRequestCmd>(IRequestCmd.class,
				adapter.getLocalUserStub(), new RequestCmd(index));
		return ds;
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		adapter = (Cmd2ModelAdapter) cmd2ModelAdpt;

	}

}
