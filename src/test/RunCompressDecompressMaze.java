package test;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;
import java.nio.ByteBuffer;


public class RunCompressDecompressMaze {
    public static void main(String[] args) {


        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(1000);
        bb.array();



        ByteBuffer byteBuffer = ByteBuffer.wrap(bb.array());
        System.out.println(byteBuffer.getInt());

//        int i = 255;
//        byte x = (byte) i;
//        System.out.println(x); // -22
//        int i2 = x & 0xFF;
//        System.out.println(i2); // 234



//        String mazeFileName = "savedMaze.maze";
//        AMazeGenerator mazeGenerator = new MyMazeGenerator();
//        Maze maze = mazeGenerator.generate(100, 100); //Generate new maze
//        try {
//            // save maze to a file
//            OutputStream out = new MyCompressorOutputStream(new FileOutputStream(mazeFileName));
//            out.write(maze.toByteArray());
//            out.flush();
//            out.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
//        Maze loadedMaze = new Maze(savedMazeBytes);
//        boolean areMazesEquals =
//                Arrays.equals(loadedMaze.toByteArray(),maze.toByteArray());
//        System.out.println(String.format("Mazes equal: %s",areMazesEquals)); //maze should be equal to loadedMaze
    }

}
