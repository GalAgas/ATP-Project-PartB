package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;
import java.io.*;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    private volatile static AtomicInteger clientCounter;
    private static ConcurrentHashMap<String, String> solAndMazeDict;
    private String tempDirPath;
    private ISearchingAlgorithm searcher;

    //key-toByteArray=>string
    public ServerStrategySolveSearchProblem() {
        tempDirPath = System.getProperty("java.io.tmpdir");
        solAndMazeDict = new ConcurrentHashMap<>();
        clientCounter = new AtomicInteger(0);
//        if (Configurations.getProperty("problemSolver").equals("BreadthFirstSearch"))
//            searcher = new BreadthFirstSearch();
//        else if (Configurations.getProperty("problemSolver").equals("BestFirstSearch"))
//            searcher = new BestFirstSearch();
//        else if (Configurations.getProperty("problemSolver").equals("DepthFirstSearch"))
//            searcher = new DepthFirstSearch();
//        else
//            searcher = new BreadthFirstSearch();
        searcher = new BreadthFirstSearch();
    }

    @Override
    public void serverStrategy(InputStream inputStream, OutputStream outputStream) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);
            //reads from client
            Maze mazeFromClient = (Maze)fromClient.readObject();
            byte[] byteMaze = mazeFromClient.toByteArray();
            String newMazeKey = Arrays.toString(byteMaze);
            Solution sol;
            String solFileName;

            //maze already solved
            if (solAndMazeDict.containsKey(newMazeKey)) {
                solFileName = solAndMazeDict.get(newMazeKey);
                sol = readSolFromFile(solFileName);
            }
            else {
                //solves the new maze
                SearchableMaze searchableMaze = new SearchableMaze(mazeFromClient);
                sol = searcher.solve(searchableMaze);
                sol.setNeighboursPathToNull();
                String mazeFileName = "maze" + clientCounter.incrementAndGet();
                solFileName = "sol" + clientCounter.get();
                //updates dictionary, saves the new maze and sol
                writeAll(solFileName,sol,mazeFileName,mazeFromClient,newMazeKey);
            }

            //returns sol to client
            toClient.writeObject(sol);
            toClient.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private synchronized void writeAll (String solName, Solution sol, String mazeName, Maze outM, String newMazeKey)
    {
        writeSolToFile(solName, sol);
        writeMazeToFile(mazeName, outM);
        solAndMazeDict.put(newMazeKey, solName);
    }

    private void writeSolToFile(String fileName, Solution out) {
        try {
            FileOutputStream file = new FileOutputStream(tempDirPath + fileName);
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(out);
            output.flush();
            output.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeMazeToFile(String fileName, Maze out) {
        try {
            FileOutputStream file = new FileOutputStream(tempDirPath + fileName);
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(out);
            output.flush();
            output.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Solution readSolFromFile(String fileName) {
        Solution inSol = null;
        try {
            FileInputStream file = new FileInputStream(tempDirPath + fileName);
            ObjectInputStream input = new ObjectInputStream(file);
            inSol = (Solution) input.readObject();
            file.close();
            return inSol;
        } catch (IOException|ClassNotFoundException e) {
            e.printStackTrace();
        }
        return inSol;
    }


}


