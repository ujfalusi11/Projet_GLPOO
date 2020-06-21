package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {

	private Server server;
	private Socket socket;
	private ObjectInputStream input;
	private String username;
	private Object message;

	ServerThread(Server server, Socket socket) throws IOException, ClassNotFoundException {
		// TODO Auto-generated constructor stub
		this.server = server;
		this.socket = socket;
		ObjectOutputStream output = new ObjectOutputStream(this.socket.getOutputStream());
		output.flush();
		input = new ObjectInputStream(this.socket.getInputStream());

		username = (String) input.readObject();

		server.clients.put(username, output);
		server.outputStreams.put(socket, output);

		server.sendToAll("!" + server.clients.keySet());

		server.showMessage("\n" + username + "(" + socket.getInetAddress().getHostAddress() + ") is online");
		//starting the thread
		start();
	}

	@SuppressWarnings("deprecation")
	public void run(){

		try {
			//Thread will run until connections are present
			while(true) {
				try{
					message = input.readObject();
				}catch (Exception e){
					stop();
				}

				if (message.toString().contains("@EE@"))
					server.sendToAll(message);
				else {
					String formattedMsg = "@" + username + message.toString().substring(message.toString().indexOf(':'), message.toString().length());
					server.sendPrivately(message.toString().substring(1, message.toString().indexOf(':')), formattedMsg);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				server.removeClient(username);
				server.removeConnection(socket, username);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
