package model.packetType;

import comp310f13.rmiChat.IRemoveUser;
import comp310f13.rmiChat.IUser;
/**
 * Remove User packet type
 * @author Minsheng
 *
 */
public class RemoveUser implements IRemoveUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 151685261814915332L;
	private IUser user;
	/**
	 * Constructor
	 * @param user
	 */
	public RemoveUser(IUser user){
		this.user=user;
	}
	/**
	 * Get user
	 */
	@Override
	public IUser getUser() {
		
		return user;
	}

}
