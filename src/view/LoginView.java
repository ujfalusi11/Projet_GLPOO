package login;

import controller.LoginController;

import javax.swing.*;
import java.awt.*;

public class LoginView {

    public static JFrame logInWindow = new JFrame();
    private static JPanel logInWindowGui = new JPanel();
    private static JLabel logInEnterUsername = new JLabel("Enter Username");
    private static JLabel logInEnterPassword = new JLabel("Enter Password");
    public static JTextField logInUsernameBox = new JTextField(20);
    public static JTextField logInPasswordBox = new JPasswordField(20);

    public static JButton logInEnter = new JButton("Sign in");
    public static JButton register = new JButton("Sign up");


    static void BuildLogInWindow(){
        logInWindow.setTitle("Sign in");
        ConfigureLogInWindow();
        LoginController.LogInWindow_Action();
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
}
