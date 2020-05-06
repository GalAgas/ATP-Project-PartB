package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import com.sun.xml.internal.ws.util.ByteArrayBuffer;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy {
    @Override
    public void serverStrategy(InputStream inputStream, OutputStream outputStream) {

        try {
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);
//            toClient.flush(); //?????????
            //reads from client
            int[] dimensions = (int[]) fromClient.readObject();
            //generates the maze
            AMazeGenerator mazeGenerator = new MyMazeGenerator(); //????????? not sure because of the config.properties file
            Maze maze = mazeGenerator.generate(dimensions[0], dimensions[1]);
            //compresses the maze
            OutputStream out = new MyCompressorOutputStream(toClient);
            out.write(maze.toByteArray());
            out.flush();
//            out.close(); //????????? not sure that it's necessary
//            fromClient.close(); //?????????
//            toClient.close(); //?????????
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}