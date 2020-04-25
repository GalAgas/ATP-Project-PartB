package algorithms.search;

import java.util.ArrayList;

public class Solution
{
    private ArrayList<AState> solPath;

    public Solution(ArrayList<AState> solPath) { this.solPath = solPath; }

    public ArrayList<AState> getSolutionPath()
    {
        return solPath;
    }

}
