package controller;

import javax.swing.JComponent;

import view.MiniView;
import view.MiniView2ModelAdapter;
import comp310f13.rmiChat.IUser;
import model.ChatRoom;
import model.IModel2ViewAdapter;
import model.MiniModel;
import model.MiniModel2ViewAdapter;
/**
 * The miniController
 * @author Minsheng
 *
 */
public class MiniController {
	
	private MiniModel model;
	private MiniView view;
	private MiniController currentController = this;
	
	public MiniController(IModel2ViewAdapter mainAdapter, ChatRoom chatRoom, IUser user){
		
		/**
		 * Init the miniModel instance
		 */
		model=new MiniModel(new MiniModel2ViewAdapter(){

			@Override
			public void append(String message) {
				view.append(message);
			}

			
		},mainAdapter,chatRoom,user
		);
		/**
		 * Initialize the miniView instance
		 */
		view=new MiniView(new MiniView2ModelAdapter(){

			@Override
			public void quit() {
				model.quit(currentController.getView());
			}

			@Override
			public void sendInvitation(String remoteHost) {
				model.connectTo(remoteHost);
			}

			@Override
			public void sendMessage(String text) {
				model.sendMessage(text);
				
			}

			@Override
			public void sendImage(String absolutePath) {
				model.sendImage(absolutePath);
				
			}
			
		});
	}
	
	/**
	 * Get the current pane
	 * @return
	 */
	public JComponent getView() {
		return view.getContentPane();
	}
/**
 * get the current model
 * @return
 */
	public MiniModel getModel(){
		return model;
	}
	/**
	 * Start the contorller
	 */
	public void start(){
		model.start();
		view.start();
	}
}
