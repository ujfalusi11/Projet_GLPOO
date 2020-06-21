package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class createFile {
    public static void main(String[] args) throws Exception {
        Class.forName("org.h2.Driver");
        Connection con = DriverManager.getConnection("jdbc:h2:" + "./Database/model.user", "system", "admin");
        System.out.println("Database created");
    }
}
