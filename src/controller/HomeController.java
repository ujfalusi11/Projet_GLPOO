package controller;

import model.User;
//import onlineUsers.OnlineUsers;
import view.ClientView;
import view.HomeView;
import view.OnlineUsersView;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HomeController {

    public static User user = new User();

    public static void HomeWindow_Action(){

        HomeView.HomeWindow.addWindowListener(
                new WindowAdapter(){
                    public void windowClosing(WindowEvent e){
                        int result = JOptionPane.showConfirmDialog(null, "Are you sure","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);

                        if(result == JOptionPane.YES_OPTION){
                            HomeView.HomeWindow.dispose();
                            System.exit(0);
                        }
                    }
                }
        );

        HomeView.onlineUsers.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e) {
                        OnlineUsersView.BuildOnlineUsersWindow();
                       // OnlineUsers.Connect();

                    }
                }
        );

        HomeView.chatRoom.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e) {
                        ClientView.BuildClientWindow();
                    }
                }
        );

        HomeView.logOut.addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e) {
                        int result = JOptionPane.showConfirmDialog(null, "Are you sure","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);

                        if(result == JOptionPane.YES_OPTION){
                            HomeView.HomeWindow.dispose();
                            System.exit(0);
                        }
                    }
                }
        );
    }

    public static void Initialize(){
        HomeView.chatRoom.setEnabled(true);
        HomeView.HomeWindow.setEnabled(true);
    }

}
