package home;

import client.Client;
import client.StartingPointClient;
import onlineUsers.OnlineUsers;
import user.User;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Home {

    public static User user = new User();

    public static JFrame HomeWindow = new JFrame();
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
    public static JButton onlineUsers = new JButton();
    public static JButton chats = new JButton();
    public static JButton groupChat = new JButton();



    //GUI Globals - Login Window
    public static JFrame logInWindow = new JFrame();
    public static JPanel logInWindowGui = new JPanel();
    public static JLabel logInEnterUsername = new JLabel("Enter Username: ");
    public static JLabel logInEnterPassword = new JLabel("Enter Password: ");
    public static JTextField logInUsernameBox = new JTextField(20);
    public static JTextField logInPasswordBox = new JPasswordField(20);

    public static JButton logInEnter = new JButton("Enter");
    private static int connected;

    public static void BuildMainWindow(){

        //HomeWindow.setTitle("Project ChatRoom - "+ user.getUserName());

        ConfigureHomeWindow();
        HomeWindow_Action();
        HomeWindow.setVisible(true);
    }


    public static void ConfigureHomeWindow(){

        //prepare the layout
        /*gui.removeAll();
        mainWindow.setContentPane(gui);
        PLAFContainer.removeAll();*/

        //setting MainWindow
        HomeWindow.setContentPane(gui);
        HomeWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        HomeWindow.setMinimumSize(new Dimension(500,300));
        HomeWindow.pack();
        HomeWindow.setLocationRelativeTo(null);
        try {
            // 1.6+
            HomeWindow.setLocationByPlatform(true);
            HomeWindow.setMinimumSize(HomeWindow.getSize());
        }
        catch(Throwable ignoreAndContinue) {
        }


        //setting top bar
        top.setText("Online");

        final UIManager.LookAndFeelInfo[] themes = UIManager.getInstalledLookAndFeels();
        themeNames = new String[themes.length];
        int ii;
        for (ii=0; ii<themes.length; ii++) {
            themeNames[ii] = themes[ii].getName();
        }

        PLAFContainer.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));

        themeChooser = new JComboBox(themeNames);
        themeChooser.setSelectedIndex(ii-1);



        PLAFContainer.add(themeChooser);

        topBar.setLayout(new BorderLayout(5,5));
        topBar.setBorder(new TitledBorder(""));
        topBar.add(top, BorderLayout.WEST);
        topBar.add(PLAFContainer, BorderLayout.EAST);


        // Setting online users list button
        onlineUsers.setText("Online users");
        onlineUsers.setPreferredSize(new Dimension(100,75));

        // Setting online list button
        groupChat.setText("Group");
        groupChat.setPreferredSize(new Dimension(100,75));

        // Setting online list button
        chats.setText("Chats");
        chats.setPreferredSize(new Dimension(100,75));
        chats.setMaximumSize(new Dimension(100,30));


        //setting the display list
        listScroll.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        listScroll.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        listScroll.setViewportView(userOnlineList);
        listScroll.setPreferredSize(new Dimension(130,200));
        listScroll.setMinimumSize(new Dimension(130,200));

        //setting the east
        userList.setLayout(new BorderLayout(5,5));
        userList.add(listScroll,BorderLayout.CENTER);

        //Setting center
        textCenter.setLayout(new BorderLayout(5,5));

        textCenter.add(onlineUsers, BorderLayout.NORTH);
        textCenter.add(chats,BorderLayout.CENTER);
        textCenter.add(groupChat,BorderLayout.SOUTH);

        //setting everything in gui
        gui.setLayout(new BorderLayout(5,5));
        gui.add(topBar, BorderLayout.NORTH);
        gui.add(userList, BorderLayout.EAST);
        gui.add(textCenter,BorderLayout.CENTER);

    }

    public static void HomeWindow_Action(){

       /* HomeWindow.addWindowListener(
                new WindowAdapter(){
                    public void windowClosing(WindowEvent e){
                        int result = JOptionPane.showConfirmDialog(null, "Are you sure","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);

                        if(result == JOptionPane.YES_OPTION){
                            try {
                                output.close();
                                clientThread.in.close();
                                SOCK.close();
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }


                            System.exit(0);
                        } else{
                            //Do nothing
                        }
                    }
                }
        );*/


        onlineUsers.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e) {
                        OnlineUsers.BuildOnlineUsersWindow();
                        OnlineUsers.Connect();

                    }
                }
        );



        /*chats.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e) {
                        USERONLINELIST_ACTION(e);
                    }
                }
        );*/

        //if(groupeChat.isEnabled()){
            groupChat.addMouseListener(
                    new MouseAdapter(){
                        public void mouseClicked(MouseEvent e) {
                            Client.BuildGroupWindow();
                            Client.Connect();
                            // while(Client.mainWindow.isActive()){
                            textCenter.remove(groupChat);
                            gui.revalidate();
                            // }
                        }
                    }
            );
       // }else{//do nothing

           //  }


        themeChooser.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent ae) {
                        int index = themeChooser.getSelectedIndex();
                        final UIManager.LookAndFeelInfo[] themes = UIManager.getInstalledLookAndFeels();
                        try {
                            UIManager.setLookAndFeel( themes[index].getClassName() );
                            SwingUtilities.updateComponentTreeUI(HomeWindow);

                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );


    }

    private static void OPENGROUPCHAT_ACTION(MouseEvent e) {
      //  StartingPointClient.run();
    }

    public static void Initialize(){
        submit.setEnabled(false);
        HomeWindow.setEnabled(false);
    }
    
    public static void BuildLogInWindow(){

    logInWindow.setTitle("Log In");

    ConfigureLogInWindow();
    LogInWindow_Action();
    logInWindow.setVisible(true);
}







