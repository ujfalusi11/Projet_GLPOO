package view;

import controller.RegisterController;
import model.User;

import javax.swing.*;
import java.awt.*;

public class RegisterView {
    private static User user = new User();

    public static JFrame regWindow = new JFrame();
    private static JPanel regWindowGui = new JPanel();
    private static JLabel regEnterUsername = new JLabel("Enter Username");
    private static JLabel regEnterPassword = new JLabel("Enter Password");
    public static JTextField regUsernameBox = new JTextField(20);
    public static JTextField regPasswordBox = new JPasswordField(20);
    public static JButton register = new JButton("Sign up");

    public static void BuildRegWindow(){
        regWindow.setTitle("Sign up");
        ConfigureRegWindow();
        RegisterController.RegWindow_Action();
        regWindow.setVisible(true);
    }

    private static void ConfigureRegWindow(){
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
}
