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
        Maze maze = mazeGenerator.generate(500, 500); //Generate new maze
//        maze.print();
        System.out.println("*****************************************");
        byte[] bm = maze.toByteArray();
//        for (int i=0; i<bm.length; i++)
//        {
//            System.out.println(bm[i]);
//        }
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



//        //1
//        ByteBuffer byteBuffer = ByteBuffer.wrap(sliceBatch);
//        int dec1 = byteBuffer.getInt();
//        System.out.println(dec1);
//        System.out.println("****************************************");

//        //2
//        String sBinary  = Arrays.toString(sliceBatch);
//        String digits = sBinary.replaceAll("[^0-9.]", "");
//        System.out.println(digits);
//        int dec2 = Integer.parseInt(digits,2);
//        System.out.println(dec2);
//        System.out.println("****************************************");
//
//        ByteBuffer bytes41 = ByteBuffer.allocate(4);
//        System.out.println(bytes41.putInt(dec1).array());





