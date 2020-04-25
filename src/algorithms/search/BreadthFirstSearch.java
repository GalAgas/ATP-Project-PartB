package algorithms.search;


import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch extends ASearchingAlgorithm {

    protected Queue<AState> Q;

    public BreadthFirstSearch() {
        super.name = "Breadth First Search";
        Q = new LinkedList<AState>();
    }

    @Override
    public Solution solve(ISearchable s)
    {
        if (s == null)
            return null;
        if (s.getStart() == null || s.getGoal() == null)
            return null;

        ArrayList<AState> allEvaluatedStates = new ArrayList<AState>();
        Q.add(s.getStart());
        allEvaluatedStates.add(s.getStart());
        s.getStart().setVisited(true);

        Solution sol = new Solution(new ArrayList<>());

        while (!Q.isEmpty()) {
            AState currState = Q.remove();

            //found goal
            if (currState == s.getGoal())
            {
                //building the solution's path
                sol = createSolPath (currState);
                break;
            }

            //get all currState's neigbours
            ArrayList<AState> allPossNext = s.getAllPossibleStates(currState);
            for (AState nextState : allPossNext) {
                if (!nextState.isVisited()) {
                    nextState.setVisited(true);
                    visitedNodes++;
                    nextState.setParent(currState);
                    Q.add(nextState);
                    allEvaluatedStates.add(nextState);
                }
            }
        }
        setAllVisitedToFalse(allEvaluatedStates);
        return sol;
    }

}
