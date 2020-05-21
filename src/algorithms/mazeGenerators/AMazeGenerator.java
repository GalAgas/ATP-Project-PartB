package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AMazeGenerator implements IMazeGenerator
{
    public long measureAlgorithmTimeMillis(int rows, int cols)
    {
        long startTime = System.currentTimeMillis();
        this.generate(rows, cols);
        long endTime = System.currentTimeMillis();
        return endTime-startTime;
    }

    public void chooseStartandGoal(Maze maze, int rows, int cols)
    {
        List<Integer> rowsList = new ArrayList<>(); //list of all rows
        for (int i=0; i<rows; i++)
            rowsList.add(i);
        //START
        boolean found = false; //checks if the random row we got has only 1's - if not we found the row
        int RowforStart = 0;
        int ColforStart = 0;
        while (!found)
        {
            Random rand = new Random();
            RowforStart = rowsList.get(rand.nextInt(rowsList.size())); //randomly chooses a row from the rows list
            for (int i=0; i<cols; i++) //iterates over the relevant cols (from the start)
            {
                if (maze.isZero(RowforStart, i)) //checks if cell is 0
                {
                    ColforStart = i;
                    found = true;
                    break;
                }
            }
        }
        maze.setStartPosition(RowforStart, ColforStart); //sets start position
        //removes the chosen row for start position
        rowsList.remove(Integer.valueOf(RowforStart));
        //GOAL
        boolean found2 = false; //checks if the random row we got has only 1's - if not we found the row
        int RowforGoal = 0;
        int ColforGoal = 0;
        while (!found2)
        {
            Random rand = new Random();
            RowforGoal = rowsList.get(rand.nextInt(rowsList.size())); //randomly chooses a row from the rows list
            for (int i=cols-1; i>=0; i--) //iterates over the relevant cols (from the end)
            {
                if (maze.isZero(RowforGoal, i)) //checks if cell is 0
                {
                    ColforGoal = i;
                    found2 = true;
                    break;
                }
            }
        }
        maze.setGoalPosition(RowforGoal, ColforGoal); //sets goal position
    }
}
