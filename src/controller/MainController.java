package controller;

import javax.swing.JComponent;

import view.IView2ModelAdapter;
import view.MainView;
import model.IModel2ViewAdapter;
import model.MainModel;
/**
 * The mainController, connect the MainModel and the MainView
 * @author Minsheng
 *
 */
public class MainController {
	/**
	 * The MainModel
	 */
	private MainModel model;
	/**
	 * The MainView
	 */
	private MainView view;
	/**
	 * The Constructor
	 */
	public MainController(){
		/**
		 * Initialize the MainModel instance
		 */
		model=new MainModel(new IModel2ViewAdapter(){

			@Override
			public void append(String s) {
				view.append(s);
			}

			@Override
			public void addchatroom(JComponent miniview) {
				view.mainViewAddTab(miniview);
			}

			@Override
			public void leavechatroom(JComponent miniview) {
				view.mainViewRemoveTab(miniview);
				
			}

			@Override
			public boolean showsInvite(String invitor) {
				return view.showInvite(invitor);
				
			}

			@Override
			public void addchatroom2(String nameOfChat, JComponent miniview) {
				view.mainViewAddTabWithName(nameOfChat, miniview);
			}
		});
		/**
		 * Initialize the MainView instance
		 */
		view=new MainView(new IView2ModelAdapter(){

			@Override
			public void createChatRoom(String name) {
				model.createChatRoom(name);
			}

			@Override
			public void quit() {
				model.stop();
			}

			@Override
			public void login(String text,Boolean hasLogout) {
				model.login(text,hasLogout);
			}

			@Override
			public void logout() {
				model.logout();
			}

			@Override
			public String getLocalIp() {
				return model.getLocalIp();
			}
			
		});
	}
	/**
	 * Start the controller
	 */
	public void start(){
		view.start();
		
	}
	/**
	 * new Main controller
	 * @param args
	 */
	public static void main(String[] args) {
		(new MainController()).start();
	}
}
