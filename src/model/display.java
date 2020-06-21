package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class display {
    public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection("jdbc:h2:" + "./Database/model.user", "system", "admin");
            Statement stmt = con.createStatement();
            String sql = " select * FROM REGISTRATION";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next())
            {
                System.out.println(rs.getString(2));
            }
            rs.close(); con.close();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
}