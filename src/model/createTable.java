package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class createTable {
    public static void main(String[] args) {
        try{
            Connection con = DriverManager.getConnection("jdbc:h2:" + "./Database/model.user", "system", "admin");
            Statement stmt = con.createStatement();
            String sql;
            stmt.executeUpdate("Truncate table userInfo");
            sql = " Create table if not exists userInfo "
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
