package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.ISearchingAlgorithm;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy{
    @Override
    public void serverStrategy(InputStream inputStream, OutputStream outputStream) {

        try {
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);
            //reads from client
            Maze maze = (Maze)fromClient.readObject();

            String tempDirectoryPath = System.getProperty("java.io.tmpdir");

            boolean exists = false;

            //checks in temp directory if the maze has already solved

            Solution sol;
            if(exists)
            {
                //sol = solution from temp dir
            }
            //the maze does'nt exists, solves the new maze
            else
            {
                SearchableMaze searchableMaze = new SearchableMaze(maze);
                ISearchingAlgorithm searcher = new BreadthFirstSearch();
                sol = searcher.solve(searchableMaze);
            }

            //need to return sol to client
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
