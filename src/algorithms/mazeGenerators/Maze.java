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

        //convert the number of rows from int to byte
        byte[] bRows = fromIntToByte(rows);
        bytesMaze[0] = bRows[0];
        bytesMaze[1] = bRows[1];
        bytesMaze[2] = bRows[2];
        bytesMaze[3] = bRows[3];

        //convert the number of cols from int to byte
        byte[] bCols = fromIntToByte(cols);
        bytesMaze[4] = bCols[0];
        bytesMaze[5] = bCols[1];
        bytesMaze[6] = bCols[2];
        bytesMaze[7] = bCols[3];

        //convert the row of start position from int to byte
        byte[] startRow = fromIntToByte(startPosition.getRowIndex());
        bytesMaze[8] = startRow[0];
        bytesMaze[9] = startRow[1];
        bytesMaze[10] = startRow[2];
        bytesMaze[11] = startRow[3];

        //convert the col of start position from int to byte
        byte[] startCol = fromIntToByte(startPosition.getColumnIndex());
        bytesMaze[12] = startCol[0];
        bytesMaze[13] = startCol[1];
        bytesMaze[14] = startCol[2];
        bytesMaze[15] = startCol[3];

        //convert the row of goal position from int to byte
        byte[] goalRow = fromIntToByte(goalPosition.getRowIndex());
        bytesMaze[16] = goalRow [0];
        bytesMaze[17] = goalRow [1];
        bytesMaze[18] = goalRow [2];
        bytesMaze[19] = goalRow [3];

        //convert the col of goal position from int to byte
        byte[] goalCol = fromIntToByte(goalPosition.getColumnIndex());
        bytesMaze[20] = goalCol[0];
        bytesMaze[21] = goalCol[1];
        bytesMaze[22] = goalCol[2];
        bytesMaze[23] = goalCol[3];

        //convert all the maze to bytes
        int index = 24;
        for (int i=0; i<rows; i++)
        {
            for (int j=0; j<cols; j++)
            {
                bytesMaze[index] = (byte) maze[i][j];
                index++;
            }
        }
        return bytesMaze;
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
