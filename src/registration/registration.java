package registration;

import home.Home;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class registration  {

    public static User user = new User();

    private static JFrame regWindow = new JFrame();
    private static JPanel regWindowGui = new JPanel();
    private static JLabel regEnterUsername = new JLabel("Enter Username");
    private static JLabel regEnterPassword = new JLabel("Enter Password");
    private static JTextField regUsernameBox = new JTextField(20);
    private static JTextField regPasswordBox = new JPasswordField(20);
    private static JButton register = new JButton("Sign up");

    public static void BuildRegWindow(){
        regWindow.setTitle("Sign up");
        ConfigureRegWindow();
        RegWindow_Action();
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

    private static  void RegWindow_Action(){
        register.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        REGISTER_ACTION();
                    }

                }
        );
    }

    private static void REGISTER_ACTION() {
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

                //Create new users' conversation file
                try {

                    File file = new File(user.getUserName()+".txt");
                    file.delete();
                    if(file.createNewFile())System.out.println("Success!");
                    else System.out.println ("Error, file already exists.");
                }
                catch(IOException ioe) {
                    ioe.printStackTrace();
                }

                Home.HomeWindow.setTitle(user.getUserName());
                Home.HomeWindow.setEnabled(true);
                Home.typeText.requestFocus();
            }
            con.close();
        }catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

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
