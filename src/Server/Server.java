package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port; //The port
    private int ms;
    private IServerStrategy clientHandler; //The strategy for handling clients
    private volatile boolean stop;

    public Server(int port, int ms, IServerStrategy clientHandler) {
        this.port = port;
        this.ms = ms;
        this.clientHandler = clientHandler;
        this.stop = false;
    }

    public void start(){
        new Thread(() -> {
            runOurServer();
        }).start();
    }

    public void runOurServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(ms);

            while (!stop)
            {
                try {
                    Socket clientSocket = serverSocket.accept();

                    new Thread(() -> {
                        clientHandle(clientSocket);
                    }).start();

                }
                catch (IOException e) {
                    //System.out.println("Where are the clients??");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function receives client socket and handles it
     * @param clientSocket - The client socket
     */
    private void clientHandle(Socket clientSocket) {
        try {
            InputStream inFromClient = clientSocket.getInputStream();
            OutputStream outToClient = clientSocket.getOutputStream();
            this.clientHandler.serverStrategy(inFromClient, outToClient);

            inFromClient.close();
            outToClient.close();
            clientSocket.close();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void stop()
    {
        this.stop = true;
    }
}
