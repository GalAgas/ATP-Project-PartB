package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.ISearchingAlgorithm;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;


import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    private static int clientCounter = 0; //check if need to synch when initialize
    private static ConcurrentHashMap<byte[], String> solAndMazeDict;

    //key-toByteArray/compresses/=>string, where to save? type of file txt?
    public void ServerStrategySolveSearchProblem() {
        String tempDirPath = System.getProperty("java.io.tmpdir");//check where to save?
            //first instance
            if (solAndMazeDict == null) {
                //saves the new dictionary in temp dir
                solAndMazeDict = new ConcurrentHashMap<byte[], String>();
                writeOutToFile("/dictFile.ext", solAndMazeDict);
//                FileOutputStream dictFile = new FileOutputStream(tempDirPath + "/dictFile.ext");
//                ObjectOutputStream outDict = new ObjectOutputStream(dictFile);
//                outDict.writeObject(solAndMazeDict);
//                dictFile.close();
            }
            else
                solAndMazeDict =  (ConcurrentHashMap)readInFromFile("/dictFile.ext");
//                FileInputStream dictFile = new FileInputStream(tempDirPath + "/dictFile.ext");
//                ObjectInputStream inDict = new ObjectInputStream(dictFile);
//                solAndMazeDict = (ConcurrentHashMap) inDict.readObject();
    }

    @Override
    public void serverStrategy(InputStream inputStream, OutputStream outputStream) {

        try {
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);
            //reads from client
            Maze mazeFromClient = (Maze)fromClient.readObject();
            byte[] newMazeKey = mazeFromClient.toByteArray();
            Solution sol;
            String solFileName;
            String tempDirPath = System.getProperty("java.io.tmpdir");//check where to save?

            //maze already solved
            if (solAndMazeDict.containsKey(newMazeKey)) {
                solFileName = solAndMazeDict.get(newMazeKey);
                sol = (Solution)readInFromFile(solFileName);
//                FileInputStream solFile = new FileInputStream(tempDirPath + solFileName);
//                ObjectInputStream inSol = new ObjectInputStream(solFile);
//                sol = (Solution) inSol.readObject();
            }
            //the maze is'nt solved
            else {
                //saves new maze in temp dir
                writeOutToFile("newMaze.ext", mazeFromClient); //check name?

                //solves the new maze
                SearchableMaze searchableMaze = new SearchableMaze(mazeFromClient);
                ISearchingAlgorithm searcher = new BreadthFirstSearch();//config.properties file
                sol = searcher.solve(searchableMaze);

                //saves the new solution in temp dir
                synchronized (this)//check? lock only here??
                {
                    solFileName = "/sol" + clientCounter + ".ext";
                    clientCounter++;
                }
                writeOutToFile(solFileName, sol);
//                FileOutputStream newSolFile = new FileOutputStream(tempDirPath + "/sol" + clientCounter + ".ext");
//                ObjectOutputStream outSol = new ObjectOutputStream(newSolFile);
//                outSol.writeObject(sol);
//                newSolFile.close();
                //updates and saves dictionary ??creates new file? does it matters?
                solAndMazeDict.put(newMazeKey, solFileName);
                writeOutToFile(solFileName, solAndMazeDict);
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
        String tempDirPath = System.getProperty("java.io.tmpdir");//check where to save?
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
        String tempDirPath = System.getProperty("java.io.tmpdir");//check where to save?
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