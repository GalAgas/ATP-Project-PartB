package Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private InetAddress ip;
    private int port; //The port
    private IClientStrategy clientStrategy;

    public Client(InetAddress ip, int port, IClientStrategy clientStrategy) {
        this.ip = ip;
        this.port = port;
        this.clientStrategy = clientStrategy;
    }

    public void communicateWithServer() {
        try {
            Socket socket = new Socket(ip, port);
            //System.out.println("Client is connected to server!");
            clientStrategy.clientStrategy(socket.getInputStream(),socket.getOutputStream());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
