package controller;

import java.io.FileWriter;

public class saveConversation {
    public static void add(String userName,String str) {
            try {
                FileWriter fw = new FileWriter(""+userName+".txt",true);
                fw.write(str);
                fw.write("\n");
                fw.close();

            }
            catch (Exception e)
            {
                System.err.println(e.getMessage());
            }
        }
}
