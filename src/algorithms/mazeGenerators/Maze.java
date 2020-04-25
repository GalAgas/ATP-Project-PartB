package algorithms.mazeGenerators;

import java.nio.ByteBuffer;

public class Maze
{
    private int rows;
    private int cols;
    private int[][] maze;
    private Position startPosition;
    private Position goalPosition;

    public Maze(int rows, int cols)
    {
        this.rows = rows;
        this.cols = cols;
        maze = new int[rows][cols];
    }

    public int[][] getMaze()
    {
        return maze;
    }

    public Position getStartPosition()
    {
        return startPosition;
    }

    public Position getGoalPosition()
    {
        return goalPosition;
    }

    public void setStartPosition(int row, int col)
    {
        this.startPosition = new Position(row, col);
    }

    public void setGoalPosition(int row, int col)
    {
        this.goalPosition = new Position(row, col);
    }

    public boolean isZero (int row, int col) { return maze[row][col] == 0; }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public void print()
    {
        for (int i=0; i<rows; i++)
        {
            for (int j=0; j<cols; j++)
            {
                if(i == startPosition.getRowIndex() && j == startPosition.getColumnIndex())
                    System.out.print('S');
                else if(i == goalPosition.getRowIndex() && j == goalPosition.getColumnIndex())
                    System.out.print('E');
                else
                    System.out.print(this.maze[i][j]);
            }
            System.out.println();
        }
    }

//    public byte[] toByteArray()
//    {
//
////        ByteBuffer bb = ByteBuffer.allocate(4);
////        bb.putInt(1000);
////        bb.array();
//    }

}
