package server;

import java.io.IOException;

public class StartingPointServer {

	public static void main(String[] args) throws IOException, ClassNotFoundException{
		Server server = new Server(2222);
		server.waitingForClients();
	}
}
