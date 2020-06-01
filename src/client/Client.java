package client;

//import home.Home;
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
	//public static String userName = "Anonymous";
	//public static Socket SOCK;
	public static ObjectOutputStream output;
	
	
	
	//GUI Globals - Main Window
	public static JFrame mainWindow = new JFrame();
		public static JPanel gui = new JPanel();
	
			public static JPanel topBar = new JPanel();
				public static JLabel top = new JLabel();
				public static JPanel PLAFContainer = new JPanel();
					public static String[] themeNames;
					public static JComboBox<?> themeChooser;
			
			public static JPanel userList = new JPanel();
				@SuppressWarnings("rawtypes")
				public static JList userOnlineList = new JList();
					public static JScrollPane listScroll = new JScrollPane();
				public static JButton submit = new JButton();
				
			public static JPanel textCenter = new JPanel();
					public static JTextArea displayText = new JTextArea();
				public static JPanel buttonLabelText = new JPanel();
					public static JPanel buttonText = new JPanel();
						public static JTextArea typeText = new JTextArea();
					public static JLabel message = new JLabel("Message:");

	
			
					
	//GUI Globals - Login Window
	public static JFrame logInWindow = new JFrame();
		public static JPanel logInWindowGui = new JPanel();
			public static JLabel logInEnterUsername = new JLabel("Enter Username: ");
	public static JLabel logInEnterPassword = new JLabel("Enter Password: ");
	public static JTextField logInUsernameBox = new JTextField(20);
	public static JTextField logInPasswordBox = new JPasswordField(20);

	public static JButton logInEnter = new JButton("Enter");
	private static int connected;


	public static void Connect(){
		//user = Home.user;

		try{
			final int port = 2222;
			user.setSocket(new Socket(InetAddress.getLocalHost(),port)) ;
			
			clientThread = new ClientThread(user.getSocket());
			
			//sending UserName
			output = new ObjectOutputStream(user.getSocket().getOutputStream());
			try{
				output.writeObject(user.getUserName());
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
	
	
	


}


	
	

