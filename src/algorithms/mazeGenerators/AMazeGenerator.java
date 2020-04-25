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

//    public void chooseStartandGoal(Maze maze, int rows, int cols)
//    {
//        List<Integer> rowsList = new ArrayList<>(); //list of all relevant rows to choose from
//        for (int i=1; i<rows-1; i++)
//            rowsList.add(i);
//        //START
//        boolean found = false; //checks if the random row we got has only 1's - if not we found the row
//        int RowforStart = 0;
//        int ColforStart = 0;
//        while (!found)
//        {
//            Random rand = new Random();
//            RowforStart = rowsList.get(rand.nextInt(rowsList.size())); //randomly chooses a row from the rows list
//            for (int i=1; i<cols-1; i++) //iterates over the relevant cols (from the start)
//            {
//                if (maze.isZero(RowforStart, i)) //checks if cell is 0
//                {
//                    ColforStart = i;
//                    found = true;
//                    break;
//                }
//            }
//        }
//        maze.setStartPosition(RowforStart, ColforStart); //sets start position
//        //removes the chosen row for start position and the row before it and after
//        rowsList.remove(Integer.valueOf(RowforStart));
//        if (RowforStart-1 > 0)
//            rowsList.remove(Integer.valueOf(RowforStart-1));
//        if (RowforStart+1 < rows-1)
//            rowsList.remove(Integer.valueOf(RowforStart+1));
//        //GOAL
//        boolean found2 = false; //checks if the random row we got has only 1's - if not we found the row
//        int RowforGoal = 0;
//        int ColforGoal = 0;
//        while (!found2)
//        {
//            Random rand = new Random();
//            RowforGoal = rowsList.get(rand.nextInt(rowsList.size())); //randomly chooses a row from the rows list
//            for (int i=cols-2; i>0; i--) //iterates over the relevant cols (from the end)
//            {
//                if (maze.isZero(RowforGoal, i)) //checks if cell is 0
//                {
//                    ColforGoal = i;
//                    found2 = true;
//                    break;
//                }
//            }
//        }
//        maze.setGoalPosition(RowforGoal, ColforGoal); //sets goal position
//    }

    /*public int chooseRandCol(Maze maze, int row, int colLength)
    {
        ArrayList<Integer> allZerosInRow = new ArrayList<Integer>();
        for (int i=1; i<colLength-1; i++)
        {
            if(maze.getMaze()[row][i] == 0)
                allZerosInRow.add(i);
        }
        int randIndex = ThreadLocalRandom.current().nextInt(0, allZerosInRow.size());
        return allZerosInRow.get(randIndex);
    }

    public int chooseRandRow(Maze maze, int col, int rowLength)
    {
        ArrayList<Integer> allZerosInCol = new ArrayList<Integer>();
        for (int i=1; i<rowLength-1; i++)
        {
            if(maze.getMaze()[i][col] == 0)
                allZerosInCol.add(i);
        }
        int randIndex = ThreadLocalRandom.current().nextInt(0, allZerosInCol.size());
        return allZerosInCol.get(randIndex);
    }

    public int[] initializePosition(Maze maze, int chosenFrame, boolean status)
    {
        int[] pos = new int[2]; //0-row , 1-col
        if (chosenFrame == 0) //up
        {
            if (status)
                pos[0] = 1;
            else
                pos[0] = 0;
            pos[1] = chooseRandCol(maze, pos[0], maze.getCols());
        }
        if (chosenFrame == 1) //down
        {
            if (status)
                pos[0] = maze.getRows()-2;
            else
                pos[0] = maze.getRows()-1;
            pos[1] = chooseRandCol(maze, pos[0], maze.getCols());
        }
        if (chosenFrame == 2) //right
        {
            if (status)
                pos[1] = maze.getCols()-2;
            else
                pos[1] = maze.getCols()-1;
            pos[0] = chooseRandRow(maze, pos[1], maze.getRows());
        }
        if (chosenFrame == 3) //left
        {
            if (status)
                pos[1] = 1;
            else
                pos[1] = 0;
            pos[0] = chooseRandRow(maze, pos[1], maze.getRows());
        }
        return pos;
    }

    public void chooseStartandGoal(Maze maze, int rows, int cols)
    {
        //creates list of possible frames to choose from
        List<Integer> frameList = new ArrayList<>();
        for (int i=0; i<cols; i++)
        {
            //up = 0
            if(maze.getMaze()[0][i] == 0 && !frameList.contains(0))
                frameList.add(0);
            //down = 1
            if(maze.getMaze()[rows-1][i] == 0 && !frameList.contains(1))
                frameList.add(1);
        }
        for (int i=0; i<rows; i++)
        {
            //right = 2
            if(maze.getMaze()[i][cols-1] == 0 && !frameList.contains(2))
                frameList.add(2);
            //left = 3
            if(maze.getMaze()[i][0] == 0 && !frameList.contains(3))
                frameList.add(3);
        }
        //Start
        Random rand = new Random();
        int frameSideStart = frameList.get(rand.nextInt(frameList.size())); //randomly chooses a side from the frame list
        int[] startPos = initializePosition(maze, frameSideStart, false);
        maze.setStartPosition(startPos[0], startPos[1]);
        //Goal
        frameList.remove(frameList.indexOf(frameSideStart)); //removes the selected frame of start
        int frameSideGoal;
        boolean threeWalls;
        if (frameList.isEmpty()) //3 walls of 1's
        {
            List<Integer> secondFrameList = new ArrayList<>();
            for (int i=0; i<4; i++)
            {
                if (frameSideStart != i)
                    secondFrameList.add(i);
            }
            frameSideGoal = secondFrameList.get(rand.nextInt(secondFrameList.size()));
            threeWalls = true;
        }
        else
        {
            frameSideGoal = frameList.get(rand.nextInt(frameList.size())); //randomly chooses a side from the frame list
            threeWalls = false;
        }
        int[] goalPos = initializePosition(maze, frameSideGoal, threeWalls);;
        maze.setGoalPosition(goalPos[0], goalPos[1]);

        //**********************************************8
        //selects a random side of the frame - up, down, right or left for the start.
        int frameSideStart = ThreadLocalRandom.current().nextInt(0, 4);
        int startRandomCol;
        int startRandomRow;
        boolean startInCorner = false;

        int frameSideGoal;
        int goalRandomCol;
        int goalRandomRow;


        //up side
        if (frameSideStart == 0)
        {
            startRandomCol = chooseRandCol(maze, 0, cols, 0);
            maze.setStartPosition(0, startRandomCol);

            //up right corner-goal can be in left or down only
            if (startRandomCol == cols - 1)
            {
                startInCorner = true;
                frameSideGoal = ThreadLocalRandom.current().nextInt(0, 2);
                if (frameSideGoal == 0)//left
                {
                    goalRandomRow = chooseRandRow(maze, 0, rows, 1);
                    maze.setGoalPosition(goalRandomRow,0);
                }

                else //down
                {
                    goalRandomCol = chooseRandCol(maze, rows-1, cols-1,0);
                    maze.setGoalPosition(rows-1, goalRandomCol);
                }
            }

            //up left corner-goal can be in right or down only
            else if(startRandomCol == 0)
            {
                startInCorner = true;
                frameSideGoal = ThreadLocalRandom.current().nextInt(0, 2);
                if (frameSideGoal == 0)//right
                {
                    goalRandomRow = chooseRandRow(maze, cols-1, rows, 1);
                    maze.setGoalPosition(goalRandomRow,cols-1);
                }
                else //down
                {
                    goalRandomCol = chooseRandCol(maze, rows-1, cols,1);
                    maze.setGoalPosition(rows-1, goalRandomCol);
                }
            }

            //start is not in the corner
            if (!startInCorner)
            {
                frameSideGoal = ThreadLocalRandom.current().nextInt(0, 3);
                if (frameSideGoal == 0)//right
                {
                    goalRandomRow = chooseRandRow(maze, cols-1, rows, 1);
                    maze.setGoalPosition(goalRandomRow,cols-1);
                }
                else if (frameSideGoal == 1) //left
                {
                    goalRandomRow = chooseRandRow(maze, 0, rows, 1);
                    maze.setGoalPosition(goalRandomRow,0);
                }
                else //down
                {
                    goalRandomCol = chooseRandCol(maze, rows-1, cols,0);
                    maze.setGoalPosition(rows-1,goalRandomCol);
                }
            }
        }

        //down side
        else if (frameSideStart == 1)
        {
            startRandomCol = chooseRandCol(maze, rows-1, cols, 0);
            maze.setStartPosition(rows-1, startRandomCol);


            //down right corner-goal can be in left or up only
            if (startRandomCol == cols - 1)
            {
                startInCorner = true;
                frameSideGoal = ThreadLocalRandom.current().nextInt(0, 2);
                if (frameSideGoal == 0)//left
                {
                    goalRandomRow = chooseRandRow(maze, 0, rows-1, 0);
                    maze.setGoalPosition(goalRandomRow,0);
                }
                else //up
                {
                    goalRandomCol = chooseRandCol(maze,0, cols-1,0);
                    maze.setGoalPosition(0, goalRandomCol);
                }
            }

            //down left corner-goal can be in right or up only
            else if(startRandomCol == 0)
            {
                startInCorner = true;
                frameSideGoal = ThreadLocalRandom.current().nextInt(0, 2);
                if (frameSideGoal == 0)//right
                {
                    goalRandomRow = chooseRandRow(maze, cols-1, rows-1, 0);
                    maze.setGoalPosition(goalRandomRow,cols-1);
                }
                else //up
                {
                    goalRandomCol = chooseRandCol(maze,0, cols,1);
                    maze.setGoalPosition(0, goalRandomCol);
                }
            }

            //start is not in the corner
            if (!startInCorner)
            {
                frameSideGoal = ThreadLocalRandom.current().nextInt(0, 3);
                if (frameSideGoal == 0)//right
                {
                    goalRandomRow = chooseRandRow(maze, cols-1, rows-1, 0);
                    maze.setGoalPosition(goalRandomRow,cols-1);
                }
                else if (frameSideGoal == 1) //left
                {
                    goalRandomRow = chooseRandRow(maze, 0, rows-1, 0);
                    maze.setGoalPosition(goalRandomRow,0);
                }
                else //up
                {
                    goalRandomCol = chooseRandCol(maze,0, cols,0);
                    maze.setGoalPosition(0, goalRandomCol);
                }
            }
        }

        //left side
        else if (frameSideStart == 2)
        {
            startRandomRow = chooseRandRow(maze,0, rows,0);
            maze.setStartPosition(startRandomRow, 0);


            //up left corner-goal can be in right or down only
            if (startRandomRow == 0)
            {
                startInCorner = true;
                frameSideGoal = ThreadLocalRandom.current().nextInt(0, 2);
                if (frameSideGoal == 0)//right
                {
                    goalRandomRow = chooseRandRow(maze,cols-1, rows,1);
                    maze.setGoalPosition(goalRandomRow,cols -1);
                }
                else //down
                {
                    goalRandomCol = chooseRandCol(maze,rows-1, cols,1);
                    maze.setGoalPosition(rows-1, goalRandomCol);
                }
            }

            //down left corner-goal can be in right or up only
            else if(startRandomRow == rows-1)
            {
                startInCorner = true;
                frameSideGoal = ThreadLocalRandom.current().nextInt(0, 2);
                if (frameSideGoal == 0)//right
                {
                    goalRandomRow = chooseRandRow(maze,cols-1,rows-1,0);
                    maze.setGoalPosition(goalRandomRow,cols-1);
                }
                else //up
                {
                    goalRandomCol = chooseRandCol(maze,0, cols,1);
                    maze.setGoalPosition(0, goalRandomCol);
                }
            }

            //start is not in the corner
            if (!startInCorner)
            {
                frameSideGoal = ThreadLocalRandom.current().nextInt(0, 3);
                if (frameSideGoal == 0)//right
                {
                    goalRandomRow = chooseRandRow(maze,cols - 1, rows,0);
                    maze.setGoalPosition(goalRandomRow,cols - 1);
                }
                else if (frameSideGoal == 1) //up
                {
                    goalRandomCol = chooseRandCol(maze,0, cols,1);
                    maze.setGoalPosition(0, goalRandomCol);
                }
                else //down
                {
                    goalRandomCol = chooseRandCol(maze,rows-1,cols,1);
                    maze.setGoalPosition(rows-1,goalRandomCol);
                }
            }
        }

        //right side
        else
        {
            startRandomRow = chooseRandRow(maze,cols-1, rows,0);
            maze.setStartPosition(startRandomRow, cols-1);

            //up right corner-goal can be in left or down only
            if (startRandomRow == 0)
            {
                startInCorner = true;
                frameSideGoal = ThreadLocalRandom.current().nextInt(0, 2);
                if (frameSideGoal == 0)//left
                {
                    goalRandomRow = chooseRandRow(maze,0, rows,1);
                    maze.setGoalPosition(goalRandomRow,0);
                }
                else //down
                {
                    goalRandomCol = chooseRandCol(maze,rows-1,cols-1,0);
                    maze.setGoalPosition(rows-1, goalRandomCol);
                }
            }

            //down right corner-goal can be in left or up only
            else if (startRandomRow == rows-1)
            {
                startInCorner = true;
                frameSideGoal = ThreadLocalRandom.current().nextInt(0, 2);
                if (frameSideGoal == 0)//left
                {
                    goalRandomRow = chooseRandRow(maze,0,rows-1,0);
                    maze.setGoalPosition(goalRandomRow,0);
                }
                else //up
                {
                    goalRandomCol = chooseRandCol(maze,0,cols-1,0);
                    maze.setGoalPosition(0, goalRandomCol);
                }
            }

            //start is not in the corner
            if (!startInCorner)
            {
                frameSideGoal = ThreadLocalRandom.current().nextInt(0, 3);
                if (frameSideGoal == 0)//left
                {
                    goalRandomRow = chooseRandRow(maze,0, rows,0);
                    maze.setGoalPosition(goalRandomRow, 0);
                }
                else if (frameSideGoal == 1) //up
                {
                    goalRandomCol = chooseRandCol(maze,0,cols-1,0);
                    maze.setGoalPosition(0, goalRandomCol);
                }
                else //down
                {
                    goalRandomCol = chooseRandCol(maze,rows-1,cols-1,0);
                    maze.setGoalPosition(rows-1,goalRandomCol);
                }
            }
        }
    }*/
}
