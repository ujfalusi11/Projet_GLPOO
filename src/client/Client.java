package client;

//import conversation.deleteConversation;
//import conversation.getConversation;
import home.Home;
import user.User;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {


	//Globals
	public static User user = new User();
	private static ClientThread clientThread;
	static ObjectOutputStream output;



	//GUI Globals - Client Window
	static JFrame clientWindow = new JFrame();
	private static JPanel gui = new JPanel();

	private static JPanel topBar = new JPanel();
	private static JLabel top = new JLabel();
	private static JPanel PLAFContainer = new JPanel();
	private static JComboBox<?> themeChooser;

	private static JPanel userList = new JPanel();

	static JList<String> userOnlineList = new JList<>();
	private static JScrollPane listScroll = new JScrollPane();
	private static JButton send = new JButton();
	private static JButton deleteConv = new JButton();

	private static JPanel textCenter = new JPanel();
	public static JTextPane displayText = new JTextPane();

	private static JPanel buttonLabelText = new JPanel();
	private static JPanel buttonText = new JPanel();
	private static JTextArea typeText = new JTextArea();
	private static JLabel message = new JLabel("Message:");


	private static void Connect(){
		user = Home.user;

		try{
			final int port = 2222;
			user.setSocket(new Socket(InetAddress.getLocalHost(),port)) ;

			clientThread = new ClientThread(user.getSocket());

			//sending UserName
			output = new ObjectOutputStream(user.getSocket().getOutputStream());
			try{
				output.writeObject(Home.HomeWindow.getTitle());
				output.flush();
			}catch(IOException ioException){
				JOptionPane.showMessageDialog(null, "Error - UserName not Sent!");
			}

			top.setText("Online");

			Thread X = new Thread(clientThread);
			X.start();

		}
		catch(Exception x){
			System.out.println(x);
			JOptionPane.showMessageDialog(null, "Server Not Responding");
			System.exit(0);
		}
	}


	public static void BuildGroupWindow(){
		clientWindow.setTitle(Home.HomeWindow.getTitle());
		ConfigureClientWindow();
		Connect();
		ClientWindow_Action();
		//getConversation.display(Home.HomeWindow.getTitle());
		clientWindow.setVisible(true);
	}


	private static void ConfigureClientWindow(){

		//setting ClientWindow
		clientWindow.setContentPane(gui);
		clientWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		clientWindow.setMinimumSize(new Dimension(500,300));
		clientWindow.pack();
		clientWindow.setLocationRelativeTo(null);
		try {
			clientWindow.setLocationByPlatform(true);
			clientWindow.setMinimumSize(clientWindow.getSize());
		}
		catch(Throwable ignoreAndContinue) {
		}

		//setting top bar
		top.setText("Offline");

		final UIManager.LookAndFeelInfo[] themes = UIManager.getInstalledLookAndFeels();
		String[] themeNames = new String[themes.length];
		int ii;
		for (ii=0; ii<themes.length; ii++) {
			themeNames[ii] = themes[ii].getName();
		}

		PLAFContainer.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));

		themeChooser = new JComboBox<>(themeNames);
		themeChooser.setSelectedIndex(ii-1);

		PLAFContainer.add(themeChooser);

		topBar.setLayout(new BorderLayout(5,5));
		topBar.setBorder(new TitledBorder(""));
		topBar.add(top, BorderLayout.WEST);
		topBar.add(PLAFContainer, BorderLayout.EAST);

		//setting the users' list
		listScroll.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		listScroll.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		listScroll.setViewportView(userOnlineList);
		listScroll.setPreferredSize(new Dimension(130,200));
		listScroll.setMinimumSize(new Dimension(130,200));

		//setting the submit Button
		send.setText("SEND");
		send.setPreferredSize(new Dimension(100,50));
		send.setMinimumSize(new Dimension(100,30));

		deleteConv.setText("Erase Chat");
		deleteConv.setPreferredSize(new Dimension(100,50));
		deleteConv.setMinimumSize(new Dimension(100,30));

		//setting the east
		userList.setLayout(new BorderLayout(5,5));
		userList.add(listScroll,BorderLayout.CENTER);
		userList.add(deleteConv,BorderLayout.NORTH);
		userList.add(send,BorderLayout.SOUTH);


		//setting the chat display area
		displayText.setText("");
		displayText.setBorder(new LineBorder(Color.GRAY));
		displayText.setEditable(false);

		//setting the textarea to type chat
		typeText.setPreferredSize(new Dimension(400,60));
		typeText.setEditable(true);
		typeText.setBorder(new LineBorder(Color.GRAY));

		buttonText.setLayout(new BorderLayout(5,5));
		buttonText.add(new JScrollPane(typeText),BorderLayout.CENTER);

		buttonLabelText.setLayout(new BorderLayout(5,5));
		buttonLabelText.add(buttonText,BorderLayout.CENTER);
		buttonLabelText.add(message,BorderLayout.WEST);

		textCenter.setLayout(new BorderLayout(5,5));
		textCenter.add(new JScrollPane(displayText), BorderLayout.CENTER);
		textCenter.add(buttonLabelText,BorderLayout.SOUTH);

		//setting everything in gui
		gui.setLayout(new BorderLayout(5,5));
		gui.add(topBar, BorderLayout.NORTH);
		gui.add(userList, BorderLayout.EAST);
		gui.add(textCenter,BorderLayout.CENTER);

	}

	private static void ClientWindow_Action(){

		send.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						try {
							SEND_ACTION();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				}
		);

		deleteConv.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						DELETECONV_ACTION();
					}

				}
		);
		userOnlineList.addMouseListener(
				new MouseAdapter(){
					public void mouseClicked(MouseEvent e) {
						USERONLINELIST_ACTION(e);
					}
				}
		);

		themeChooser.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent ae) {
						int index = themeChooser.getSelectedIndex();
						final UIManager.LookAndFeelInfo[] themes = UIManager.getInstalledLookAndFeels();
						try {
							UIManager.setLookAndFeel( themes[index].getClassName() );
							SwingUtilities.updateComponentTreeUI(clientWindow);

						} catch(Exception e) {
							e.printStackTrace();
						}
					}
				}
		);


	}

	private static void DELETECONV_ACTION() {
		displayText.setText("");
		displayText.revalidate();
		//deleteConversation.clearTheFile(Home.HomeWindow.getTitle()+".txt");
	}

	private static void USERONLINELIST_ACTION(MouseEvent e){
		if (e.getClickCount() == 2) {
			final String selectedUser = userOnlineList.getSelectedValue();
			SwingUtilities.invokeLater(
					new Runnable(){
						public void run() {
							typeText.setText("@" + selectedUser + ": ");
							typeText.requestFocus();
						}
					}
			);
		}
	}

	private static void SEND_ACTION() throws IOException{
		if(!typeText.getText().equals("")){
			clientThread.SEND(typeText.getText());
			typeText.requestFocus();
			typeText.setText("");
		}
	}

}


	
	

