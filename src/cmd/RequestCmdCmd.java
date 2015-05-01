package cmd;

import java.rmi.RemoteException;

import model.MiniModel;
import model.packetType.AddCmd;
import comp310f13.rmiChat.IAddCmd;
import comp310f13.rmiChat.IRequestCmd;
import provided.datapacket.ADataPacket;
import provided.datapacket.ADataPacketAlgoCmd;
import provided.datapacket.DataPacket;
import provided.datapacket.ICmd2ModelAdapter;
/**
 * Request cmd cmd
 * @author Minsheng
 *
 */
public class RequestCmdCmd extends ADataPacketAlgoCmd<ADataPacket, IRequestCmd, Void>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9056745353109381372L;
	private Cmd2ModelAdapter adapter;
	@SuppressWarnings("unchecked")
	@Override
	public ADataPacket apply(Class<?> index, DataPacket<IRequestCmd> host,
			Void... params) {
		System.out.println("RequestCmd\n");
		//@SuppressWarnings("unchecked")
		System.out.println("index="+index);
		System.out.println("host.getData().getClass()="+host.getData().getClass());
		
		AddCmd addCmd=new AddCmd(host.getData().getID());
		
		ADataPacket ds=new DataPacket<IAddCmd>(IAddCmd.class, adapter.getLocalUserStub(), addCmd);
		
		try {
			host.getSender().receiveData(ds);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ds;
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		this.adapter=(Cmd2ModelAdapter) cmd2ModelAdpt;
		
	}

}
