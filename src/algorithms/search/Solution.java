package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;

public class Solution implements Serializable
{
    private ArrayList<AState> solPath;

    public Solution(ArrayList<AState> solPath) { this.solPath = solPath; }

    public ArrayList<AState> getSolutionPath()
    {
        return solPath;
    }

}
