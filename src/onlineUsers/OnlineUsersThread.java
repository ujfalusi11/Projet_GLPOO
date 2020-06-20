package onlineUsers;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Arrays;

public class OnlineUsersThread implements Runnable{

    //Globals
    private Socket SOCK;
    private ObjectInputStream in;
    private String[] currentUsers;

    //Constructor getting the socket
    OnlineUsersThread(Socket X){
        this.SOCK = X;
    }

    @Override
    public void run() {
        try{
            in = new ObjectInputStream(SOCK.getInputStream());
            CheckStream();

        }catch(Exception E){
            JOptionPane.showMessageDialog(null, E);
        }
    }

    private void CheckStream() throws IOException, ClassNotFoundException{
        while(true){
            RECEIVE();
        }
    }


    private void RECEIVE() throws IOException, ClassNotFoundException {
        if (in != null) {
            String message = (String) in.readObject();


            if (message.startsWith("!")) {
                String temp1 = message.substring(1);
                temp1 = temp1.replace("[", "");
                temp1 = temp1.replace("]", "");

                currentUsers = temp1.split(", ");
                Arrays.sort(currentUsers);

                try {

                    SwingUtilities.invokeLater(
                            new Runnable() {
                                public void run() {
                                    OnlineUsers.userOnlineList.setListData(currentUsers);
                                }
                            }
                    );
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Unable to set Online list data");
                }
            }
        }
    }
}
