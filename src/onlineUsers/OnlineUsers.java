package onlineUsers;

import home.Home;
import user.User;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class OnlineUsers {

    private static JFrame onlineUsersWindow = new JFrame();

    //GUI Globals - Main Window
    private static JPanel gui = new JPanel();

    private static JPanel topBar = new JPanel();
    private static JLabel top = new JLabel();
    private static JPanel PLAFContainer = new JPanel();

    private static JPanel userList = new JPanel();
    static JList userOnlineList = new JList();
    private static JScrollPane listScroll = new JScrollPane();
    private static User user = new User();


    public static void Connect(){
        //user = Home.user;

        try{
            final int port = 2222;
            user.setSocket(new Socket(InetAddress.getLocalHost(),port)) ;

            OnlineUsersThread OUThread = new OnlineUsersThread(user.getSocket());

            //sending UserName
            ObjectOutputStream output = new ObjectOutputStream(user.getSocket().getOutputStream());
            try{
                output.writeObject(Home.HomeWindow.getTitle());
                output.flush();
            }catch(IOException ioException){
                JOptionPane.showMessageDialog(null, "Error - UserName not Sent!");
            }

            top.setText("Online");

            Thread X = new Thread(OUThread);
            X.start();
        }
        catch(Exception x){
            System.out.println(x);
            JOptionPane.showMessageDialog(null, "Server Not Responding");
            System.exit(0);
        }
    }

    public static void BuildOnlineUsersWindow(){
        onlineUsersWindow.setTitle(Home.HomeWindow.getTitle());
        ConfigureOUWindow();
        onlineUsersWindow.setVisible(true);
    }

    private static void ConfigureOUWindow(){


        //setting MainWindow
        onlineUsersWindow.setContentPane(gui);
        onlineUsersWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        onlineUsersWindow.setMinimumSize(new Dimension(500,300));
        onlineUsersWindow.pack();
        onlineUsersWindow.setLocationRelativeTo(null);
        try {
            onlineUsersWindow.setLocationByPlatform(true);
            onlineUsersWindow.setMinimumSize(onlineUsersWindow.getSize());
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

        JComboBox<?> themeChooser = new JComboBox(themeNames);
        themeChooser.setSelectedIndex(ii-1);

        PLAFContainer.add(themeChooser);

        topBar.setLayout(new BorderLayout(5,5));
        topBar.setBorder(new TitledBorder(""));
        topBar.add(top, BorderLayout.WEST);
        topBar.add(PLAFContainer, BorderLayout.EAST);

        //setting the username list
        listScroll.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        listScroll.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        listScroll.setViewportView(userOnlineList);
        listScroll.setPreferredSize(new Dimension(130,200));
        listScroll.setMinimumSize(new Dimension(130,200));

        //setting the center
        userList.setLayout(new BorderLayout(5,5));
        userList.add(listScroll,BorderLayout.CENTER);

        //setting everything in gui
        gui.setLayout(new BorderLayout(5,5));
        gui.add(topBar, BorderLayout.NORTH);
        gui.add(userList, BorderLayout.CENTER);
    }
}
