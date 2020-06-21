package view;

import controller.ClientController;
import controller.getConversation;
//import home.Home;
import model.User;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ClientView {

    //Globals
    public static User user = new User();

    //GUI Globals - Client Window
    public static JFrame clientWindow = new JFrame();
    private static JPanel gui = new JPanel();
    private static JPanel topBar = new JPanel();
    public static JLabel top = new JLabel();
    private static JPanel PLAFContainer = new JPanel();
    public static JComboBox<?> themeChooser;
    private static JPanel userList = new JPanel();
    public static JList<String> userOnlineList = new JList<>();
    private static JScrollPane listScroll = new JScrollPane();
    public static JButton send = new JButton();
    public static JButton deleteConv = new JButton();
    private static JPanel textCenter = new JPanel();
    public static JTextPane displayText = new JTextPane();
    private static JPanel buttonLabelText = new JPanel();
    private static JPanel buttonText = new JPanel();
    public static JTextArea typeText = new JTextArea();
    private static JLabel message = new JLabel("Message:");

    public static void BuildClientWindow(){
        ClientView.clientWindow.setTitle(HomeView.HomeWindow.getTitle());
        ClientView.ConfigureClientWindow();
        ClientController.Connect();
        ClientController.ClientWindow_Action();
        getConversation.display(HomeView.HomeWindow.getTitle());
        ClientView.clientWindow.setVisible(true);
    }

    public static void ConfigureClientWindow(){

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

}