public static void ConfigureLogInWindow(){
    logInWindow.setContentPane(logInWindowGui);
    logInWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    logInWindow.setMinimumSize(new Dimension(370,150));
    logInWindow.pack();
    logInWindow.setLocationRelativeTo(null);
    try {
        // 1.6+
        logInWindow.setLocationByPlatform(true);
        logInWindow.setMinimumSize(logInWindow.getSize());
    }
    catch(Throwable ignoreAndContinue) {
    }


    logInWindowGui.setLayout(new FlowLayout());
    logInWindowGui.add(logInEnterUsername);
    logInWindowGui.add(logInUsernameBox);
    logInWindowGui.add(logInEnterPassword);
    logInWindowGui.add(logInPasswordBox);
    logInWindowGui.add(logInEnter);
}







public static void LogInWindow_Action(){
    logInEnter.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    LOGIN_ACTION();
                }

            }
    );

    logInUsernameBox.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    LOGIN_ACTION();
                }
            }
    );
}


public static void LOGIN_ACTION() {
    if(!logInUsernameBox.getText().equals("") && !logInPasswordBox.getText().equals("")) {
        user.setUserName(logInUsernameBox.getText().trim());
        user.setPassword(logInPasswordBox.getText().trim());

        try {
            Connection con = DriverManager.getConnection("jdbc:h2:" + "./Database/user", "system", "admin");
            Statement stmt = con.createStatement();
            String sql = " select * FROM REGISTRATION";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next())
            {
                if (user.getUserName().equals(rs.getString(2)) && user.getPassword().equals(rs.getString(3))) {
                    HomeWindow.setTitle("ChatRoom - "+user.getUserName());
                    logInWindow.dispose();
                    //submit.setEnabled(true);
                    HomeWindow.setEnabled(true);
                    typeText.requestFocus();
                    //Connect();
                    connected = 1;
                    break;
                }
            }
            if(connected!=1){
                JOptionPane.showMessageDialog(null, "Please Enter valid login information!");
            }

            rs.close(); con.close();


        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }


    }
    else {
        JOptionPane.showMessageDialog(null, "Please Enter your complete login information!");
    }
}

public static void main(String[] args) throws UnknownHostException, IOException{

    BuildMainWindow();
    Initialize();
    BuildLogInWindow();

}
}
   
