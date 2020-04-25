package algorithms.search;

import java.util.ArrayList;
import java.util.Stack;

public class DepthFirstSearch extends ASearchingAlgorithm
{
    private Stack<AState> stack;

    public DepthFirstSearch() {
        super.name = "Depth First Search";
        stack = new Stack<AState>();
    }

    @Override
    public Solution solve(ISearchable s) {
        if (s == null) //input check
            return null;
        AState start = s.getStart();
        AState goal = s.getGoal();
        if (start == null || goal == null) //input check
            return null;
        Solution sol = new Solution(new ArrayList<>());
        ArrayList<AState> allEvaluatedStates = new ArrayList<AState>();
        //there is more than one state in the path
        start.setVisited(true);
        stack.push(start);
        allEvaluatedStates.add(start);
        while (!stack.isEmpty()) {
            AState curr = stack.pop();
            if (curr.getName().equals(goal.getName())) //reached to goal state
            {
                sol =  createSolPath(curr);
                break;
            }
            ArrayList<AState> allPossNext = s.getAllPossibleStates(curr); //get all curr possible moves
            //push unvisited states to the stack
            for (AState nextState : allPossNext) {
                if (!nextState.isVisited()) {
                    nextState.setParent(curr);
                    nextState.setVisited(true);
                    super.visitedNodes++;
                    stack.push(nextState);
                    allEvaluatedStates.add(nextState);
                }
            }
        }
        setAllVisitedToFalse(allEvaluatedStates);
        return sol;
    }
}
