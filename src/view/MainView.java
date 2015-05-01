package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Font;
/**
 * The main view
 * @author Minsheng
 *
 */
public class MainView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1434216695759527061L;
	private JPanel contentPane;
	private JTextArea textArea;
	private IView2ModelAdapter model;
	private JTextField chatRmName;
	private JTabbedPane mainTab;
	private JTextField usernameField;
	private JButton btnCreateNewChatroom;
	private JButton btnLogIn,btnLogOut;
	private JLabel lblNewLabel;
	private MainView currentView = this;
	private Boolean hasLogout = false;
	/**
	 * Create the frame.
	 */
	public MainView(IView2ModelAdapter model) {
		this.model=model;
		init();
		
	}
	/**
	 * Initialize the frame
	 */
	public void init(){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				quit();
			}
		});
		
		
		setBounds(100, 100, 750, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 3, 0, 0));
		
		usernameField = new JTextField();
		usernameField.setToolTipText("Enter your disired username");
		panel_2.add(usernameField);
		usernameField.setColumns(10);
		
		btnLogIn = new JButton("Log in");
		btnLogIn.setToolTipText("log in to system");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.login(usernameField.getText(), hasLogout);
				btnCreateNewChatroom.setEnabled(true);
				btnLogIn.setEnabled(false);
				usernameField.setEditable(false);
				textArea.append("\nLogin as:" + usernameField.getText());
				currentView.status(false);
				btnLogOut.setEnabled(true);
			}
		});
		panel_2.add(btnLogIn);
		
		btnLogOut = new JButton("Log out");
		btnLogOut.setToolTipText("log out the system, but can relogin");
		btnLogOut.setEnabled(false);
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.logout();
				btnCreateNewChatroom.setEnabled(false);
				btnLogIn.setEnabled(true);
				currentView.status(true);
				usernameField.setEditable(true);
				hasLogout = true;
				textArea.append("\n" + usernameField.getText() + " has Loged out!");
			}
		});
		panel_2.add(btnLogOut);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.setToolTipText("quit the system");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		panel_1.add(btnQuit);
		
		lblNewLabel = new JLabel("Status: Disconnected");
		lblNewLabel.setToolTipText("Status bar to show the connection information");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		panel_1.add(lblNewLabel);
		
		mainTab = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(mainTab, BorderLayout.CENTER);
		mainTab.setPreferredSize(new Dimension(700, 30));
		
		textArea = new JTextArea();
		textArea.setToolTipText("Information display here");
		textArea.setEditable(false);
		textArea.append("Welcome!\n");
		
		mainTab.addTab("MainScreen", null, textArea, null);
		
		JPanel southPanel = new JPanel();
		contentPane.add(southPanel, BorderLayout.SOUTH);
		
		JLabel chatRMnamelb = new JLabel("New Chatroom Name:");
		southPanel.add(chatRMnamelb);
		
		chatRmName = new JTextField();
		chatRmName.setToolTipText("Enter your desired chat room name");
		southPanel.add(chatRmName);
		chatRmName.setColumns(20);
		
		btnCreateNewChatroom = new JButton("Create New ChatRoom");
		btnCreateNewChatroom.setToolTipText("To create a new chat room with the name entered");
		btnCreateNewChatroom.setEnabled(false);
		southPanel.add(btnCreateNewChatroom);
		btnCreateNewChatroom.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				model.createChatRoom(chatRmName.getText());
				mainTab.setSelectedIndex(mainTab.getTabCount()-1);
				chatRmName.setText("");
			}
		});
	}
	/**
	 * Start the frame
	 */
	public void start(){
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	/**
	 * Set the status of the disconnection
	 * @param isDisconnected
	 */
	public void status(Boolean isDisconnected){
		if(isDisconnected){
			lblNewLabel.setText("Status: Disconnected");
			lblNewLabel.setForeground(Color.RED);
		}else{
			lblNewLabel.setText("Status: Conencted, Your IP is: " + model.getLocalIp());
			lblNewLabel.setForeground(Color.BLUE);
		}
	}
	/**
	 * Add a tab
	 * @param miniview
	 */
	public void mainViewAddTab(JComponent miniview){
		mainTab.addTab(chatRmName.getText(),miniview);
	}
	/**
	 * Add the status of the connection
	 * @param s
	 */
	public void append(String s){
		textArea.append(s);
	}
	/**
	 * Quit the Main View
	 */
	public void quit(){
		model.quit();
	}
	/**
	 * remove a tab
	 * @param miniview
	 */
	public void mainViewRemoveTab(JComponent miniview) {
		mainTab.remove(miniview);
	}
	/**
	 * Add a Tab with a given name
	 * @param nameOfChat
	 * @param miniview
	 */
	public void mainViewAddTabWithName(String nameOfChat, JComponent miniview){
		mainTab.addTab(nameOfChat,miniview);
		mainTab.setSelectedIndex(mainTab.getTabCount()-1);
	}
	/**
	 * Show the invite message
	 * @param invitor
	 * @return
	 */
	public boolean showInvite(String invitor) {
		this.mainTab.setSelectedIndex(0);
		textArea.append("\nInvite Info: " + invitor);
		Object[] options = {"Accept","Reject"};
		int result=JOptionPane.showOptionDialog(null, invitor,"Inviting",JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,options, options[0]);
		if(result == 0){
			return true; 
		}
		return false;
		
	}
}
