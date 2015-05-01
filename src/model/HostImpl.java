package model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

import model.packetType.AddUser;
import provided.datapacket.ADataPacket;
import provided.datapacket.DataPacket;
import comp310f13.rmiChat.IAddUser;
import comp310f13.rmiChat.IChatRoom;
import comp310f13.rmiChat.IHost;
import comp310f13.rmiChat.IUser;
import controller.MiniController;
/**
 * The implement of the IHost
 * @author Minsheng
 *
 */
public class HostImpl implements IHost{
	
	private IModel2ViewAdapter view;
	private IHost anotherHost;
	private UUID uuid = UUID.randomUUID();
	private String name;
	/**
	 * The constructor
	 * @param view
	 * @param username
	 */
	public HostImpl(IModel2ViewAdapter view, String username){
		this.view=view;
		this.name = username;
	}
	/**
	 * Get remote host
	 * @return
	 */
	public IHost getRemoteHost(){
		return this.anotherHost;
	}
	/**
	 * send local host stub
	 */
	@Override
	public void sendLocalHostStub(IHost localHostStub) throws RemoteException {
		anotherHost=localHostStub;
	}
  /**
   * Send chatroom invite info
   */
	@Override
	public boolean sendInvite(String chatroomInfo) throws RemoteException {
		return view.showsInvite(chatroomInfo);
	}

	/**
	 * Add to Chatroom
	 */
	@Override
	public boolean addToChatRoom(IChatRoom localChatRoom)
			throws RemoteException {
		
		java.util.Iterator<IUser> it = localChatRoom.getUsers().iterator();
		while(it.hasNext()){
			IUser user = it.next();
			System.out.println("names :::::" + user.getName() +":"+ user.getUUID() + "|||||" + this.getName() + ":" + this.getUUID());
			if(user.getUUID().equals(this.getUUID())){
				System.out.println("equal");
				return true;
			}else{
				System.out.println("fail equal");
			}
		}
		
			ChatRoom newChat=new ChatRoom(localChatRoom.getName());
			newChat.setUserList(localChatRoom.getUsers());
			
		    String nameOfChat=localChatRoom.getName();
		    System.out.println("nameOfChat="+nameOfChat);
		    IUser newUser=new User(uuid, name);
		    IUser myUser=(IUser)UnicastRemoteObject.exportObject(newUser, IUser.CONNECTION_PORT);
		    System.out.println("get User stub ss");
		    
		    
			MiniController newChatRM = new MiniController(view,newChat,myUser);
			view.addchatroom2(nameOfChat,newChatRM.getView());
			MainModel.CRlist.add(newChatRM);

			ADataPacket ds = new DataPacket<IAddUser> (IAddUser.class, myUser, new AddUser(myUser));
	
			System.out.println("in addtoChatRoom"+ds.getSender().getName());
			newChat.sendMessage(ds);
			
			newChat.addLocalUser(myUser);
		
		return true;
	}
	/**
	 * Get the name of the IHost
	 */
	@Override
	public String getName() throws RemoteException {
		return name;
	}
	/**
	 * Get the UUID of the host
	 */
	@Override
	public UUID getUUID() throws RemoteException {
		return uuid;
	}

}
