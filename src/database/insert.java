package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class insert {
    public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection("jdbc:h2:" + "./Database/user", "system", "admin");
            Statement stmt = con.createStatement();
            String sql = " insert INTO REGISTRATION(username,password) values ('alice','alice'), "
                    + " ('bob', 'bob'), "
                    + " ('tom', 'tom'),"
                    + " ('tim', 'tim')";
            int i = stmt.executeUpdate(sql);
            System.out.println(i + "records inserted");
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

    }
}
