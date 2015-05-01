package model;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import provided.rmiUtils.IRMIUtils;
import provided.rmiUtils.IRMI_Defs;
import provided.rmiUtils.RMIUtils;
import provided.util.IVoidLambda;
import comp310f13.rmiChat.IHost;
import comp310f13.rmiChat.IUser;
import controller.MiniController;
/**
 * The Main Model
 * @author Minsheng
 *
 */
public class MainModel {
	
	private IModel2ViewAdapter view;
	private IHost host;
	private Registry registry;
	private String username;
	public static ArrayList<MiniController> CRlist;
	
	private IVoidLambda<String> outputCmd = new IVoidLambda<String> (){
		public void apply(String... strs){
			for(String s: strs)System.out.println(s);
		}
	};
	/**
	 * The constructor
	 * @param view
	 */
	public MainModel(IModel2ViewAdapter view){
		CRlist = new ArrayList<MiniController>();
		this.view=view;
		
	}
	
	/**
	 * Utility object used to get the Registry
	 */
	private IRMIUtils rmiUtils = new RMIUtils(outputCmd);
	
	/**
	 * Start the model
	 * @param haveLogout
	 */
	public void start(Boolean haveLogout){
		rmiUtils.startRMI(IRMI_Defs.CLASS_SERVER_PORT_SERVER);
		try{
			IHost host1=new HostImpl(view,username);
			host=(IHost)UnicastRemoteObject.exportObject(host1, IHost.CONNECTION_PORT);
			
			if(!haveLogout){ //RMI stop will not destroy the registry, recreate registry will have error
				registry = LocateRegistry.createRegistry(IRMI_Defs.REGISTRY_PORT);
			}
			registry.rebind(IHost.BOUND_NAME, host);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * when closing the main window, remove IUser from every chatroom
	 */
	public void stop() {
		try {
			registry.unbind(IHost.BOUND_NAME);
			System.out.println("MainController: " + IHost.BOUND_NAME
					+ " has been unbound.");
			
			rmiUtils.stopRMI();
		} catch (Exception e) {
			System.err.println("MainController: Error unbinding "
					+ IHost.BOUND_NAME + ":\n" + e);
		}
	}
	/**
	 * Create a new Chat room
	 * @param name
	 */
	public void createChatRoom(String name) {
		ChatRoom chatRoom=new ChatRoom(name);
		IUser myUser;
		MiniController newChatRM;
		try {
			IUser temp=new User(this.host.getUUID(),username);
			myUser = (IUser) UnicastRemoteObject.exportObject(temp, IUser.CONNECTION_PORT);
			
			chatRoom.addLocalUser(myUser);
			newChatRM = new MiniController(view,chatRoom, myUser);
			view.addchatroom(newChatRM.getView());
			CRlist.add(newChatRM);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Log in
	 * @param text
	 * @param hasLogout
	 */
	public void login(String text, Boolean hasLogout) {
		this.username = text;
		this.start(hasLogout);
	}
/**
 * Log out
 */
	public void logout() {
		for(int i = 0; i < CRlist.size(); i++){
			MiniController currentCR = CRlist.get(i);
			currentCR.getModel().quit(currentCR.getView());
		}
		CRlist = new ArrayList<MiniController>();
		this.stop();
	}
	/**
	 * Get the local IP
	 * @return
	 */
	public String getLocalIp() {
		String localAddr = "";
		try {
			localAddr = rmiUtils.getLocalAddress() + ":" + IRMI_Defs.CLASS_SERVER_PORT_SERVER;
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}
		return localAddr;
	}
}
