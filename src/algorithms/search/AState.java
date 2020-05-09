package algorithms.search;

import java.io.Serializable;

public abstract class AState implements Comparable<AState>, Serializable
{
    private String name;
    private int cost;
    private AState parent;
    private AState[] neighbours;
    private boolean isVisited;

    public AState(String name)
    {
        this.name = name;
        neighbours = new AState[4];
        isVisited = false;
    }


    public void setCost(int cost)
    {
        this.cost = cost;
    }

    public void setParent(AState parent)
    {
        this.parent = parent;
    }

    public String getName()
    {
        return name;
    }

    public int getCost()
    {
        return cost;
    }

    public AState getParent() {
        return parent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisited() { return isVisited; }

    public void setVisited(boolean visited) { isVisited = visited; }

    public AState[] getNeigbours() { return neighbours; }

    public String toString() { return name; }

    public int compareTo(AState other)
    {
        if (other == null) //check?
            return 0;
        if (other.getCost() < cost)
            return 1;
        else if (other.getCost()==cost)
            return 0;
        return -1;
    }

}


