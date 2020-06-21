package controller;

import model.User;
import view.ClientView;
import view.HomeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientController {
    private ClientView Cview;

    public ClientController(ClientView clientView){
        this.Cview = clientView;
    }

    //Globals
    public static User user = new User();
    private static ClientThread clientThread;
    static ObjectOutputStream output;

    public static void Connect(){
        user = HomeView.user;

        try{
            final int port = 2222;
            user.setSocket(new Socket(InetAddress.getLocalHost(),port)) ;

            clientThread = new ClientThread(user.getSocket());

            //sending UserName
            output = new ObjectOutputStream(user.getSocket().getOutputStream());
            try{
                output.writeObject(HomeView.HomeWindow.getTitle());
                output.flush();
            }catch(IOException ioException){
                JOptionPane.showMessageDialog(null, "Error - UserName not Sent!");
            }

            ClientView.top.setText("Online");

            Thread X = new Thread(clientThread);
            X.start();

        }
        catch(Exception x){
            System.out.println(x);
            JOptionPane.showMessageDialog(null, "Server Not Responding");
            System.exit(0);
        }
    }

    public static void ClientWindow_Action(){

        ClientView.send.addActionListener(
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

        ClientView.deleteConv.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        DELETECONV_ACTION();
                    }

                }
        );
        ClientView.userOnlineList.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e) {
                        USERONLINELIST_ACTION(e);
                    }
                }
        );

        ClientView.themeChooser.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent ae) {
                        int index = ClientView.themeChooser.getSelectedIndex();
                        final UIManager.LookAndFeelInfo[] themes = UIManager.getInstalledLookAndFeels();
                        try {
                            UIManager.setLookAndFeel( themes[index].getClassName() );
                            SwingUtilities.updateComponentTreeUI(ClientView.clientWindow);

                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );


    }

    private static void DELETECONV_ACTION() {
        ClientView.displayText.setText("");
        ClientView.displayText.revalidate();
        deleteConversation.clearTheFile(HomeView.HomeWindow.getTitle()+".txt");
    }

    private static void USERONLINELIST_ACTION(MouseEvent e){
        if (e.getClickCount() == 2) {
            final String selectedUser = ClientView.userOnlineList.getSelectedValue();
            SwingUtilities.invokeLater(
                    new Runnable(){
                        public void run() {
                            ClientView.typeText.setText("@" + selectedUser + ": ");
                            ClientView.typeText.requestFocus();
                        }
                    }
            );
        }
    }

    private static void SEND_ACTION() throws IOException{
        if(!ClientView.typeText.getText().equals("")){
            clientThread.SEND(ClientView.typeText.getText());
            ClientView.typeText.requestFocus();
            ClientView.typeText.setText("");
        }
    }
}
