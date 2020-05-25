package algorithms.search;

import java.util.ArrayList;
import java.util.Collections;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm
{
    protected String name;
    protected int visitedNodes;

    @Override
    public String getName() { return name; }

    public int getNumberOfNodesEvaluated() { return visitedNodes; }

//    //building the solution's path
//    protected Solution createSolPath (AState currState)
//    {
//        Solution sol;
//        ArrayList<AState> path = new ArrayList<AState>();
//        while (currState != null)
//        {
//            path.add(currState);
//            currState = currState.getParent();
//        }
//        Collections.reverse(path);
//        sol = new Solution(path);
//        return sol;
//    }

    //reset all evaluated nodes to unvisited
    protected void setAllVisitedToFalse (ArrayList<AState> allVisitedNodes)
    {
        for (AState visitedNode : allVisitedNodes) {
            visitedNode.setVisited(false);
        }
    }
}
