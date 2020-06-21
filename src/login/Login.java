package login;

import home.Home;
import registration.registration;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Login {

    private static JFrame logInWindow = new JFrame();
    private static JPanel logInWindowGui = new JPanel();
    private static JLabel logInEnterUsername = new JLabel("Enter Username");
    private static JLabel logInEnterPassword = new JLabel("Enter Password");
    private static JTextField logInUsernameBox = new JTextField(20);
    private static JTextField logInPasswordBox = new JPasswordField(20);

    private static JButton logInEnter = new JButton("Sign in");
    private static JButton register = new JButton("Sign up");

    private static int connected;

    public static void BuildLogInWindow(){
        logInWindow.setTitle("Sign in");
        ConfigureLogInWindow();
        LogInWindow_Action();
        logInWindow.setVisible(true);
    }


    private static void ConfigureLogInWindow(){
        logInWindow.setContentPane(logInWindowGui);
        logInWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logInWindow.setMinimumSize(new Dimension(370,150));
        logInWindow.pack();
        logInWindow.setLocationRelativeTo(null);
        try {
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
        logInWindowGui.add(register);
    }



    private static void LogInWindow_Action(){
        logInEnter.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        LOGIN_ACTION();
                    }

                }
        );

        register.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        logInWindow.dispose();
                        registration.BuildRegWindow();
                    }

                }
        );
    }


    private static void LOGIN_ACTION() {
        if(!logInUsernameBox.getText().equals("") && !logInPasswordBox.getText().equals("")) {
            User user = new User();
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
                        Home.HomeWindow.setTitle(user.getUserName());
                        logInWindow.dispose();
                        Home.HomeWindow.setEnabled(true);
                        Home.typeText.requestFocus();
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
