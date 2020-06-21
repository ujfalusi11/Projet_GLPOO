package model;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class insert {
    public static void main(String[] args) throws IOException {
        try {
            Connection con = DriverManager.getConnection("jdbc:h2:" + "./Database/model.user", "system", "admin");
            Statement stmt = con.createStatement();
            String sql = " insert INTO userInfo(username,password) values ('alice','alice'), "
                    + " ('bob', 'bob'), ";
            int i = stmt.executeUpdate(sql);
            System.out.println(i + "records inserted");
            con.close();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        try {

            File file = new File("bob.txt");
            //file.delete();
            if(file.createNewFile())System.out.println("Success!");
            else System.out.println ("Error, file already exists.");
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }

        try {

            File file = new File("alice.txt");
            //file.delete();
            if(file.createNewFile())System.out.println("Success!");
            else System.out.println ("Error, file already exists.");
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
