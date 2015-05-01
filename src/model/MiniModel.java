package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import cmd.AddCmdCmd;
import cmd.AddUserCmd;
import cmd.Cmd2ModelAdapter;
import cmd.DefaultCmd;
import cmd.ImageMessageCmd;
import cmd.RemoveUserCmd;
import cmd.RequestCmdCmd;
import cmd.StatusFailCmd;
import cmd.StatusOkCmd;
import cmd.TextMessageCmd;
import model.packetType.IImageMessage;
import model.packetType.ImageMessage;
import model.packetType.RemoveUser;
import model.packetType.TextMessage;
import provided.datapacket.ADataPacket;
import provided.datapacket.DataPacket;
import provided.datapacket.DataPacketAlgo;
import provided.rmiUtils.IRMIUtils;
import provided.rmiUtils.RMIUtils;
import provided.util.IVoidLambda;
import comp310f13.rmiChat.IAddCmd;
import comp310f13.rmiChat.IAddUser;
import comp310f13.rmiChat.IHost;
import comp310f13.rmiChat.IRemoveUser;
import comp310f13.rmiChat.IRequestCmd;
import comp310f13.rmiChat.IStatusFail;
import comp310f13.rmiChat.IStatusOk;
import comp310f13.rmiChat.ITextMessage;
import comp310f13.rmiChat.IUser;
/**
 * The MiniModel
 * @author Minsheng
 *
 */
public class MiniModel {
	
	private MiniModel2ViewAdapter view;
	private IUser user;
	private ChatRoom myChatRoom;
	private IModel2ViewAdapter themainAdapter;
	private IHost anotherHost;
	private IVoidLambda<String> outputCmd = new IVoidLambda<String> (){
		
		@Override
		public void apply(String... params) {
			//for(String s: params)view.append(s);
		}
	};
	private IRMIUtils rmiUtils = new RMIUtils(outputCmd);
	//default cmd
	public static DataPacketAlgo<ADataPacket, Void> algo;
	/**
	 * The Constructor
	 * @param view
	 * @param mainAdapter
	 * @param chatRoom
	 * @param user
	 */
	
