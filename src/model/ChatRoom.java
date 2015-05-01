package model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import provided.datapacket.ADataPacket;
import comp310f13.rmiChat.IChatRoom;
import comp310f13.rmiChat.IUser;
/**
 * ChatRoom
 * @author Minsheng
 *
 */
public class ChatRoom implements IChatRoom{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2528966183976494942L;
	private String name;
	private List<IUser> userList=new ArrayList<IUser> ();
	/**
	 * Constructor
	 * @param name
	 */
	public ChatRoom(String name){
		this.name=name;
	}
	/**
	 * Get the name of the chat room
	 */
	@Override
	public String getName() {
		return name;
	}
	/**
	 * Add the local user stub
	 */
	@Override
	public void addLocalUser(IUser newUserStub) {
		java.util.Iterator<IUser> it=this.getUsers().iterator();
		int flag=0;
		while(it.hasNext()){
			try {
				if(it.next().getUUID().equals(newUserStub.getUUID())){
					flag=1;
					break;
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}	
		}
		if(flag==0){
			System.out.println("in addlocalUser Add new Stub");
			userList.add(newUserStub);
		}
	}
 /**
  * remove the user stub
  */
	@Override
	public void removeLocalUser(IUser userStub) {
//		java.util.Iterator<IUser> it=this.getUsers().iterator();
//		int flag=0;
//		while(it.hasNext()){
//			try {
//				if(it.next().getUUID()==userStub.getUUID()){
//					flag=1;
//					break;
//				}
//			} catch (RemoteException e) {
//				e.printStackTrace();
//			}	
//		}
//		if(flag==1){
//			userList.remove(userStub);
//		}
		userList.remove(userStub);
		
	}
 /**
  * Get all the users
  */
	@Override
	public Iterable<IUser> getUsers() {
		return userList;
	}
	
	public void setUserList(Iterable<IUser> users){
		java.util.Iterator<IUser> it=users.iterator();
		System.out.println("in setUserList");
		while(it.hasNext()){
			userList.add(it.next());
		}
	}
	/**
	 * Send message
	 */
	@Override
	public Iterable<ADataPacket> sendMessage(ADataPacket dp) {
		System.out.println("Call sendMessage in ChatRoom---");
		List<ADataPacket> packetList=new ArrayList<ADataPacket> ();
		java.util.Iterator<IUser> it=this.getUsers().iterator();
		while(it.hasNext()){
			try {
				List<UUID> theuuid = new ArrayList<UUID>();
				IUser tempUser = it.next();
				if(!tempUser.getUUID().equals(dp.getSender().getUUID())){
				System.out.println("before receive");
					if(theuuid.contains(tempUser.getUUID())==false){
					   packetList.add(tempUser.receiveData(dp));
					}
					theuuid.add(tempUser.getUUID());
					System.out.println("add Packet");
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
		}
		
		return packetList;
	}

}
