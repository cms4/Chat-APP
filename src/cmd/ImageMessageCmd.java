package cmd;

import java.io.Serializable;

import javax.swing.JLabel;

import comp310f13.rmiChat.IStatusFail;
import comp310f13.rmiChat.IStatusOk;
import model.packetType.IImageMessage;
import model.packetType.StatusFail;
import model.packetType.StatusOk;
import provided.datapacket.ADataPacket;
import provided.datapacket.ADataPacketAlgoCmd;
import provided.datapacket.DataPacket;
import provided.datapacket.ICmd2ModelAdapter;

public class ImageMessageCmd extends ADataPacketAlgoCmd<ADataPacket, IImageMessage, Void>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8426567503955411231L;
	private Cmd2ModelAdapter adapter;

	@Override
	public ADataPacket apply(Class<?> index, DataPacket<IImageMessage> host,
			Void... params) {
		ADataPacket dp;
		System.out.println("I HIT itTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
		try {
			JLabel label=new JLabel();
			label.setIcon(host.getData().getMsg());
			adapter.addComponent("", label);
			dp=new DataPacket<IStatusOk>(IStatusOk.class, adapter.getLocalUserStub(), new StatusOk());
			
		}
		catch(Exception e){
			e.printStackTrace();
			dp=new DataPacket<IStatusFail>(IStatusFail.class, adapter.getLocalUserStub(), new StatusFail());
		}
		return dp;
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		adapter=(Cmd2ModelAdapter) cmd2ModelAdpt;
	}

}
