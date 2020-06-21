package controller;

import model.User;
import view.HomeView;
import view.LoginView;
import view.RegisterView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {

    private static int connected;

    public static void LogInWindow_Action(){
        LoginView.logInEnter.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        LOGIN_ACTION();
                    }

                }
        );

        LoginView.register.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        LoginView.logInWindow.dispose();
                        RegisterView.BuildRegWindow();
                    }

                }
        );
    }


    private static void LOGIN_ACTION() {
        if(!LoginView.logInUsernameBox.getText().equals("") && !LoginView.logInPasswordBox.getText().equals("")) {
            User user = new User();
            user.setUserName(LoginView.logInUsernameBox.getText().trim());
            user.setPassword(LoginView.logInPasswordBox.getText().trim());

            try {
                Connection con = DriverManager.getConnection("jdbc:h2:" + "./Database/model.user", "system", "admin");
                Statement stmt = con.createStatement();
                String sql = " select * FROM REGISTRATION";
                ResultSet rs = stmt.executeQuery(sql);

                while(rs.next())
                {
                    if (user.getUserName().equals(rs.getString(2)) && user.getPassword().equals(rs.getString(3))) {
                        HomeView.HomeWindow.setTitle(user.getUserName());
                        LoginView.logInWindow.dispose();
                        HomeView.HomeWindow.setEnabled(true);
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
}
