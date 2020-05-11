package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    private int port; //The port number
    private int listeningInterval; //The elapsed time until socket timeout
    private IServerStrategy serverStrategy; //The strategy for handling clients
    private volatile boolean stop;

    public Server(int port, int listeningInterval, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
        this.stop = false;
    }

    public void start(){
        new Thread(this::runOurServer).start();
    }

    private void runOurServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningInterval);
            ExecutorService executor = Executors.newFixedThreadPool(5); //????????? not sure because of the config.properties file
            while (!stop)
            {
                try {
                    Socket clientSocket = serverSocket.accept();

                    executor.execute(() -> clientHandle(clientSocket));

                }
                catch (IOException  e) {
                    //System.out.println("Where are the clients??");
                }
            }
            executor.shutdown();
            serverSocket.close();
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
            this.serverStrategy.serverStrategy(inFromClient, outToClient);

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
