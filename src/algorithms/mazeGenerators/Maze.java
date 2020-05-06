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

    public Maze(byte[] bytesMaze)
    {
        byte [] bm = new byte[4];
        int [] res = new int[6];
        int counter = 0;
        int resIndex = 0;
        for (int i=0; i<24; i++)
        {
            bm[counter] = bytesMaze[i];
            counter++;
            if (counter == 4)
            {
                res[resIndex] = fromByteToInt(bm);
                resIndex++;
                counter = 0;
            }
        }
        rows = res[0];
        cols = res[1];
        setStartPosition(res[2], res[3]);
        setGoalPosition(res[4], res[5]);
        maze = new int[rows][cols];
        int index = 24;
        for (int i=0; i<rows; i++)
        {
            for (int j=0; j<cols; j++)
            {
                maze[i][j] = bytesMaze[index];
                index++;
            }
        }
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

    public byte[] toByteArray()
    {
        byte[] bytesMaze = new byte[24+rows*cols];
        int index=0;

        convert4(bytesMaze, rows, index);
        index+=4;

        convert4(bytesMaze, cols, index);
        index+=4;

        convert4(bytesMaze, startPosition.getRowIndex(), index);
        index+=4;

        convert4(bytesMaze, startPosition.getColumnIndex(), index);
        index+=4;

        convert4(bytesMaze, goalPosition.getRowIndex(), index);
        index+=4;

        convert4(bytesMaze, goalPosition.getColumnIndex(), index);

        //convert all the maze to bytes
        int startMaze = 24;
        for (int i=0; i<rows; i++)
        {
            for (int j=0; j<cols; j++)
            {
                bytesMaze[startMaze] = (byte) maze[i][j];
                startMaze++;
            }
        }
        return bytesMaze;
    }

    private void convert4(byte[] bytesMaze,int numToConvert, int startIndex)
    {
        byte[] convertedNum = fromIntToByte(numToConvert);
        System.arraycopy(convertedNum, 0, bytesMaze, startIndex,4);
    }

    private byte[] fromIntToByte(int num)
    {
        ByteBuffer bytes = ByteBuffer.allocate(4);
        bytes.putInt(num);
        return bytes.array();
    }

    private int fromByteToInt(byte[] bm)
    {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bm);
        return byteBuffer.getInt();
    }
}
