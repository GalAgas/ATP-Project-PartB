package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;
//import com.sun.xml.internal.ws.util.ByteArrayBuffer;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy {
    @Override
    public void serverStrategy(InputStream inputStream, OutputStream outputStream) {

        try {
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);

            //reads from client
            int[] dimensions = (int[]) fromClient.readObject();
            //generates the maze
            AMazeGenerator mazeGenerator;
//            if (Configurations.getProperty("generateMaze").equals("MyMazeGenerator"))
//                mazeGenerator = new MyMazeGenerator();
//            else if (Configurations.getProperty("generateMaze").equals("EmptyMazeGenerator"))
//                mazeGenerator = new EmptyMazeGenerator();
//            else if (Configurations.getProperty("generateMaze").equals("SimpleMazeGenerator"))
//                mazeGenerator = new SimpleMazeGenerator();
//            else
//                mazeGenerator = new MyMazeGenerator();
            mazeGenerator = new MyMazeGenerator();
            Maze maze = mazeGenerator.generate(dimensions[0], dimensions[1]);
            //compresses the maze
            OutputStream out = new MyCompressorOutputStream(toClient);
            out.write(maze.toByteArray());
            out.flush();
            out.close(); //????????? not sure that it's necessary
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
