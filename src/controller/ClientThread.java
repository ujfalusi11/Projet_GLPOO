package controller;

import view.ClientView;
import view.HomeView;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Arrays;


public class ClientThread implements Runnable{

	//Globals
	private Socket SOCK;
	private ObjectInputStream in;
	private String[] currentUsers;

	//Constructor getting the socket
	ClientThread(Socket X){
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


	private void RECEIVE() throws IOException, ClassNotFoundException{
		if(in != null){
			String message = (String) in.readObject();


			String strr;
			if(message.startsWith("!")) {
				String temp1 = message.substring(1);
				temp1 = temp1.replace("[", "");
				temp1 = temp1.replace("]", "");

				currentUsers = temp1.split(", ");
				Arrays.sort(currentUsers);

				try {

					SwingUtilities.invokeLater(
							new Runnable(){
								public void run() {
									ClientView.userOnlineList.setListData(currentUsers);
								}
							}
					);
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Unable to set Online list data");
				}
			}


			else if(message.startsWith("@EE@|")) {
				final String temp2 = message.substring(5);

				SwingUtilities.invokeLater(
						new Runnable(){
							public void run() {

								StyledDocument doc = ClientView.displayText.getStyledDocument();

								SimpleAttributeSet left = new SimpleAttributeSet();
								StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
								StyleConstants.setForeground(left, Color.BLACK);

								try
								{
									doc.insertString(doc.getLength(), "\n"+temp2, left );
									doc.setParagraphAttributes(doc.getLength(), 1, left, false);

								}
								catch(Exception e) {}
							}
						}
				);
				strr =temp2;
				saveConversation.add(ClientView.clientWindow.getTitle(), strr);
			}

			else if(message.startsWith("@")){
				final String temp3 = message.substring(1);

				SwingUtilities.invokeLater(
						new Runnable(){
							public void run() {

								StyledDocument doc = ClientView.displayText.getStyledDocument();

								SimpleAttributeSet left = new SimpleAttributeSet();
								StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
								StyleConstants.setForeground(left, Color.GREEN);

								SimpleAttributeSet right = new SimpleAttributeSet();
								StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
								StyleConstants.setForeground(right, Color.BLUE);
								try
								{
									doc.insertString(doc.getLength(), "\n"+"from @"+ temp3, right );
									doc.setParagraphAttributes(doc.getLength(), 1, right, false);
								}
								catch(Exception e) {}
							}
						}
				);
				strr = "from @"+ temp3;
				saveConversation.add(HomeView.HomeWindow.getTitle(), strr);
			}
		}
	}

	void SEND(final String str) throws IOException{
		String writeStr;
		if(str.startsWith("@")){
			SwingUtilities.invokeLater(
					new Runnable(){
						@Override
						public void run() {
							// TODO Auto-generated method stub

							StyledDocument doc = ClientView.displayText.getStyledDocument();

							SimpleAttributeSet left = new SimpleAttributeSet();
							StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
							StyleConstants.setForeground(left, Color.RED);

							SimpleAttributeSet right = new SimpleAttributeSet();
							StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
							StyleConstants.setForeground(right, Color.BLUE);

							try
							{
								doc.insertString(doc.getLength(), "\n" + HomeView.HomeWindow.getTitle() + ": " + str, right );
								doc.setParagraphAttributes(doc.getLength(), 1, right, false);
							}
							catch(Exception e) {}
						}
					}
			);
			writeStr = str;
			saveConversation.add(HomeView.HomeWindow.getTitle(), str);

		}
		else {
			writeStr = "@EE@|" + HomeView.HomeWindow.getTitle() + ": " + str;
		}
		ClientController.output.writeObject(writeStr);
		ClientController.output.flush();

	}
}
















