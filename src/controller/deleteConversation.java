package controller;

import java.io.FileWriter;
import java.io.PrintWriter;

public class deleteConversation {
    public static void clearTheFile(String fileName) {
        try {
            FileWriter fwOb = new FileWriter(fileName, false);
            PrintWriter pwOb = new PrintWriter(fwOb, false);
            pwOb.flush();
            pwOb.close();
            fwOb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
