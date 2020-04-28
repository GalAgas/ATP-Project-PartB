package test;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;
import java.util.Arrays;


public class RunCompressDecompressMaze {
    public static void main(String[] args) {
        String mazeFileName = "savedMaze.maze";
        AMazeGenerator mazeGenerator = new MyMazeGenerator();
        Maze maze = mazeGenerator.generate(4, 7); //Generate new maze
        maze.print();
        System.out.println("*****************************************");
        byte[] bm = maze.toByteArray();
//        for (int i=0; i<bm.length; i++)
//        {
//            System.out.println(bm[i]);
//        }
        System.out.println(bm.length);
        System.out.println(Arrays.toString(bm));
        System.out.println("");
        System.out.println("****************************************");
        try {
            // save maze to a file
            OutputStream out = new MyCompressorOutputStream(new FileOutputStream(mazeFileName));
            out.write(bm);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        //test- fromByteArrayToMaze
//        System.out.println("########");
//        Maze m2 = new Maze(bm);
//        m2.print();
//        System.out.println("********");
//        System.out.println(m2.getRows());
//        System.out.println("********");
//        System.out.println(m2.getCols());
//        System.out.println("********");
//        System.out.println(m2.getStartPosition());
//        System.out.println("********");
//        System.out.println(m2.getGoalPosition());
//        byte savedMazeBytes[] = new byte[0];
//        try {
//            //read maze from file
//            InputStream in = new MyDecompressorInputStream(new FileInputStream(mazeFileName));
//            savedMazeBytes = new byte[maze.toByteArray().length];
//            in.read(savedMazeBytes);
//            in.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Maze loadedMaze = new Maze(savedMazeBytes);
//        boolean areMazesEquals = Arrays.equals(loadedMaze.toByteArray(),maze.toByteArray());
//        System.out.println(String.format("Mazes equal: %s",areMazesEquals)); //maze should be equal to loadedMaze
    }
}








