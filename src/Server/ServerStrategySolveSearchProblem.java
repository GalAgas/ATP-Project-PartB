package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.ISearchingAlgorithm;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;


import java.io.*;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    private static AtomicInteger clientCounter;
    private static ConcurrentHashMap<String, String> solAndMazeDict;
    private String tempDirPath;

    //key-toByteArray=>string
    public ServerStrategySolveSearchProblem() {
        tempDirPath = System.getProperty("java.io.tmpdir");
        String dictFileName = "dictFile.txt";

        //dict already exists
        if(new File(tempDirPath+dictFileName).exists())
        {
            System.out.println(tempDirPath);
            solAndMazeDict = (ConcurrentHashMap)readInFromFile(dictFileName);
            clientCounter = new AtomicInteger(solAndMazeDict.size());
        }
        //first instance
        else
        {
            solAndMazeDict = new ConcurrentHashMap<>();
            writeOutToFile(dictFileName, solAndMazeDict);
            clientCounter = new AtomicInteger(0);
        }
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
                sol = (Solution)readInFromFile(solFileName);
            }
            else {
                //saves new maze in temp dir
                String mazeFileName = "maze" + clientCounter.incrementAndGet() + ".txt";
                writeOutToFile(mazeFileName, mazeFromClient);

                //solves the new maze
                SearchableMaze searchableMaze = new SearchableMaze(mazeFromClient);
                ISearchingAlgorithm searcher = new BreadthFirstSearch();//config.properties file
                sol = searcher.solve(searchableMaze);

                //saves the new solution in temp dir
                solFileName = "sol" + clientCounter.get() + ".txt";
                writeOutToFile(solFileName, sol);

                //updates and saves dictionary
                solAndMazeDict.put(newMazeKey, solFileName);
                writeOutToFile("dictFile.txt", solAndMazeDict);
            }

            //returns sol to client
            toClient.writeObject(sol);
            toClient.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //try&catch or exception?
    private void writeOutToFile(String fileName, Object out) {
        try {
            FileOutputStream file = new FileOutputStream(tempDirPath + fileName);
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(out);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Object readInFromFile(String fileName) {
        Object inObject = null;
        try {
            FileInputStream file = new FileInputStream(tempDirPath + fileName);
            ObjectInputStream input = new ObjectInputStream(file);
            inObject = input.readObject();
            file.close();
        } catch (IOException|ClassNotFoundException e) {
            e.printStackTrace();
        }
        return inObject;
    }

}