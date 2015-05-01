package model.packetType;

import comp310f13.rmiChat.IAddUser;
import comp310f13.rmiChat.IUser;
/**
 * The addUser packet type
 * @author Minsheng
 *
 */
public class AddUser implements IAddUser{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8777631607027467623L;
	private IUser user;
	public AddUser(IUser user){
		this.user=user;
	}
	@Override
	public IUser getUser() {
		return this.user;
	}

}
