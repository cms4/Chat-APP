package cmd;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.MiniModel;
import comp310f13.rmiChat.IUser;
import provided.datapacket.ICmd2ModelAdapter;
/**
 * The adapter connect DataPacketAlgoCmd and the model of a ChatApp system
 * @author Minsheng
 *
 */
public class Cmd2ModelAdapter implements ICmd2ModelAdapter, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9200301950007159427L;
	private MiniModel model;
/**
 * Constructor
 * @param model
 */
	public Cmd2ModelAdapter(MiniModel model){
		this.model=model;
	}
	/**
	 * Get Local user stub
	 */
	@Override
	public IUser getLocalUserStub() {
		
		return model.getUser();
	}
	/**
	 * Append the status to the view
	 */
	@Override
	public void append(String s) {
		model.appends(s+"\n");
	}

	@Override
	public void addComponent(String name, Component newComp) {
		JFrame displayer = new JFrame();
		displayer.setBounds(100, 100, 450, 300);
		displayer.add(newComp);
		displayer.setVisible(true);
	}
	public MiniModel getMiniModel(){
		return model;
	}
}
