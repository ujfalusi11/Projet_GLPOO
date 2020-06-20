package home;

import client.Client;
import onlineUsers.OnlineUsers;
import user.User;
import login.Login;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.UnknownHostException;

public class Home {

    public static User user = new User();

    public static JFrame HomeWindow = new JFrame();
    private static JPanel gui = new JPanel();

    private static JPanel topBar = new JPanel();
    private static JLabel top = new JLabel();

    private static JPanel userList = new JPanel();

    private static JScrollPane listScroll = new JScrollPane();

    private static JPanel textCenter = new JPanel();
    public static JTextArea typeText = new JTextArea();
    private static JButton onlineUsers = new JButton();
    private static JButton chatRoom = new JButton();
    private static JButton logOut = new JButton();

    private static void BuildHomeWindow(){
        ConfigureHomeWindow();
        HomeWindow_Action();
        HomeWindow.setVisible(true);
    }


    private static void ConfigureHomeWindow(){

        //setting MainWindow
        HomeWindow.setContentPane(gui);
        HomeWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        HomeWindow.setMinimumSize(new Dimension(500,300));
        HomeWindow.pack();
        HomeWindow.setLocationRelativeTo(null);
        try {
            HomeWindow.setLocationByPlatform(true);
            HomeWindow.setMinimumSize(HomeWindow.getSize());
        }
        catch(Throwable ignoreAndContinue) {
        }

        //setting top bar
        top.setText("Online");

        final UIManager.LookAndFeelInfo[] themes = UIManager.getInstalledLookAndFeels();
        String[] themeNames = new String[themes.length];
        int ii;
        for (ii=0; ii<themes.length; ii++) {
            themeNames[ii] = themes[ii].getName();
        }

        topBar.setLayout(new BorderLayout(5,5));
        topBar.setBorder(new TitledBorder(""));
        topBar.add(top, BorderLayout.WEST);

        // Setting online users list button
        onlineUsers.setText("Online users");
        onlineUsers.setPreferredSize(new Dimension(250,75));

        // Setting online list button
        chatRoom.setText("Chatroom");
        chatRoom.setPreferredSize(new Dimension(250,75));

        // Setting logging out button
        logOut.setText("Sign out");
        logOut.setPreferredSize(new Dimension(100,75));

        //setting the east
        userList.setLayout(new BorderLayout(5,5));
        userList.add(listScroll,BorderLayout.CENTER);

        //Setting center
        textCenter.setLayout(new BorderLayout(5,5));

        textCenter.add(onlineUsers, BorderLayout.EAST);
        //textCenter.add(chats,BorderLayout.CENTER);
        textCenter.add(chatRoom,BorderLayout.WEST);
        textCenter.add(logOut,BorderLayout.SOUTH);

        //setting everything in gui
        gui.setLayout(new BorderLayout(5,5));
        gui.add(topBar, BorderLayout.NORTH);
        gui.add(textCenter,BorderLayout.CENTER);

    }

    public static void HomeWindow_Action(){

        HomeWindow.addWindowListener(
                new WindowAdapter(){
                    public void windowClosing(WindowEvent e){
                        int result = JOptionPane.showConfirmDialog(null, "Are you sure","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);

                        if(result == JOptionPane.YES_OPTION){
                            //try {
                                //Client.output.close();
                                HomeWindow.dispose();
                           /* } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }*/


                            System.exit(0);
                        } else{
                            //Do nothing
                        }
                    }
                }
        );

        onlineUsers.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e) {
                        OnlineUsers.BuildOnlineUsersWindow();
                        OnlineUsers.Connect();

                    }
                }
        );

        chatRoom.addMouseListener(
                    new MouseAdapter(){
                        public void mouseClicked(MouseEvent e) {
                            Client.BuildGroupWindow();
                        }
                    }
            );

        logOut.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e) {
                        int result = JOptionPane.showConfirmDialog(null, "Are you sure","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);

                        if(result == JOptionPane.YES_OPTION){
                                HomeWindow.dispose();
                            System.exit(0);
                        }
                    }
                }
        );
    }

    private static void Initialize(){
        chatRoom.setEnabled(true);
        HomeWindow.setEnabled(true);
    }


    public static void main(String[] args) throws UnknownHostException, IOException{
        BuildHomeWindow();
        Initialize();
        Login.BuildLogInWindow();
    }
}
