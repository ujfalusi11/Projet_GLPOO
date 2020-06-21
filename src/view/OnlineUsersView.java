package view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class OnlineUsersView {

    private static JFrame onlineUsersWindow = new JFrame();

    //GUI Globals - Main Window
    private static JPanel gui = new JPanel();
    private static JPanel topBar = new JPanel();
    public static JLabel top = new JLabel();
    private static JPanel PLAFContainer = new JPanel();
    private static JPanel userList = new JPanel();
    public static JList<String> userOnlineList = new JList<String>();
    private static JScrollPane listScroll = new JScrollPane();


    public static void BuildOnlineUsersWindow(){
        onlineUsersWindow.setTitle(HomeView.HomeWindow.getTitle());
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

        JComboBox<?> themeChooser = new JComboBox<>(themeNames);
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
