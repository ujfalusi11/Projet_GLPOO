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

    public static void REGISTER_ACTION() {
        user.setUserName(regUsernameBox.getText());
        user.setPassword(regPasswordBox.getText());

        try{
            Connection con = DriverManager.getConnection("jdbc:h2:" + "./Database/user", "system", "admin");
            Statement stmt = con.createStatement();
            if(alreadyExists(user.getUserName())){
                JOptionPane.showMessageDialog(null, user.getUserName() + " is already taken. Choose a different one");
            }else{
                String sql = " insert INTO REGISTRATION(username,password) values ('"+user.getUserName()+"' ,'"+user.getUserName()+"')";
                stmt.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, " Welcome " + user.getUserName());
                con.close();

                regWindow.dispose();
                Home.HomeWindow.setTitle("ChatRoom - "+user.getUserName());

                //submit.setEnabled(true);
                Home.HomeWindow.setEnabled(true);
                Home.typeText.requestFocus();
            }
            con.close();
        }catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
   /* private registration() {
        // Username Label
        user_label = new JLabel();
        user_label.setText("User Name :");
        userName_text = new JTextField();
        // Password Label
        password_label = new JLabel();
        password_label.setText("Password :");
        password_text = new JPasswordField();
        // Submit
        submit = new JButton("SUBMIT");
        panel = new JPanel(new GridLayout(3, 1));
        panel.add(user_label);
        panel.add(userName_text);
        panel.add(password_label);
        panel.add(password_text);
        message = new JLabel();
        panel.add(message);
        panel.add(submit);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Adding the listeners to components..
        submit.addActionListener(this);
        add(panel, BorderLayout.CENTER);
        setTitle("Please Login Here !");
        setSize(450,350);
        setVisible(true);
    }*/
   // public static void main(String[] args) {
       // new registration();
   // }


    private static boolean alreadyExists(String userName){
        try {
            Connection con = DriverManager.getConnection("jdbc:h2:" + "./Database/user", "system", "admin");
            Statement stmt = con.createStatement();
            String sql = " select * FROM REGISTRATION";
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