	public MiniModel(MiniModel2ViewAdapter view, IModel2ViewAdapter mainAdapter, ChatRoom chatRoom, IUser user){
		this.view=view;
		this.themainAdapter = mainAdapter;
		this.user=user;
		myChatRoom = chatRoom;
		Cmd2ModelAdapter cmdAdapter=new Cmd2ModelAdapter(this);
		
		ImageMessageCmd imageCmd=new ImageMessageCmd();
		imageCmd.setCmd2ModelAdpt(cmdAdapter);
		/**
		 * The DefaultCmd
		 */
		DefaultCmd defaultCmd=new DefaultCmd();
		defaultCmd.setCmd2ModelAdpt(cmdAdapter);
		/**
		 * The StatusOkCmd
		 */
		StatusOkCmd iStatusOkCmd=new StatusOkCmd();
		iStatusOkCmd.setCmd2ModelAdpt(cmdAdapter);
		/**
		 * The StatusFailCmd
		 */
		StatusFailCmd iStatusFailCmd=new StatusFailCmd();
		iStatusFailCmd.setCmd2ModelAdpt(cmdAdapter);
		/**
		 * The TextMessageCmd
		 */
		TextMessageCmd textMessageCmd=new TextMessageCmd();
		textMessageCmd.setCmd2ModelAdpt(cmdAdapter);
		/**
		 * The AddUserCmd
		 */
		AddUserCmd addUserCmd=new AddUserCmd();
		addUserCmd.setCmd2ModelAdpt(cmdAdapter);
		/**
		 * The RemoveUserCmd
		 */
		RemoveUserCmd removeUserCmd=new RemoveUserCmd();
		removeUserCmd.setCmd2ModelAdpt(cmdAdapter);
		/**
		 * The AddCmdCmd
		 */
		AddCmdCmd addCmd=new AddCmdCmd();
		addCmd.setCmd2ModelAdpt(cmdAdapter);
		/**
		 * The RequestCmdCmd
		 */
		RequestCmdCmd requestCmd=new RequestCmdCmd();
		requestCmd.setCmd2ModelAdpt(cmdAdapter);
		/**
		 * Add the default cmd
		 */
		algo=new DataPacketAlgo<ADataPacket, Void>(defaultCmd);
		/**
		 * Add the IAddCmd
		 */
		algo.setCmd(IAddCmd.class, addCmd);
		/**
		 * Add the IAddUser
		 */
		algo.setCmd(IAddUser.class, addUserCmd);
		/**
		 * Add the IRemoveUser
		 */
		algo.setCmd(IRemoveUser.class, removeUserCmd);
		/**
		 * Add the ITextMessage
		 */
		algo.setCmd(ITextMessage.class, textMessageCmd);
		/**
		 * Add the IRequestCmd
		 */
		algo.setCmd(IRequestCmd.class, requestCmd);
		/**
		 * Add the IStatusOkCmd
		 */
		algo.setCmd(IStatusOk.class, iStatusOkCmd);
		/**
		 * Add the IStatusFailCmd
		 */
		algo.setCmd(IStatusFail.class, iStatusFailCmd);
		
		algo.setCmd(IImageMessage.class, imageCmd);
	}
	/**
	 * Get the name of the chatroom
	 * @return
	 */
	public String getCRname() {
		return myChatRoom.getName();
	}
    /**
     * Start the model, do nothing
     */
	public void start(){
	}
	/**
	 * Get remote IHost
	 * @param remoteHost
	 */
	public void setAnotherStub(String remoteHost){
		try {
			Registry remoteRegistry = rmiUtils.getRemoteRegistry(remoteHost);
			this.anotherHost = (IHost) remoteRegistry.lookup(IHost.BOUND_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Connect to the remoteHost	
	 * @param remoteHost
	 */
	public void connectTo(String remoteHost) {
		setAnotherStub(remoteHost);
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					String chatroomInfo = "Invited by: "+ user.getName() + " To chatRoom:" + myChatRoom.getName();
					if(anotherHost.sendInvite(chatroomInfo)){
						//System.out.println("Return true");
						view.append(anotherHost.getName()+" has joined the chatRoom \n");
						anotherHost.addToChatRoom(myChatRoom);
					}else{
						view.append(anotherHost.getName()+" has rejected the invition \n");
					}
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
			
		}).start();
		
	}
	
	/**
	 * quit the chatroom, remove IUser
	 * @param content
	 */
	public void quit(JComponent content){
		myChatRoom.removeLocalUser(user);
		ADataPacket ds = new DataPacket<IRemoveUser> (IRemoveUser.class, user, new RemoveUser(this.user));
		myChatRoom.sendMessage(ds);
		this.themainAdapter.leavechatroom(content);
	}
   /**
    * Send text message 
    * @param text
    */
	public void sendMessage(String text) {
		try {
			String name=user.getName();
			ADataPacket da = new DataPacket<ITextMessage> (ITextMessage.class, user, new TextMessage(text, name));
			Iterable<ADataPacket> list=myChatRoom.sendMessage(da);
			java.util.Iterator<ADataPacket> it=list.iterator();
			ADataPacket curr;
			while(it.hasNext()){
				curr=it.next();
				curr.execute(algo);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Get current IUser
	 * @return
	 */
	public IUser getUser(){
		return this.user;
	}
	/**
	 * Get the chatroom
	 * @return
	 */
	public ChatRoom getChatRoom(){
		return this.myChatRoom;
	}
	/**
	 * Add a string s to the view
	 * @param s
	 */
	public void appends(String s){
		view.append(s);
	}
	public void sendImage(String path){
		BufferedImage img;
		try {
			img = ImageIO.read(new File(path));
			ImageIcon icon=new ImageIcon(img);
			ADataPacket da = new DataPacket<IImageMessage> (IImageMessage.class, user, new ImageMessage(icon));
			Iterable<ADataPacket> list=myChatRoom.sendMessage(da);
			
			java.util.Iterator<ADataPacket> it=list.iterator();
			
			ADataPacket curr;
			while(it.hasNext()){
				System.out.println("dfsdf");
				curr=it.next();
				curr.execute(algo);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private DataPacket<Object> currPacket;
	
	public void setCurrDefaulPackt(DataPacket<Object> host) {
		this.currPacket=host;
		
	}
	public DataPacket<Object> getCurrDefaultPacket(){
		return this.currPacket;
	}
	
}
