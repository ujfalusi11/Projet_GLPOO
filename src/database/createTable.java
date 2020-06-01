package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class createTable {
    public static void main(String[] args) {
        try{
            Connection con = DriverManager.getConnection("jdbc:h2:" + "./Database/user", "system", "admin");
            Statement stmt = con.createStatement();
            String sql = " Create table if not exists registration "
                    + " (id INTEGER auto_increment, "
                    + " username VARCHAR(15), "
                    + " password VARCHAR(8), "
                    + " PRIMARY KEY ( id )) ";
            stmt.executeUpdate(sql);
            System.out.println("Table Created");
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
}
