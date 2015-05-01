package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
/**
 * The miniView
 * @author Minsheng
 *
 */
public class MiniView extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4709064235648908218L;
	private JPanel contentPane;
	private JTextField textFieldIP;
    private MiniView2ModelAdapter model;
    private JButton btnQuitChatrm;
    private JSplitPane splitPane;
    private JScrollPane upperScrollPane;
    private JScrollPane downScrollPane;
    private JPanel southPanel;
    private JButton btnSendMessage;
    private JButton btnSendFile;
    private JTextArea upperTextArea;
    private JTextField textField;
    private JPanel panel;
    private JFrame currentFrame=this;
	/**
	 * Create the frame.
	 */
	public MiniView(MiniView2ModelAdapter model) {
		this.model=model;
		init();
	}
	public void init(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				quit();
			}
		});
		setBounds(100, 100, 550, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel northPanel = new JPanel();
		contentPane.add(northPanel, BorderLayout.NORTH);
		
		btnQuitChatrm = new JButton("Quit ChatRm");
		btnQuitChatrm.setToolTipText("leave the chat room");
		btnQuitChatrm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.quit();
			}
		});
		northPanel.add(btnQuitChatrm);
		
		JButton btnInvite = new JButton("Invite");
		btnInvite.setToolTipText("invite someone with the IP that inputted to the chat room");
		northPanel.add(btnInvite);
		btnInvite.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
//				EventQueue.invokeLater(new Runnable(){
//					@Override
//					public void run() {
						model.sendInvitation(textFieldIP.getText());
					}});
				
			//}
	//	});
		
		textFieldIP = new JTextField();
		textFieldIP.setToolTipText("Enter the IP address of the people that invited! Be sure that you two clients are in same network field");
		northPanel.add(textFieldIP);
		textFieldIP.setColumns(10);
		
		splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane, BorderLayout.CENTER);
		splitPane.setDividerLocation(430);
		
		upperScrollPane = new JScrollPane();
		splitPane.setLeftComponent(upperScrollPane);
		
		upperTextArea = new JTextArea();
		upperTextArea.setToolTipText("Display the information in chatroom, include the chatting words");
		upperTextArea.setEditable(false);
		upperScrollPane.setViewportView(upperTextArea);
		
		downScrollPane = new JScrollPane();
		splitPane.setRightComponent(downScrollPane);
		
		textField = new JTextField();
		textField.setToolTipText("Enter the message to be sent");
		downScrollPane.setViewportView(textField);
		textField.setColumns(10);
		
		panel = new JPanel();
		downScrollPane.setRowHeaderView(panel);
		
		btnSendMessage = new JButton("Send Message");
		btnSendMessage.setToolTipText("Click to send the message that inputted");
		panel.add(btnSendMessage);
		btnSendMessage.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				((MiniView) currentFrame).append("me:"+textField.getText()+"\n");
				model.sendMessage(textField.getText());
				textField.setText("");
			}
		});
		
		btnSendFile = new JButton("Send File");
		btnSendFile.setToolTipText("click and choose a file from your system to send to everyone in the chat room");
		panel.add(btnSendFile);
		btnSendFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFileChooser fc = new JFileChooser();
				fc.addChoosableFileFilter(new FileFilter(){
					public String getDescription(){
						return "PNG";
					}

					@Override
					public boolean accept(File f) {
						if(f.isDirectory()){
							return true;
						}
						else{
							return f.getName().toLowerCase().endsWith(".png");
							
						}
					}
				});
				int returnVal = fc.showOpenDialog(currentFrame);
				if(returnVal==JFileChooser.APPROVE_OPTION){
					System.out.println("Choose file name="+fc.getSelectedFile().getName());
					model.sendImage(fc.getSelectedFile().getAbsolutePath());		
				}
			}
		});
		
		southPanel = new JPanel();
		contentPane.add(southPanel, BorderLayout.SOUTH);
	}
	public void start(){
		setVisible(true);
	}
	public void quit(){
		model.quit();
	}
	
	public JPanel getContentPane(){
		return this.contentPane;
	}
	public void append(String message) {
		upperTextArea.append(message);
	}
}
