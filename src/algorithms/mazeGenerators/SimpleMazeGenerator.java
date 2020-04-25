package algorithms.mazeGenerators;

//import com.sun.deploy.security.SelectableSecurityManager;

import java.util.concurrent.ThreadLocalRandom;

public class SimpleMazeGenerator extends AMazeGenerator
{
    @Override
    public Maze generate(int rows, int cols) {
        if (rows<2)
            rows=2;
        if (cols<2)
            cols=2;
        Maze simpleMaze = new Maze(rows, cols);
        if (rows <= 3 || cols <= 3) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++)
                    simpleMaze.getMaze()[i][j] = 0;
            }
        }
        else
        {
            int rand;
            for (int i=0; i<rows; i++)
            {
                for (int j=0; j<cols; j++)
                {
                    rand = ThreadLocalRandom.current().nextInt(0, 11);
                    if (rand < 10 )
                        simpleMaze.getMaze()[i][j] = 0;
                    else
                        simpleMaze.getMaze()[i][j] = 1;
                }
            }
        }
        chooseStartandGoal(simpleMaze, rows, cols);
        return simpleMaze;
    }
}
