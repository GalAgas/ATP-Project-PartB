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

        //test- toByteArray
        Maze m = new MyMazeGenerator().generate(4,500);
        m.print();

        byte[] bm = m.toByteArray();

        for (int i=0; i<bm.length; i++)
        {
            System.out.println(bm[i]);
        }



//        ByteBuffer byteBuffer = ByteBuffer.wrap(bb.array());
//        System.out.println(byteBuffer.getInt());





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
