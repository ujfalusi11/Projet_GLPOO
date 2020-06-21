package controller;

import view.ClientView;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;


public class getConversation {
    public static void display(String userName) {
        try {
            FileReader reader = new FileReader(""+userName+".txt");
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if(line.startsWith("from") || line.startsWith("@")){
                    StyledDocument doc = ClientView.displayText.getStyledDocument();

                    SimpleAttributeSet left = new SimpleAttributeSet();
                    StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
                    StyleConstants.setForeground(left, Color.GREEN);

                    SimpleAttributeSet right = new SimpleAttributeSet();
                    StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
                    StyleConstants.setForeground(right, Color.BLUE);
                    try
                    {
                        doc.insertString(doc.getLength(), "\n"+ line, right );
                        doc.setParagraphAttributes(doc.getLength(), 1, right, false);
                    }
                    catch(Exception e) {}
                }
                else {
                    StyledDocument doc = ClientView.displayText.getStyledDocument();

                    SimpleAttributeSet left = new SimpleAttributeSet();
                    StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
                    StyleConstants.setForeground(left, Color.BLACK);

                    try
                    {
                        doc.insertString(doc.getLength(), "\n"+line, left );
                        doc.setParagraphAttributes(doc.getLength(), 1, left, false);
                    }
                    catch(Exception e) {}
                }


            }
            reader.close();
}
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
}
