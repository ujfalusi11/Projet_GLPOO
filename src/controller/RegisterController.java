package controller;

import model.User;
import view.HomeView;
import view.RegisterView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegisterController {

    public static User user = new User();

    public static  void RegWindow_Action(){
        RegisterView.register.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        REGISTER_ACTION();
                    }

                }
        );
    }

    private static void REGISTER_ACTION() {
        user.setUserName(RegisterView.regUsernameBox.getText());
        user.setPassword(RegisterView.regPasswordBox.getText());

        try{
            Connection con = DriverManager.getConnection("jdbc:h2:" + "./Database/model.user", "system", "admin");
            Statement stmt = con.createStatement();
            if(alreadyExists(user.getUserName())){
                JOptionPane.showMessageDialog(null, user.getUserName() + " is already taken. Choose a different one");
            }else{
                String sql = " insert INTO userInfo(username,password) values ('"+user.getUserName()+"' ,'"+user.getUserName()+"')";
                stmt.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, " Welcome " + user.getUserName());
                con.close();
                RegisterView.regWindow.dispose();

                //Create new users' conversation file
                try {

                    File file = new File(user.getUserName()+".txt");
                    //file.delete();
                    if(file.createNewFile())System.out.println("Success!");
                    else System.out.println ("Error, file already exists.");
                }
                catch(IOException ioe) {
                    ioe.printStackTrace();
                }

                HomeView.HomeWindow.setTitle(user.getUserName());
                HomeView.HomeWindow.setEnabled(true);
            }
            con.close();
        }catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    private static boolean alreadyExists(String userName){
        try {
            Connection con = DriverManager.getConnection("jdbc:h2:" + "./Database/model.user", "system", "admin");
            Statement stmt = con.createStatement();
            String sql = " select * FROM userInfo";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next())
            {
                if (userName.trim().equals(rs.getString(2))) {
                    return true;
                }
            }
            rs.close(); con.close();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        return false;
    }
}
