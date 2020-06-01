package user;

import java.net.Socket;

public class User {

    private String userName;
    private String password;
    private String ipAddress;
    private Socket socket = null;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() { return userName; }

    public String getPassword() {
        return password;
    }

    public String getIpAddress() { return ipAddress; }

    public Socket getSocket() { return this.socket; }
}
