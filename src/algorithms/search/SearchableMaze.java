package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.*;

public class SearchableMaze implements ISearchable
{
    private Maze maze;
    private AState start;
    private AState goal;
    private HashMap<String, AState> allStates;

    public SearchableMaze(Maze maze)
    {
        this.maze = maze;
        allStates = new HashMap<>();
        //creates all states with zero
        buildAllStates();
        //initializes 4 neighbours for each state
        setNeighbours();

        int sRow = maze.getStartPosition().getRowIndex();
        int sCol = maze.getStartPosition().getColumnIndex();
        start = searchStateByName(sRow, sCol);

        int gRow = maze.getGoalPosition().getRowIndex();
        int gCol = maze.getGoalPosition().getColumnIndex();
        goal = searchStateByName(gRow, gCol);

        //initializes all costs
        setAllCosts();
    }

    public AState up (AState state)
    {
        int row = getRowState(state);
        int col = getColState(state);
        AState up = null;
        if (row-1 >= 0 && maze.isZero(row-1, col))
            up = searchStateByName(row-1, col);
        return up;
    }

    public AState right (AState state)
    {
        int row = getRowState(state);
        int col = getColState(state);
        AState right = null;
        if (col+1 <= maze.getCols()-1 && maze.isZero(row, col+1))
            right = searchStateByName(row, col+1);
        return right;
    }

    public AState left (AState state)
    {
        int row = getRowState(state);
        int col = getColState(state);
        AState left = null;
        if (col-1 >= 0 && maze.isZero(row, col-1))
            left = searchStateByName(row, col-1);
        return left;
    }

    public AState down (AState state)
    {
        int row = getRowState(state);
        int col = getColState(state);
        AState down = null;
        if (row+1 <= maze.getRows()-1 && maze.isZero(row+1, col))
            down = searchStateByName(row+1, col);
        return down;
    }

    private void buildAllStates()
    {
        for (int i=0; i<maze.getRows(); i++) {
            for (int j = 0; j < maze.getCols(); j++) {
                if (maze.isZero(i, j)) {
                    AState newState = new MazeState(Integer.toString(i) + "," + Integer.toString(j));
                    newState.setCost(Integer.MAX_VALUE);
                    allStates.put(newState.getName(), newState);
                }
            }
        }
    }

    private void setNeighbours()
    {
        for (AState val : allStates.values())
        {
            val.getNeigbours()[0] = up(val);
            val.getNeigbours()[1] = right(val);
            val.getNeigbours()[2] = down(val);
            val.getNeigbours()[3] = left(val);
        }
    }

    private int getRowState(AState state)
    {
        int index = state.getName().indexOf(",");
        int row = Integer.parseInt(state.getName().substring(0,index));
        return row;
    }

    private int getColState(AState state)
    {
        int index = state.getName().indexOf(",");
        int col = Integer.parseInt(state.getName().substring(index+1));
        return col;
    }

    private AState searchStateByName(int row, int col)
    {
        String key = Integer.toString(row) + "," + Integer.toString(col);
        AState val = null;
        if (allStates.containsKey(key))
            val = allStates.get(key);
        return val;
    }

    @Override
    public ArrayList<AState> getAllPossibleStates(AState state)
    {
        ArrayList<AState> possibleS = new ArrayList<AState>();

        AState upR = null;
        AState upL = null;
        AState downR = null;
        AState downL = null;


        //up
        if (state.getNeigbours()[0] != null)
        {
            possibleS.add(state.getNeigbours()[0]);
            upR = state.getNeigbours()[0].getNeigbours()[1];
            if (upR != null)
                possibleS.add(upR);
            upL = state.getNeigbours()[0].getNeigbours()[3];
            if (upL != null)
                possibleS.add(upL);
        }

        //right
        if (state.getNeigbours()[1] != null)
        {
            possibleS.add(state.getNeigbours()[1]);
            if (upR == null)
            {
                upR = state.getNeigbours()[1].getNeigbours()[0];
                if (upR != null)
                    possibleS.add(upR);
            }
            downR = state.getNeigbours()[1].getNeigbours()[2];
            if (downR != null)
                possibleS.add(downR);
        }

        //down
        if (state.getNeigbours()[2] != null)
        {
            possibleS.add(state.getNeigbours()[2]);
            if (downR == null)
            {
                downR = state.getNeigbours()[2].getNeigbours()[1];
                if (downR != null)
                    possibleS.add(downR);
            }
            downL = state.getNeigbours()[2].getNeigbours()[3];
            if (downL != null)
                possibleS.add(downL);
        }

        //left
        if (state.getNeigbours()[3] != null)
        {
            possibleS.add(state.getNeigbours()[3]);
            if (upL == null)
            {
                upL = state.getNeigbours()[3].getNeigbours()[0];
                if (upL != null)
                    possibleS.add(upL);
            }
            if (downL == null)
            {
                downL = state.getNeigbours()[3].getNeigbours()[2];
                if (downL != null)
                    possibleS.add(downL);
            }
        }
        return possibleS;
    }

    public AState getStart() { return start; }

    public AState getGoal() { return goal; }

    public void setAllCosts()
    {
        Queue<AState> q = new LinkedList<AState>();
        q.add(start);
        start.setCost(0);

        while (!q.isEmpty()) {
            AState currState = q.remove();
            //get all currState's neighbours
            ArrayList<AState> neighbours = getAllPossibleStates(currState);
            for (AState state : neighbours)
            {
                //does'nt have cost yet
                if (state.getCost() == Integer.MAX_VALUE)
                {
                    q.add(state);
                    calcCost(state);
                }
            }
        }
    }

    private void calcCost(AState state)
    {
        if (state == null)
            return;
        int addition;
        ArrayList<AState> neighbours = getAllPossibleStates(state);
        AState minState = findMinCostNeighbour(neighbours);
        List<AState> regNeighbours = Arrays.asList(state.getNeigbours());
        //not diagonal
        if (regNeighbours.contains(minState))
            addition = 10;
        //diagonal
        else
            addition = 15;
        state.setCost(minState.getCost()+addition);
    }

    private AState findMinCostNeighbour(ArrayList<AState> neighbours)
    {
        if (neighbours == null)
            return null;
        int minCost = Integer.MAX_VALUE;
        AState minState = null;
        for (AState s: neighbours)
        {
            if(s.getCost() < minCost)
            {
                minState = s;
                minCost = s.getCost();
            }
        }
        return minState;
    }
}