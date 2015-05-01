package cmd;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import model.packetType.StatusOk;
import comp310f13.rmiChat.IStatusOk;
import comp310f13.rmiChat.ITextMessage;
import provided.datapacket.ADataPacket;
import provided.datapacket.ADataPacketAlgoCmd;
import provided.datapacket.DataPacket;
import provided.datapacket.ICmd2ModelAdapter;
/**
 * The ITextMessage cmd
 * @author Minsheng
 *
 */
public class TextMessageCmd extends ADataPacketAlgoCmd<ADataPacket, ITextMessage, Void>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6037185365804820746L;
	private Cmd2ModelAdapter adapter;
	@Override
	public ADataPacket apply(Class<?> index, DataPacket<ITextMessage> host,
			Void... params) {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm:ss");
		String time=dateFormat.format(host.getData().getTime());
		String s=time+" "+host.getData().getName()+": "+host.getData().getMsg();
		adapter.getMiniModel().appends(s+"\n");
		System.out.println(s);
		ADataPacket dp=new DataPacket<IStatusOk>(IStatusOk.class, adapter.getLocalUserStub(), new StatusOk());
		return dp;
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		this.adapter=(Cmd2ModelAdapter) cmd2ModelAdpt;
		
	}

}
