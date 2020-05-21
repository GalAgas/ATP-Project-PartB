package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements IClientStrategy{
    private InetAddress ip;
    private int port; //The port
    private IClientStrategy clientStrategy;

    public Client(InetAddress ip, int port, IClientStrategy clientStrategy) {
        this.ip = ip;
        this.port = port;
        this.clientStrategy = clientStrategy;
    }

    @Override
    public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {

    }

    public void communicateWithServer() {
        try {
            Socket socket = new Socket(ip, port);
            clientStrategy.clientStrategy(socket.getInputStream(),socket.getOutputStream());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
