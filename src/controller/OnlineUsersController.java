package controller;

import model.User;
import view.HomeView;
import view.OnlineUsersView;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class OnlineUsersController {

    public static User user = new User();

    public static void Connect(){
       user = HomeView.user;

        try{
            final int port = 2222;
            user.setSocket(new Socket(InetAddress.getLocalHost(),port)) ;

            OnlineUsersThread OUThread = new OnlineUsersThread(user.getSocket());

            //sending UserName
            ObjectOutputStream output = new ObjectOutputStream(user.getSocket().getOutputStream());
            try{
                output.writeObject(HomeView.HomeWindow.getTitle());
                output.flush();
            }catch(IOException ioException){
                JOptionPane.showMessageDialog(null, "Error - UserName not Sent!");
            }

            OnlineUsersView.top.setText("Online");

            Thread X = new Thread(OUThread);
            X.start();
        }
        catch(Exception x){
            System.out.println(x);
            JOptionPane.showMessageDialog(null, "Server Not Responding");
            System.exit(0);
        }
    }
}
