package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Solution implements Serializable
{
//    private ArrayList<AState> solPath;
//
//    public Solution(ArrayList<AState> solPath) { this.solPath = solPath; }
//
//    public ArrayList<AState> getSolutionPath()
//    {
//        return solPath;
//    }

    private AState sol;

    public Solution(AState solution) {
        if (solution != null)
            this.sol = solution;
        else
            this.sol = null;
    }

    public ArrayList<AState> getSolutionPath()
    {
        ArrayList<AState> solPath = new ArrayList<AState>();
        //add sol (goal state) to the ArrayList
        solPath.add(sol);
        AState temp = null;
        //add the parent of sol to the ArrayList
        if (sol.getParent() != null)
            temp = sol.getParent();
        //if temp!=null -> add all states of the solution to the ArrayList
        while (temp != null) {
            solPath.add(temp);
            temp = temp.getParent();
        }
        Collections.reverse(solPath);
        return solPath;
    }

    //public String toString() { return ("Solution steps: "); }

    public AState getState() {
        return sol;
    }
}
