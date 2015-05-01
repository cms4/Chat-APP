package model;

import java.rmi.RemoteException;
import java.util.UUID;
import provided.datapacket.ADataPacket;
import comp310f13.rmiChat.IUser;
/**
 * The concrete class implements IUser
 * @author Minsheng
 *
 */
public class User implements IUser{
	
	private String name;
	private UUID uuid;
	/**
	 * The constructor
	 * @param id
	 * @param name
	 */
	public User(UUID id, String name){
		this.uuid=id;
		this.name=name;
		
	}
	/**
	 * Get the name of the Iuser
	 */
	@Override
	public String getName() throws RemoteException {
		return name;
	}
	/**
	 * Get the UUID of the IUser
	 */
	@Override
	public UUID getUUID() throws RemoteException {
		return uuid;
	}
/**
 * Receive Data
 */
	@Override
	public ADataPacket receiveData(ADataPacket dp) throws RemoteException {
		return dp.execute(MiniModel.algo);
	}
	

}
