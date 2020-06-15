package registration;

import home.Home;
import login.Login;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class registration  {
    JPanel panel;
    JLabel user_label, password_label, message;
    JTextField userName_text;
    JPasswordField password_text;
    JButton submit, cancel;

    public static User user = new User();

    public static JFrame regWindow = new JFrame();
    public static JPanel regWindowGui = new JPanel();
    public static JLabel regEnterUsername = new JLabel("Enter Username");
    public static JLabel regEnterPassword = new JLabel("Enter Password");
    public static JTextField regUsernameBox = new JTextField(20);
    public static JTextField regPasswordBox = new JPasswordField(20);
    public static JButton register = new JButton("Register");


    public static void BuildRegWindow(){

        regWindow.setTitle("Registration");

        ConfigureRegWindow();
        RegWindow_Action();
        regWindow.setVisible(true);
    }

    public static void ConfigureRegWindow(){
        regWindow.setContentPane(regWindowGui);
        regWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        regWindow.setMinimumSize(new Dimension(370,150));
        regWindow.pack();
        regWindow.setLocationRelativeTo(null);
        try {
            // 1.6+
            regWindow.setLocationByPlatform(true);
            regWindow.setMinimumSize(regWindow.getSize());
        }
        catch(Throwable ignoreAndContinue) {
        }


        regWindowGui.setLayout(new FlowLayout());
        regWindowGui.add(regEnterUsername);
        regWindowGui.add(regUsernameBox);
        regWindowGui.add(regEnterPassword);
        regWindowGui.add(regPasswordBox);
        regWindowGui.add(register);
    }

    public static  void RegWindow_Action(){
        register.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        REGISTER_ACTION();
                    }

                }
        );
    }

}
