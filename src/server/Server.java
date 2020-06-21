package server;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;

class Server {

	private JTextArea displayWindow;
	private ServerSocket serverSocket;
	final Hashtable<Socket, ObjectOutputStream> outputStreams;
	final Hashtable<String, ObjectOutputStream> clients;

	//constructor
	Server(int port) throws IOException{
		//Simple Gui for Server
		JFrame serverGui = new JFrame("Server");
		serverGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		serverGui.setSize(500, 500);
		displayWindow = new JTextArea();
		serverGui.add(new JScrollPane(displayWindow), BorderLayout.CENTER);
		serverGui.setVisible(true);


		outputStreams = new Hashtable<Socket, ObjectOutputStream>();
		clients = new Hashtable<String, ObjectOutputStream>();

		serverSocket = new ServerSocket(port);
		showMessage("Waiting for clients at " + serverSocket);
	}

	//Waiting for clients to connect
	void waitingForClients() throws IOException, ClassNotFoundException{

		while (true){
			Socket socket = serverSocket.accept();

			new ServerThread(this, socket);
		}
	}

	//displaying message on Server Gui
	void showMessage(final String message) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(
				new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						displayWindow.append(message);
					}

				}
		);
	}

	//Sending a message to all the available clients
	void sendToAll(Object data) throws IOException{

		for (Enumeration<ObjectOutputStream> e = getOutputStreams(); e.hasMoreElements(); ){
			//since we don't want server to remove one client and at the same time sending message to it
			synchronized (outputStreams){
				ObjectOutputStream tempOutput = e.nextElement();
				tempOutput.writeObject(data);
				tempOutput.flush();
			}
		}
	}

	//To get Output Stream of the available clients from the hash table
	private Enumeration<ObjectOutputStream> getOutputStreams() {
		// TODO Auto-generated method stub
		return outputStreams.elements();
	}

	//Sending private message
	void sendPrivately(String username, String message) throws IOException {
		// TODO Auto-generated method stub
		ObjectOutputStream tempOutput = clients.get(username);
		tempOutput.writeObject(message);
		tempOutput.flush();
	}

	//Removing the client from the client hash table
	void removeClient(String username) throws IOException{

		synchronized (clients){
			clients.remove(username);
			sendToAll("!" + clients.keySet());
		}
	}

	//Removing a connection from the outputStreams hash table and closing the socket
	void removeConnection(Socket socket, String username) throws IOException{

		synchronized (outputStreams){
			outputStreams.remove(socket);
		}

		//Printing out the client along with the IP offline in the format of ReetAwwsum(123, 12, 21, 21) is offline
		showMessage("\n" + username + "(" + socket.getInetAddress().getHostAddress() + ") is offline");

	}
}












