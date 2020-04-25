package algorithms.mazeGenerators;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MyMazeGenerator extends AMazeGenerator
{
    @Override
    public Maze generate(int rows, int cols)
    {
        if (rows<2)
            rows=2;
        if (cols<2)
            cols=2;
        Maze myMaze = new Maze(rows, cols);
        if (rows <= 3 || cols <= 3)
        {
            for (int i=0; i<rows; i++)
            {
                for (int j=0; j<cols; j++)
                    myMaze.getMaze()[i][j] = 0;
            }
//            int rand;
//            for (int i=0; i<rows; i++)
//            {
//                for (int j=0; j<cols; j++)
//                {
//                    rand = ThreadLocalRandom.current().nextInt(0, 11);
//                    if (rand < 10 )
//                        myMaze.getMaze()[i][j] = 0;
//                    else
//                        myMaze.getMaze()[i][j] = 1;
//                }
//            }
        }
        else
        {
            for (int i=0; i<rows; i++)
            {
                for (int j=0; j<cols; j++)
                    myMaze.getMaze()[i][j] = 1;
            }

            LinkedList<Position[]> neighbours = new LinkedList<>();
            Random random = new Random();
            int x = random.nextInt(rows);
            int y = random.nextInt(cols);
            neighbours.add(new Position[]{new Position(x,y),new Position(x,y)});

            while (!neighbours.isEmpty())
            {
                Position[] curr = neighbours.remove(random.nextInt(neighbours.size()));
                x = curr[1].getRowIndex();
                y = curr[1].getColumnIndex();

                if(myMaze.getMaze()[x][y] == 1)
                {
                    myMaze.getMaze()[curr[0].getRowIndex()][curr[0].getColumnIndex()] = 0;
                    myMaze.getMaze()[x][y] = 0;

                    if (x>=2 && myMaze.getMaze()[x-2][y] == 1)
                        neighbours.add(new Position[]{new Position(x-1,y),new Position(x-2,y)});

                    if (y>=2 && myMaze.getMaze()[x][y-2] == 1)
                        neighbours.add(new Position[]{new Position(x,y-1),new Position(x,y-2)});

                    if (x < rows-2 && myMaze.getMaze()[x+2][y] == 1)
                        neighbours.add(new Position[]{new Position(x+1,y),new Position(x+2,y)});

                    if (y < cols-2 && myMaze.getMaze()[x][y+2] == 1)
                        neighbours.add(new Position[]{new Position(x,y+1),new Position(x,y+2)});
                }

            }
        }
        chooseStartandGoal(myMaze, rows, cols);
        return myMaze;
    }
}
