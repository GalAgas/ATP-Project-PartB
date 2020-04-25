package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator{
    @Override
    public Maze generate(int rows, int cols)
    {
        if (rows<2)
            rows=2;
        if (cols<2)
            cols=2;
        Maze emptyMaze = new Maze(rows, cols);
        for (int i=0; i<rows; i++)
        {
            for (int j=0; j<cols; j++)
                emptyMaze.getMaze()[i][j] = 0;
        }
        chooseStartandGoal(emptyMaze, rows, cols);
        return emptyMaze;
    }
}
