package algorithms.search;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Solution implements Serializable
{
    private ArrayList<AState> solPath ;

    public Solution(ArrayList<AState> solPath) { this.solPath = solPath; }

    public ArrayList<AState> getSolutionPath()
    {
        return solPath;
    }

    public void setNeighboursPathToNull()
    {
        for (AState state:solPath) {
            state.setNeighboursToNull();
        }
    }

    //public String toString() { return ("Solution steps: "); }


}
